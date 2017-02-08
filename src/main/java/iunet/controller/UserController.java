package iunet.controller;

import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import iunet.model.Email;
import iunet.model.um.LoginLog;
import iunet.model.um.User;
import iunet.service.CommonService;
import iunet.service.UserService;
import iunet.util.BaseResult;
import iunet.util.Constant;
import iunet.util.DateUtil;
import iunet.util.EmailUtil;
import iunet.util.EncryptUtil;
import iunet.util.HtmlUtil;
import iunet.util.SerializerUtil;
import iunet.util.StringUtil;
import iunet.util.WebUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private ImageCaptchaService imageCaptchaService;
	
	@Value("${webContext}")
	private String webContext;

	@RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult getUserInfo(@RequestBody JSONObject req, HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			log.info("/user/getUserInfo.do start..");
			String userName = req.getString("userName");
			if (StringUtils.isEmpty(userName)) {
				WebUtil.chearCache(session);
				return BaseResult.returnErrorMessage(1, Constant.PARAMETER_IS_EMPTY);
			}
			log.info("/user/getUserInfo.do getUserInfo:{}", req.toJSONString());
			JSONObject res = (JSONObject) session.getAttribute(Constant.CACHE_USER);
			if (null == res) {
				WebUtil.chearCache(session);
				return BaseResult.returnErrorMessage(Constant.USER_INFO_FAILURE);
			}
			if (!userName.equals(res.getString("login_name"))) {
				WebUtil.chearCache(session);
				return BaseResult.returnErrorMessage(Constant.USER_INFO_NOT_MATCH);
			}
			log.info("从缓存获取用户信息：res:{}", res.toJSONString());
			return BaseResult.returnSuccessMessage(res, Constant.LOGIN_SUCCESS);
		} catch (Exception e) {
			if (null == req || req.isEmpty()) {
				log.error("/user/getUserInfo.do data:{},{}", Constant.PARAMETER_IS_EMPTY, e.getMessage());
			} else {
				log.error("/user/getUserInfo.do data:{},{}", req.toJSONString(), e.getMessage());
			}
			e.printStackTrace();
		}
		return BaseResult.returnErrorMessage(Constant.USER_INFO_FAILURE);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult login(@RequestBody JSONObject req, HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			String userName = req.getString("userName");
			String password = req.getString("password");
			String verifyCode = req.getString("verifyCode");

			if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(verifyCode)) {
				return BaseResult.returnErrorMessage(Constant.PARAMETER_IS_EMPTY);
			}

			try {
				if (!imageCaptchaService.validateResponseForID(session.getId(), verifyCode)) {
					log.info("用户输入的验证图形验证码有误！login verifyCode：{}", verifyCode);
					return BaseResult.returnErrorMessage(1, Constant.VERIFICATION_CODE_ERROR);
				}
			} catch (Exception e) {
				log.error("校验图形验证码失败");
				return BaseResult.returnErrorMessage(Constant.SYSTEM_ERROR);
			}

			log.info("/user/login.do data:{}", req.toJSONString());

			User user = userService.queryUserByLoginName(userName);
			if (null == user) {
				return BaseResult.returnErrorMessage(Constant.ACCOUNT_OR_PASSWORD_ERROR);
			}

			String md5_password = user.getPassword();
			if (StringUtils.isEmpty(md5_password) || !md5_password.equals(EncryptUtil.md5Encrypt(password))) {
				return BaseResult.returnErrorMessage(Constant.PASSWORD_ERROR);
			}

			Integer state = user.getState();
			if (null != state && state == 1) {
				return BaseResult.returnErrorMessage(Constant.ACCOUNT_HAS_BEEN_FROZEN);
			}

			int id = user.getId();
			List<String> groupIds = userService.queryGroupIdsByUserId(id);
			List<String> roleIds = userService.queryRoleIdsByUserId(id);

			user.setLogin_time(System.currentTimeMillis());
			Set<String> propertySet = new HashSet<String>();
			propertySet.add("password");
			JSONObject res = SerializerUtil.skipByName(JSONObject.toJSON(user), propertySet);
			res.put("UserGroups", groupIds);
			res.put("UserRoles", roleIds);
			session.setAttribute(Constant.CACHE_USER, res);
			session.setAttribute(Constant.CACHE_USER_ID, id);

			// 添加登录日志
			LoginLog loginlog = new LoginLog();
			loginlog.setId(StringUtil.getRandom(32));
			loginlog.setUser_id(user.getId());
			loginlog.setDevice_name(WebUtil.getDeviceName(request));
			loginlog.setHost_name(WebUtil.getLocalName(request));
			loginlog.setIp_address(WebUtil.getRemoteAddr(request));
			loginlog.setLogin_name(user.getUser_name());
			loginlog.setLogin_time(DateUtil.Timestamp());
			loginlog.setLogout_time(DateUtil.Timestamp());
			loginlog.setState(0);
			userService.insertLoginLog(loginlog);

			return BaseResult.returnSuccessMessage(res, Constant.LOGIN_SUCCESS);
		} catch (Exception e) {
			if (null == req || req.isEmpty()) {
				log.error("/user/login.do data:{},{}", Constant.PARAMETER_IS_EMPTY, e.getMessage());
			} else {
				log.error("/user/login.do data:{},{}", req.toJSONString(), e.getMessage());
			}
			e.printStackTrace();
		}
		return BaseResult.returnErrorMessage(Constant.LOGIN_ERROR);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public BaseResult register(@RequestBody JSONObject req, HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			String userName = req.getString("userName");
			String nickname = req.getString("nickname");
			String password = req.getString("password");
			String email = req.getString("email");
			String verifyCode = req.getString("verifyCode");

			if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)
					|| StringUtils.isEmpty(email) || StringUtils.isEmpty(verifyCode)) {
				return BaseResult.returnErrorMessage(Constant.PARAMETER_IS_EMPTY);
			}

			try {
				if (!imageCaptchaService.validateResponseForID(session.getId(), verifyCode)) {
					log.info("用户输入的验证图形验证码有误！register verifyCode：{}", verifyCode);
					return BaseResult.returnErrorMessage(1, Constant.VERIFICATION_CODE_ERROR);
				}
			} catch (Exception e) {
				log.error("校验图形验证码失败");
				return BaseResult.returnErrorMessage(Constant.SYSTEM_ERROR);
			}

			if (userService.emailHasRegisterOccupy(email)) {
				log.error(Constant.EMAIL_HAS_EMPLOY);
				return BaseResult.returnErrorMessage(Constant.EMAIL_HAS_EMPLOY);
			}

			log.info("/user/register.do data:{}", req.toJSONString());

			User user = new User();
			user.setId(commonService.getNewId(Constant.UM_USER));
			user.setLogin_name(userName);
			user.setUser_name(nickname);
			user.setPassword(EncryptUtil.md5Encrypt(password));
			user.setEmail(email);
			user.setState(0);
			user.setCreate_time(DateUtil.Timestamp());
			String data = ((JSONObject) JSONObject.toJSON(user)).toJSONString();
			
			String uuid = StringUtil.getRandom(32);
			String href = "http://" + WebUtil.getRemoteAddr(request) + ":" + WebUtil.getLocalPort(request) + "/" + webContext + "/user/activation.do?uuid=" + uuid;
			try {
				Email mail = new Email();
				mail.setSubject("iunet-web 账户激活");
				mail.setToEmails(email);
				mail.setContent("<html><body>" 
						+ "<h2>亲爱的用户：</h2><br />" 
						+ "你好！<br />"
						+ "&nbsp&nbsp&nbsp&nbsp感谢您注册iunet-web。您正在进行账户注册，请在尽快点击下面链接激活您的账户：" 
						+ "<a href='" + href + "' target='_blank'>点击激活账户</a><br />"
						+ "&nbsp&nbsp&nbsp&nbsp如非本人操作，请忽略此邮件，由此给您带来的不便请谅解！<br />" 
						+ "<br /><br /><br /><br />"
						+ "&nbsp&nbsp&nbsp&nbspiunet "
						+ DateUtil.formatDateString(new Date(), "yyyy年MM月dd日 HH:mm:ss")
						+ "</body></html>");
				
				Map<String, Object> insertMap = new HashMap<String, Object>();
				insertMap.put("uuid", uuid);
				insertMap.put("email", email);
				insertMap.put("state", 0);
				insertMap.put("data", data);
				int inser = userService.insertActivationByUUID(insertMap);
				if (inser == 1) {
					log.info("发送激活邮件成功  mail：{}, 激活链接:{}, {}", email, href);
					EmailUtil.sendEmail(mail);
					if (userService.emailHasRegisterOccupy(email)) {
						log.error(Constant.EMAIL_HAS_EMPLOY);
						return BaseResult.returnErrorMessage(Constant.EMAIL_HAS_EMPLOY);
					}
					return BaseResult.returnSuccessMessage(Constant.REGISTER_SUCCESS);
				} else {
					log.error("用户注册失败：data：{}", data);
					return BaseResult.returnErrorMessage(Constant.REGISTER_ERROR);
				}
			} catch (Exception e) {
				log.error("发送激活邮件失败  mail：{}, 激活链接:{}, {}", email, href, e.getMessage());
				e.printStackTrace();
				return BaseResult.returnErrorMessage("发送激活邮件失败!");
			}
		} catch (Exception e) {
			if (null == req || req.isEmpty()) {
				log.error("/user/register.do data:{},{}", Constant.PARAMETER_IS_EMPTY, e.getMessage());
			} else {
				log.error("/user/register.do data:{},{}", req.toJSONString(), e.getMessage());
			}
			e.printStackTrace();
		}
		return BaseResult.returnErrorMessage(Constant.REGISTER_ERROR);
	}

	@RequestMapping(value = "/resetSendEmail", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult resetSendEmail(@RequestBody JSONObject req, HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			String email = req.getString("email");
			String verifyCode = req.getString("verifyCode");

			if (StringUtils.isEmpty(email) || StringUtils.isEmpty(verifyCode)) {
				return BaseResult.returnErrorMessage(Constant.PARAMETER_IS_EMPTY);
			}

			try {
				if (!imageCaptchaService.validateResponseForID(session.getId(), verifyCode)) {
					log.info("用户输入的验证图形验证码有误！resetSendEmail verifyCode：{}", verifyCode);
					return BaseResult.returnErrorMessage(1, Constant.VERIFICATION_CODE_ERROR);
				}
			} catch (Exception e) {
				log.error("校验图形验证码失败");
				return BaseResult.returnErrorMessage(Constant.SYSTEM_ERROR);
			}
			log.info("/user/resetSendEmail.do data:{}", req.toJSONString());

			if (!userService.emailHasOccupy(email)) {
				log.error(Constant.EMAIL_HAS_NOT_EMPLOY);
				return BaseResult.returnErrorMessage(Constant.EMAIL_HAS_NOT_EMPLOY);
			}

			String reset_verifycode = StringUtil.getRandom(12);

			log.info("resetSendEmail reset_verifycode：{}", reset_verifycode);

			try {
				Email mail = new Email();
				mail.setSubject("iunet-web 重置密码");
				mail.setToEmails(email);
				mail.setContent("<html><body>" 
						+ "<h2>亲爱的用户：</h2><br />" 
						+ "你好！<br />"
						+ "&nbsp&nbsp&nbsp&nbsp感谢您使用iunet-web。您正在进行邮箱验证，请在验证码输入框中输入此次验证码：<span style='color: red;font-weight: bold;'>" + reset_verifycode + "</span> 以完成验证。<br />"
						+ "&nbsp&nbsp&nbsp&nbsp如非本人操作，请忽略此邮件，由此给您带来的不便请谅解！<br />" 
						+ "<br /><br /><br /><br />"
						+ "&nbsp&nbsp&nbsp&nbspiunet " + DateUtil.formatDateString(new Date(), "yyyy年MM月dd日 HH:mm:ss")
						+ "</body></html>");
				EmailUtil.sendEmail(mail);

				session.setAttribute(Constant.CACHE_RESET_VERIFYCODE, reset_verifycode);
				session.setAttribute(Constant.CACHE_RESET_EMAIL, email);
				return BaseResult.returnSuccessMessage(Constant.SEND_EMAIL_VERIFYCODE_SUCCESS);
			} catch (Exception e) {
				log.error(Constant.SEND_EMAIL_VERIFYCODE_ERROR + " mail：{}, 验证码:{}, {}", JSONObject.toJSON(email),
						reset_verifycode, e.getMessage());
				e.printStackTrace();
				return BaseResult.returnErrorMessage(Constant.SEND_EMAIL_VERIFYCODE_ERROR);
			}
		} catch (Exception e) {
			if (null == req || req.isEmpty()) {
				log.error("/user/resetSendEmail.do data:{},{}", Constant.PARAMETER_IS_EMPTY, e.getMessage());
			} else {
				log.error("/user/resetSendEmail.do data:{},{}", req.toJSONString(), e.getMessage());
			}
			e.printStackTrace();
		}
		return BaseResult.returnErrorMessage(Constant.SEND_EMAIL_VERIFYCODE_ERROR);
	}

	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult reset(@RequestBody JSONObject req, HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			String verifyCode = req.getString("verifyCode");
			String password = req.getString("password");

			if (StringUtils.isEmpty(verifyCode) || StringUtils.isEmpty(password)) {
				return BaseResult.returnErrorMessage(Constant.PARAMETER_IS_EMPTY);
			}
			log.info("/user/reset.do data:{}", req.toJSONString());

			String reset_verifycode = (String) session.getAttribute(Constant.CACHE_RESET_VERIFYCODE);
			String reset_email = (String) session.getAttribute(Constant.CACHE_RESET_EMAIL);

			if (StringUtils.isEmpty(reset_verifycode) || StringUtils.isEmpty(reset_email)) {
				log.error("/user/reset.do {}", Constant.PAGE_OVERDUE);
				return BaseResult.returnErrorMessage(Constant.PAGE_OVERDUE);
			}

			log.info("/user/reset.do reset_verifycode:{}", reset_verifycode);

			if (verifyCode.equals(reset_verifycode)) {
				int userId = userService.queryUserIdByEmail(reset_email);
				User user = new User();
				user.setId(userId);
				user.setPassword(EncryptUtil.md5Encrypt(password));
				user.setUpdate_time(DateUtil.Timestamp());

				int update = userService.updateUser(user);
				if (update == 1) {
					return BaseResult.returnSuccessMessage(Constant.RESET_SUCCESS);
				} else {
					log.error(Constant.RESET_ERROR + "：{}", ((JSONObject) JSONObject.toJSON(user)).toJSONString());
					return BaseResult.returnErrorMessage(Constant.RESET_ERROR);
				}
			} else {
				return BaseResult.returnErrorMessage(Constant.VERIFICATION_CODE_ERROR);
			}
		} catch (Exception e) {
			if (null == req || req.isEmpty()) {
				log.error("/user/reset.do data:{},{}", Constant.PARAMETER_IS_EMPTY, e.getMessage());
			} else {
				log.error("/user/reset.do data:{},{}", req.toJSONString(), e.getMessage());
			}
			e.printStackTrace();
		}
		return BaseResult.returnErrorMessage(Constant.RESET_ERROR);
	}

	@RequestMapping(value = "/activation", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public void activation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resStr = "";
		String filePath = "/vm/sweetalert.vm";
		String uuid = request.getParameter("uuid");
		if (StringUtils.isEmpty(uuid)) {
			resStr = HtmlUtil.addErrorActivation(filePath);
		} else {
			String userData = userService.queryActivationDataByUUID(uuid);
			if(StringUtils.isEmpty(userData)) {
				resStr = HtmlUtil.addErrorActivation2(filePath);
			} else {
				User user = JSONObject.toJavaObject((JSONObject) JSONObject.parse(userData), User.class);
				try {
					userService.insertUser(user);
					userService.updateActivationByUUID(uuid);
					resStr = HtmlUtil.addSuccessActivation(filePath);
				} catch (Exception e) {
					//插入系统错误表
					log.error("根据UUID 更新账户激活状态 updateActivationByUUID 失败！ userData:{}, uuid:{}", userData, uuid);
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
			log.error("-----------------responseOutputStream.write(); error-----------------");
		} finally {
			responseOutputStream.close();
			log.info("-----------------activation 账户激活-----------------");
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		WebUtil.chearCache(request.getSession());
		request.getRequestDispatcher("/").forward(request, response);
	}
}