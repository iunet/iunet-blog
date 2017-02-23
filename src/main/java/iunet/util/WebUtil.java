package iunet.util;

import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import iunet.service.LoginService;
import iunet.service.impl.LoginServiceImpl;

public class WebUtil {
	private static final Logger log = LoggerFactory.getLogger(WebUtil.class);

	static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"
			+ "|windows (phone|ce)|blackberry"
			+ "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp" 
			+ "|laystation portable)|nokia|fennec|htc[-_]"
			+ "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
	static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
	
	static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
	static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);

	/**
	 * 清除所有session
	 * @param session
	 * @throws Exception
	 */
	public static void chearCache(HttpSession session) throws Exception {
		try {
			BigDecimal user_id = (BigDecimal) session.getAttribute("cache_user_id");
			Enumeration enumeration = session.getAttributeNames();
			while (enumeration.hasMoreElements()) {
				String key = enumeration.nextElement().toString();
				if(null == key) {
					continue;
				}
				if("cache_user".equals(key) && null != user_id) {
					LoginService userService = new LoginServiceImpl();
					userService.updateLoginOutTime(user_id);
				}
				log.info("WebUtil chearCache key:{}", key);
				session.removeAttribute(key);
			}
		} catch (Exception e) {
			log.error("chearCache error {}", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 获取客户端IP
	 * @param request
	 * @return
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}
	
	public static String getLocalPort(HttpServletRequest request) {
		return String.valueOf(request.getLocalPort());
	}

	/**
	 * 检测是否是移动设备访问
	 * @param userAgent
	 * @return
	 */
	public static boolean check(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent").toLowerCase();
		if (null == userAgent) {
			userAgent = "";
		}
		Matcher matcherPhone = phonePat.matcher(userAgent);
		Matcher matcherTable = tablePat.matcher(userAgent);
		if (matcherPhone.find() || matcherTable.find()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取设备名称
	 * @param userAgent
	 * @return
	 */
	public static String getDeviceName(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent").toLowerCase();
		if (null == userAgent) {
			userAgent = "";
		}
		Matcher matcherPhone = phonePat.matcher(userAgent);
		Matcher matcherTable = tablePat.matcher(userAgent);
		if (matcherPhone.find()) {
			return matcherPhone.group();
		} else if (matcherTable.find()) {
			return matcherTable.group();
		} else {
			return "pc";
		}
	}

	public static String getLocalName(HttpServletRequest request) {
		return request.getLocalName();
	}
}
