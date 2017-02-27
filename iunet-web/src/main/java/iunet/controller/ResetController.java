package iunet.controller;

import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.octo.captcha.service.image.ImageCaptchaService;

import iunet.model.BaseResult;
import iunet.model.Email;
import iunet.service.RegisterService;
import iunet.service.ResetService;
import iunet.util.DateUtil;
import iunet.util.EmailUtil;
import iunet.util.EncryptUtil;
import iunet.util.StringUtil;

@Controller
@RequestMapping("/reset")
public class ResetController {
	private static final Logger log = LoggerFactory.getLogger(ResetController.class);

	@Autowired
	private ImageCaptchaService imageCaptchaService;

	@Autowired
	private ResetService resetService;

	@Autowired
	private RegisterService registerService;

	/**
	 * 重置密码发送邮件验证码
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult sendEmail(@RequestBody JSONObject req, HttpServletRequest request) {
		log.info("/reset/sendEmail.do start.. time：{}", DateUtil.nowStr());
		HttpSession session = request.getSession();
		try {
			String email = req.getString("email");
			String verifyCode = req.getString("verifyCode");

			class SendEmailParam {
				@Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "邮箱格式不正确！")
				private String email;

				@Size(min = 1, message = "验证码不能为空！")
				private String verifyCode;

				public SendEmailParam(String email, String verifyCode) {
					this.email = email;
					this.verifyCode = verifyCode;
				}
			}
			
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<SendEmailParam>> constraintViolations = validator
					.validate(new SendEmailParam(email, verifyCode));
			for (ConstraintViolation<SendEmailParam> constraintViolation : constraintViolations) {
				return BaseResult.returnErrorMessage(constraintViolation.getMessage());
			}

			if (!imageCaptchaService.validateResponseForID(session.getId(), verifyCode)) {
				log.info("用户输入的验证图形验证码有误！resetSendEmail verifyCode：{}", verifyCode);
				return BaseResult.returnErrorMessage(1, "您输入的验证码不正确！");
			}

			log.info("/reset/sendEmail.do data:{}", req.toJSONString());

			if (!registerService.emailHasRegisterOccupy(email)) {
				log.error("邮箱尚未注册，无法找回密码！");
				return BaseResult.returnErrorMessage("邮箱尚未注册，无法找回密码！");
			}

			String resetVerifycode = StringUtil.getRandom(12);
			log.info("/reset/sendEmail.do resetVerifycode：{}", resetVerifycode);

			try {
				Email mail = new Email();
				mail.setSubject("iunet-web 重置密码");
				mail.setToEmails(email);
				mail.setContent("<html><body>" 
						+ "<h2>亲爱的用户：</h2><br />" 
						+ "你好！<br />"
						+ "&nbsp&nbsp&nbsp&nbsp感谢您使用iunet-web。您正在进行重置密码操作，请在验证码输入框中输入此次验证码："
						+ "<span style='color: red;font-weight: bold;'>" + resetVerifycode + "</span> [20分钟内有效]以完成验证。<br />"
						+ "&nbsp&nbsp&nbsp&nbsp如非本人操作，请忽略此邮件，由此给您带来的不便请谅解！<br />" 
						+ "<br /><br /><br /><br />&nbsp&nbsp&nbsp&nbspiunet " 
						+ DateUtil.formatDateString(new Date(), "yyyy年MM月dd日 HH:mm:ss")
						+ "</body></html>");
				EmailUtil.sendEmail(mail);

				session.setAttribute("cache_reset_verifycode", resetVerifycode);
				session.setAttribute("cache_reset_email", email);
				log.info("发送重置密码邮件成功  mail：{}, resetVerifycode:{}", JSONObject.toJSON(email), resetVerifycode);
				return BaseResult.returnSuccessMessage("发送重置密码邮件成功！");
			} catch (Exception e) {
				log.error("发送重置密码邮件失败！" + " mail：{}, resetVerifycode:{}, {}", JSONObject.toJSON(email), resetVerifycode,
						e.getMessage());
				e.printStackTrace();
				return BaseResult.returnErrorMessage("发送重置密码邮件失败！");
			}
		} catch (Exception e) {
			log.error("/reset/sendEmail.do error {}", e.getMessage());
			e.printStackTrace();
		}
		return BaseResult.returnErrorMessage("发送重置密码邮件失败！");
	}

	/**
	 * 重置密码
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult reset(@RequestBody JSONObject req, HttpServletRequest request) {
		log.info("/user/reset.do start.. time：{}", DateUtil.nowStr());
		HttpSession session = request.getSession();
		try {
			String verifyCode = req.getString("verifyCode");
			String password = req.getString("password");

			class ResetParam {
				@Size(min = 1, message = "验证码不能为空！")
				private String verifyCode;

				@Size(min = 1, max = 12, message = "密码长度必须在1-12之间！")
				private String password;

				public ResetParam(String verifyCode, String password) {
					this.verifyCode = verifyCode;
					this.password = password;
				}
			}
			
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<ResetParam>> constraintViolations = validator
					.validate(new ResetParam(verifyCode, password));
			for (ConstraintViolation<ResetParam> constraintViolation : constraintViolations) {
				return BaseResult.returnErrorMessage(constraintViolation.getMessage());
			}

			log.info("/reset/reset.do data:{}", req.toJSONString());

			String cacheVerifycode = (String) session.getAttribute("cache_reset_verifycode");
			String cacheVmail = (String) session.getAttribute("cache_reset_email");

			if (StringUtils.isEmpty(cacheVerifycode) || StringUtils.isEmpty(cacheVmail)) {
				log.error("/reset/reset.do {}", "页面已过期");
				return BaseResult.returnErrorMessage("页面已过期");
			}

			log.info("/reset/reset.do cacheVerifycode:{} cacheVmail：{}", cacheVerifycode, cacheVmail);

			if (verifyCode.equals(cacheVerifycode)) {
				int updateUser = resetService.updateUserPassword(EncryptUtil.md5Encrypt(password), cacheVmail);
				if (updateUser == 1) {
					return BaseResult.returnSuccessMessage("重置密码成功！");
				} else {
					log.error("重置密码错误！");
					return BaseResult.returnErrorMessage("重置密码错误！");
				}
			} else {
				return BaseResult.returnErrorMessage("你输入的邮箱验证码错误！");
			}
		} catch (Exception e) {
			log.error("/reset/reset.do {}", e.getMessage());
			e.printStackTrace();
		}
		return BaseResult.returnErrorMessage("重置密码失败！");
	}

}