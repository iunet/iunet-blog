package iunet.interceptor;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;

import iunet.util.DateUtil;
import iunet.util.WebUtil;

@Component
public class CommonInterceptor extends HandlerInterceptorAdapter {
	private static final Logger log = LoggerFactory.getLogger(CommonInterceptor.class);

	@Value("${cacheTime}")
	private Long cacheTime;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		log.info("CommonInterceptor requestUri:{}", request.getRequestURI());
		
		HttpSession session = request.getSession();
		
		JSONObject obj = (JSONObject) session.getAttribute("cache_user");
		if (null == obj || obj.isEmpty()) {
			log.info("CommonInterceptor 用户未登录");
			request.getRequestDispatcher("/").forward(request, response);
			return false;
		} else {
			BigDecimal state = obj.getBigDecimal("state");
			if(!state.equals(new BigDecimal(0))) {
				log.info("用户状态异常，退出系统：state：{}", state);
				WebUtil.chearCache(session);
				request.getRequestDispatcher("/").forward(request, response);
				return false;
			}
			long loginTime = DateUtil.parseDate(obj.getString("loginTime")).getTime();
			long now = System.currentTimeMillis();
			if (null == cacheTime) {
				cacheTime = 10L;
			}
			log.info("cache_user：{}, cacheTime:[{} 分钟], loginTime:{}, now:{}", obj.toJSONString(), cacheTime, DateUtil.nowStr(loginTime), DateUtil.nowStr(now));
			if (now > (loginTime + cacheTime * 1000 * 60)) {
				log.info("用户长时间[{} 分钟]未操作系统，退出系统：loginTime：{},now:{}", cacheTime,  DateUtil.nowStr(loginTime), DateUtil.nowStr(now));
				WebUtil.chearCache(session);
				request.getRequestDispatcher("/").forward(request, response);
				return false;
			}
			obj.put("loginTime", DateUtil.nowStr(now));
			session.setAttribute("cache_user", obj);
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("CommonInterceptor.afterCompletion:");
	}
}