package iunet.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;

import iunet.util.Constant;
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
		
		JSONObject obj = (JSONObject) session.getAttribute(Constant.CACHE_USER);
		if (null == obj || obj.isEmpty()) {
			log.info("CommonInterceptor 用户未登录");
			request.getRequestDispatcher("/").forward(request, response);
			return false;
		} else {
			long login_time = obj.getLongValue("login_time");
			long now = System.currentTimeMillis();
			if (null == cacheTime) {
				cacheTime = 600L;
			}
			log.info(Constant.CACHE_USER + "：{}, login_time:{}, cacheTime:{}, now:{}", obj.toJSONString(), login_time,
					cacheTime, now);
			if (now > (login_time + cacheTime * 1000)) {
				WebUtil.chearCache(session);
				request.getRequestDispatcher("/").forward(request, response);
				return false;
			}
			obj.put("login_time", now);
			session.setAttribute(Constant.CACHE_USER, obj);
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("CommonInterceptor.afterCompletion:");
	}
}