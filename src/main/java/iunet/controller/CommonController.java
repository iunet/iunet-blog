package iunet.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import iunet.model.BaseResult;
import iunet.util.DateUtil;
import iunet.util.WebUtil;  

@Controller
@RequestMapping("/common")
public class CommonController {
	private static final Logger log = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	
	@RequestMapping(value = "/getVerifyCode", method = RequestMethod.GET)
	@ResponseBody
    public void getVerifyCode(HttpServletRequest request , HttpServletResponse response) throws Exception{
		log.info("/common/getVerifyCode.do start.. time：{}", DateUtil.nowStr());
        byte[] captchaChallengeAsJpeg = null;  
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();  
        try {  
            String captchaId = request.getSession().getId();  
            BufferedImage challenge = imageCaptchaService.getImageChallengeForID(captchaId, request.getLocale());  
            JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(jpegOutputStream);  
            jpegEncoder.encode(challenge);  
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        response.setHeader("Cache-Control", "no-store");    
        response.setHeader("Pragma", "no-cache");    
        response.setDateHeader("Expires", 0);    
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        try {
        	captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
            responseOutputStream.write(captchaChallengeAsJpeg);
        	responseOutputStream.flush(); 
        } catch (Exception e) {
			log.error("获取图形验证码失败！");
			throw new Exception("获取图形验证码失败！");
		} finally { 
        	responseOutputStream.close();
        } 
    }
	
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult getUserInfo(@RequestBody JSONObject req, HttpServletRequest request) {
		log.info("/common/getUserInfo.do start.. time:{}", DateUtil.nowStr());
		HttpSession session = request.getSession();
		try {
			String userName = req.getString("userName");
			if (StringUtils.isEmpty(userName)) {
				WebUtil.chearCache(session);
				return BaseResult.returnErrorMessage(1, "用户信息已失效，请重新登录！");
			}
			log.info("/common/getUserInfo.do getUserInfo:{}", req.toJSONString());
			JSONObject res = (JSONObject) session.getAttribute("cache_user");
			if (null == res) {
				WebUtil.chearCache(session);
				return BaseResult.returnErrorMessage("用户信息已失效，请重新登录！");
			}
			if (!userName.equals(res.getString("loginName"))) {
				WebUtil.chearCache(session);
				return BaseResult.returnErrorMessage("用户信息不符，请重新登录！");
			}
			log.info("从缓存获取用户信息：res:{}", res.toJSONString());
			return BaseResult.returnSuccessMessage(res, "用户登录成功！");
		} catch (Exception e) {
			log.error("/common/getUserInfo.do error:{}", e.getMessage());
			e.printStackTrace();
		}
		return BaseResult.returnErrorMessage("用户信息已失效，请重新登录！");
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("/common/logout.do start.. time：{}", DateUtil.nowStr());
		WebUtil.chearCache(request.getSession());
		request.getRequestDispatcher("/").forward(request, response);
	}
}