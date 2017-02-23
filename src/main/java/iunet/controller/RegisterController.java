package iunet.controller;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.octo.captcha.service.image.ImageCaptchaService;

import iunet.model.BaseResult;
import iunet.model.Email;
import iunet.model.proj.UmActivation;
import iunet.model.proj.UmUser;
import iunet.service.CommonService;
import iunet.service.RegisterService;
import iunet.util.DateUtil;
import iunet.util.EmailUtil;
import iunet.util.EncryptUtil;
import iunet.util.HtmlUtil;
import iunet.util.StringUtil;
import iunet.util.WebUtil;

@Controller
@RequestMapping("/register")
public class RegisterController {
	private static final Logger log = LoggerFactory.getLogger(RegisterController.class);

	@Autowired
	private ImageCaptchaService imageCaptchaService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private RegisterService registerService;

	@Value("${webContext}")
	private String webContext;

	class RegisterParam {
		@Size(min = 1, message = "用户名不能为空！")
		private String userName;

		@Size(min = 1, message = "昵称不能为空！")
		private String nickname;

		@Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "邮箱格式不正确！")
		private String email;

		@Size(min = 1, max = 12, message = "密码长度必须在1-12之间！")
		private String password;

		@Size(min = 1, message = "验证码不能为空！")
		private String verifyCode;

		public RegisterParam(String userName, String nickname, String email, String password, String verifyCode) {
			this.userName = userName;
			this.nickname = nickname;
			this.email = email;
			this.password = password;
			this.verifyCode = verifyCode;
		}
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public BaseResult register(@RequestBody JSONObject req, HttpServletRequest request) {
		log.info("/register/register.do start.. time：{}", DateUtil.nowStr());
		HttpSession session = request.getSession();
		try {
			String userName = req.getString("userName");
			String nickname = req.getString("nickname");
			String email = req.getString("email");
			String password = req.getString("password");
			String verifyCode = req.getString("verifyCode");

			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<RegisterParam>> constraintViolations = validator
					.validate(new RegisterParam(userName, nickname, email, password, verifyCode));
			for (ConstraintViolation<RegisterParam> constraintViolation : constraintViolations) {
				return BaseResult.returnErrorMessage(constraintViolation.getMessage());
			}

			if (!imageCaptchaService.validateResponseForID(session.getId(), verifyCode)) {
				log.info("用户输入的验证图形验证码有误！register verifyCode：{}", verifyCode);
				return BaseResult.returnErrorMessage(1, "您输入的验证码不正确！");
			}

			if (registerService.emailHasRegisterOccupy(email)) {
				log.error("邮箱已被注册，是本人邮箱？您可以通过邮箱验证找回密码！");
				return BaseResult.returnErrorMessage("邮箱已被注册，是本人邮箱？您可以通过邮箱验证找回密码！");
			}

			log.info("/register/register.do data:{}", req.toJSONString());

			UmUser user = new UmUser();
			user.setId(commonService.getNewId("UM_USER"));
			user.setLoginName(userName);
			user.setUserName(nickname);
			user.setPassword(EncryptUtil.md5Encrypt(password));
			user.setEmail(email);
			user.setState(new BigDecimal(0));
			user.setCreateTime(DateUtil.Timestamp());

			String data = ((JSONObject) JSONObject.toJSON(user)).toJSONString();
			String uuid = StringUtil.getRandom(32);
			String href = "http://" + WebUtil.getRemoteAddr(request) + ":" + WebUtil.getLocalPort(request) + "/"
					+ webContext + "/user/activation.do?uuid=" + uuid;

			Email mail = new Email();
			mail.setSubject("iunet-web 账户激活");
			mail.setToEmails(email);
			mail.setContent("<html><body>" + "<h2>亲爱的用户：</h2><br />" + "你好！<br />"
					+ "&nbsp&nbsp&nbsp&nbsp感谢您注册iunet-web。您正在进行账户注册，请点击下面链接激活您的账户：" + "<a href='" + href
					+ "' target='_blank'>点击激活账户</a><br />" + "&nbsp&nbsp&nbsp&nbsp如非本人操作，请忽略此邮件，由此给您带来的不便请谅解！<br />"
					+ "<br /><br /><br /><br />" + "&nbsp&nbsp&nbsp&nbspiunet "
					+ DateUtil.formatDateString(new Date(), "yyyy年MM月dd日 HH:mm:ss") + "</body></html>");

			UmActivation activation = new UmActivation();
			activation.setUuid(uuid);
			activation.setEmail(email);
			activation.setState(new BigDecimal(0));
			activation.setData(data);
			int inser = registerService.insertActivationByUUID(activation);
			if (inser == 1) {
				if (registerService.emailHasRegister(email)) {
					log.error("您的邮箱已经注册但未激活!");
					return BaseResult.returnErrorMessage("您的邮箱已经注册但未激活!");
				}
				EmailUtil.sendEmail(mail);
				log.info("发送激活邮件成功  mail：{}, 激活链接:{}, {}", JSONObject.toJSON(mail), href);
				return BaseResult.returnSuccessMessage("注册成功，请到邮箱激活账户！");
			} else {
				log.error("用户注册失败：data：{}", data);
				return BaseResult.returnErrorMessage("用户注册失败！");
			}
		} catch (Exception e) {
			log.error("用户注册失败! {}", e.getMessage());
			e.printStackTrace();
		}
		return BaseResult.returnErrorMessage("用户注册失败!");
	}

	@RequestMapping(value = "/activation", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public void activation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("/register/activation.do start.. time：{}", DateUtil.nowStr());
		String resStr = "";
		String filePath = "/vm/sweetalert.vm";
		String uuid = request.getParameter("uuid");
		if (StringUtils.isEmpty(uuid)) {
			resStr = HtmlUtil.addErrorActivation(filePath);
		} else {
			String userData = registerService.queryActivationDataByUUID(uuid);
			if (StringUtils.isEmpty(userData)) {
				resStr = HtmlUtil.addErrorActivation2(filePath);
			} else {
				UmUser user = JSONObject.toJavaObject((JSONObject) JSONObject.parse(userData), UmUser.class);
				try {
					registerService.insertUser(user);
					registerService.updateActivationStateByUUID(uuid);
					resStr = HtmlUtil.addSuccessActivation(filePath);
				} catch (Exception e) {
					log.error("激活失败！ userData:{}, uuid:{}", userData, uuid);
					resStr = HtmlUtil.addErrorActivation(filePath);
				}
			}
		}
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html;charset=UTF-8");
		OutputStream responseOutputStream = response.getOutputStream();
		try {
			log.info("resStr{}", resStr);
			responseOutputStream.write(resStr.getBytes("UTF-8"));
			responseOutputStream.flush();
		} catch (Exception e) {
			log.error("账户激活失败！");
			throw new Exception("账户激活失败！");
		} finally {
			responseOutputStream.close();
		}
	}
}