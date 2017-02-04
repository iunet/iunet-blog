package iunet.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.octo.captcha.service.image.ImageCaptchaService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;  

@Controller
@RequestMapping("/common")
public class CommonController {
	private static final Logger log = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	
	@RequestMapping(value = "/getVerifyCode", method = RequestMethod.GET)
	@ResponseBody
    public void getVerifyCode(HttpServletRequest request , HttpServletResponse response) throws Exception{  
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
        } finally { 
        	responseOutputStream.close();
        	log.info("getVerifyCode 获取图形验证码-----------------");
        } 
    }
}