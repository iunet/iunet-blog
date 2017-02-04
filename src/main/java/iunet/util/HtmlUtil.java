package iunet.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HtmlUtil {
	
	private static final Logger log = LoggerFactory.getLogger(HtmlUtil.class);
	
	private static String webContext;
	
	@Value("${webContext}")
	public void setWebContext(String webContext) {
		HtmlUtil.webContext = webContext;
	}
	
	public static Document readHtmlFile(String filePath) {
		log.info("readHtmlFile filePath:{}", filePath);
		Document doc = new Document("");
		try {
			doc = Jsoup.parse(HtmlUtil.class.getResourceAsStream(filePath), "UTF-8", "");
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		} finally {
			return doc;
		}
	}
	
	public static String addErrorActivation(String filePath) {
		Document doc = readHtmlFile(filePath);
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("window.onload = function () {swal({title: '账户激活失败！',text: '请联系客户！',type: 'error',confirmButtonText: '确定'});}");
		sb.append("</script>");
		doc.body().prepend(sb.toString());
		return doc.toString();
	}
	
	public static String addErrorActivation2(String filePath) {
		Document doc = readHtmlFile(filePath);
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("window.onload = function () {swal({title: '系统错误',text: '您已激活或尚未注册',type: 'error',confirmButtonText: '确定'}, function () {window.top.location.href = '/"+webContext+"'});}");
		sb.append("</script>");
		doc.body().prepend(sb.toString());
		return doc.toString();
	}
	
	public static String addSuccessActivation(String filePath) {
		Document doc = readHtmlFile(filePath);
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("window.onload = function () {swal({title: '账户激活成功！',text: '',type: 'success',confirmButtonText: '确定'}, function () {window.top.location.href = '/"+webContext+"'});}");
		sb.append("</script>");
		doc.body().prepend(sb.toString());
		return doc.toString();
	}
}
