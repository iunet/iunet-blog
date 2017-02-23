package iunet.controller;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.octo.captcha.service.image.ImageCaptchaService;

import iunet.model.BaseResult;
import iunet.model.proj.UmLoginLog;
import iunet.model.proj.UmUser;
import iunet.service.LoginService;
import iunet.util.DateUtil;
import iunet.util.EncryptUtil;
import iunet.util.SerializerUtil;
import iunet.util.StringUtil;
import iunet.util.WebUtil;

@Controller
@RequestMapping("/login")
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private ImageCaptchaService imageCaptchaService;

	@Autowired
	private LoginService loginService;

	class LoginParam {
		@Size(min = 1, message = "用户名不能为空！")
		private String userName;

		@Size(min = 1, max = 12, message = "密码长度必须在1-12之间！")
		private String password;

		@Size(min = 1, message = "验证码不能为空！")
		private String verifyCode;

		public LoginParam(String userName, String password, String verifyCode) {
			this.userName = userName;
			this.password = password;
			this.verifyCode = verifyCode;
		}
	}

	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult userLogin(@RequestBody JSONObject req, HttpServletRequest request) {
		log.info("/login/userLogin.do start.. time：{}", DateUtil.nowStr());
		HttpSession session = request.getSession();
		try {

			String userName = req.getString("userName");
			String password = req.getString("password");
			String verifyCode = req.getString("verifyCode");

			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<LoginParam>> constraintViolations = validator
					.validate(new LoginParam(userName, password, verifyCode));
			for (ConstraintViolation<LoginParam> constraintViolation : constraintViolations) {
				return BaseResult.returnErrorMessage(constraintViolation.getMessage());
			}

			if (!imageCaptchaService.validateResponseForID(session.getId(), verifyCode)) {
				log.info("用户输入的验证图形验证码有误！login verifyCode：{}", verifyCode);
				return BaseResult.returnErrorMessage(1, "您输入的验证码不正确！");
			}

			log.info("/login/userLogin.do data:{}", req.toJSONString());

			UmUser user = loginService.selectUserByLoginName(userName);
			if (null == user) {
				return BaseResult.returnErrorMessage("账号或密码错误！");
			}

			String md5_password = user.getPassword();
			if (StringUtils.isEmpty(md5_password) || !md5_password.equals(EncryptUtil.md5Encrypt(password))) {
				return BaseResult.returnErrorMessage("密码错误！");
			}

			BigDecimal state = user.getState();
			if (null != state && state.compareTo(new BigDecimal(1)) == 0) {
				return BaseResult.returnErrorMessage("账号未激活或冻结！");
			}

			BigDecimal id = user.getId();
			List<String> groupIds = loginService.selectGroupIdsByUserId(id);
			List<String> roleIds = loginService.selectRoleIdsByUserId(id);

			user.setLoginTime(DateUtil.nowStr());
			Set<String> propertySet = new HashSet<String>();
			propertySet.add("password");
			JSONObject res = SerializerUtil.skipByName(JSONObject.toJSON(user), propertySet);
			res.put("UserGroups", groupIds);
			res.put("UserRoles", roleIds);
			session.setAttribute("cache_user", res);
			session.setAttribute("cache_user_id", id);

			// 添加登录日志
			UmLoginLog loginlog = new UmLoginLog();
			loginlog.setId(StringUtil.getRandom(32));
			loginlog.setUserId(id);
			loginlog.setDeviceName(WebUtil.getDeviceName(request));
			loginlog.setHostName(WebUtil.getLocalName(request));
			loginlog.setIpAddress(WebUtil.getRemoteAddr(request));
			loginlog.setLoginName(user.getUserName());
			loginlog.setLoginTime(DateUtil.Timestamp());
			loginlog.setLogoutTime(DateUtil.Timestamp());
			loginlog.setState(new BigDecimal(0));
			loginService.insertLoginLog(loginlog);
			log.info("用户登录成功：{}", res.toJSONString());
			return BaseResult.returnSuccessMessage(res, "用户登录成功！");
		} catch (Exception e) {
			log.error("/login/userLogin.do 用户登录失败！{}", e.getMessage());
			e.printStackTrace();
		}
		return BaseResult.returnErrorMessage("用户登录失败！");
	}

}