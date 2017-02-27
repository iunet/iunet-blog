package iunet.model;

import java.util.Map;

public class Email {
	private String emailHost;//发件人邮箱服务器
	private String emailFrom;//发件人邮箱
	private String emailUserName;//发件人用户名
	private String emailPassword;//发件人密码
	private String toEmails;//收件人邮箱，多个邮箱以“;”分隔
	private String subject;//邮件主题
	private String content;//邮件内容
	private Map<String, String> pictures;//邮件中的图片，为空时无图片。map中的key为图片ID，value为图片地址
	private Map<String, String> attachments;//邮件中的附件，为空时无附件。map中的key为附件ID，value为附件地址
	private String fromAddress;// 发送人地址1个
	private String toAddresses;// 接收人地址,可以为很多个，每个地址之间用";"分隔，比方说450065208@qq.com;lpf@sina.com
	private String[] attachFileNames;// 附件
	
	public String getEmailHost() {
		return emailHost;
	}
	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}
	public String getEmailFrom() {
		return emailFrom;
	}
	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}
	public String getEmailUserName() {
		return emailUserName;
	}
	public void setEmailUserName(String emailUserName) {
		this.emailUserName = emailUserName;
	}
	public String getEmailPassword() {
		return emailPassword;
	}
	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}
	public String getToEmails() {
		return toEmails;
	}
	public void setToEmails(String toEmails) {
		this.toEmails = toEmails;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Map<String, String> getPictures() {
		return pictures;
	}
	public void setPictures(Map<String, String> pictures) {
		this.pictures = pictures;
	}
	public Map<String, String> getAttachments() {
		return attachments;
	}
	public void setAttachments(Map<String, String> attachments) {
		this.attachments = attachments;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getToAddresses() {
		return toAddresses;
	}
	public void setToAddresses(String toAddresses) {
		this.toAddresses = toAddresses;
	}
	public String[] getAttachFileNames() {
		return attachFileNames;
	}
	public void setAttachFileNames(String[] attachFileNames) {
		this.attachFileNames = attachFileNames;
	}
}