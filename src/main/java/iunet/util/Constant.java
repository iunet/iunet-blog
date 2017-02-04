package iunet.util;

public class Constant {
	// cache
	public final static String CACHE_USER = "cache_user";
	public final static String CACHE_USER_ID = "cache_user_id";
	public final static String CACHE_RESET_VERIFYCODE = "cache_reset_verifycode";
	public final static String CACHE_RESET_EMAIL = "cache_reset_email";
	
	// 用户相关异常
	
	public final static String PASSWORD_ERROR = "密码错误！";
	public final static String ACCOUNT_OR_PASSWORD_ERROR = "账号或密码错误！";
	public final static String ACCOUNT_HAS_BEEN_FROZEN = "账号未激活或冻结！";
	
	public final static String LOGIN_SUCCESS = "用户登录成功！";
	public final static String LOGIN_ERROR = "用户登录失败！";
	
	public final static String USER_INFO_FAILURE = "用户信息已失效，请重新登录！";
	public final static String USER_INFO_NOT_MATCH = "用户信息不符，请重新登录！";
	
	public final static String EMAIL_HAS_EMPLOY = "邮箱已被注册！";
	public final static String REGISTER_SUCCESS = "用户注册成功！";
	public final static String REGISTER_ERROR = "用户注册失败！";
	
	public final static String EMAIL_HAS_NOT_EMPLOY = "该邮箱尚未注册！";
	public final static String RESET_SUCCESS = "重置密码成功！";
	public final static String RESET_ERROR = "重置密码失败！";
	
	//public
	public final static String SYSTEM_ERROR = "系统错误！";
	public final static String PAGE_OVERDUE = "页面已过期！";
	public final static String PARAMETER_IS_EMPTY = "请求参数为空！";
	public final static String REQUEST_NO_RETURN  = "请求无返回！";
	public final static String VERIFICATION_CODE_ERROR = "您输入的验证码不正确！";
	
	public final static String SEND_EMAIL_VERIFYCODE_SUCCESS = "发送验证码至用户邮箱成功！";
	public final static String SEND_EMAIL_VERIFYCODE_ERROR = "发送验证码至用户邮箱失败！";
	
	//table
	public final static String UM_USER = "UM_USER";
	public final static String UM_ROLE = "UM_ROLE";
	public final static String UM_USERGROUP = "UM_USERGROUP";
}
