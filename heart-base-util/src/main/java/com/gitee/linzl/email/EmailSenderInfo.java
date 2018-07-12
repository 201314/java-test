package com.gitee.linzl.email;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.mail.HtmlEmail;

public class EmailSenderInfo {

	// 邮件发送服务器域名
	private String host;
	// 邮箱端口
	private int port;

	// 登陆邮件发送服务器的用户名和密码
	private String userName;
	private String password;
	// 邮件发件人
	private String nickname;

	// 邮件主题
	private String subject;
	// 邮件的文本内容
	private String content;

	// 收件人邮件地址
	private List<String> toAddresss = Collections.emptyList();

	// 抄送人邮件地址
	private List<String> ccAddresss = Collections.emptyList();

	// 密送人邮件地址
	private List<String> bccAddresss = Collections.emptyList();

	// 邮件附件
	private List<File> attachFile = Collections.emptyList();

	/**
	 * 邮件内容中img src 图片内容的相对url,有此时则src可以不写全路径，
	 * 
	 * 如http://portal.goetui.com/wp-content/themes/bigtea/images/logo.png
	 * 
	 * 则设置relativeUrl=http://portal.goetui.com
	 * 
	 * img src = "wp-content/themes/bigtea/images/logo.png"
	 */
	private String relativeUrl;

	/**
	 * 邮件内嵌图片的cid,随机生成
	 * 
	 * @return
	 */
	public static final String randomCid() {
		final String cid = RandomStringUtils.randomAlphabetic(HtmlEmail.CID_LENGTH).toLowerCase();
		return cid;
	}

	public EmailSenderInfo(String subject, String content, List<String> toAddresss) {
		this.subject = subject;
		this.content = content;
		this.toAddresss = toAddresss;
	}

	public String getHost() {
		return host == null ? EmailConstants.HOST : host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port <= 0 ? EmailConstants.PORT : port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 登陆邮件发送服务器的用户名
	 * 
	 * @return
	 */
	public String getUserName() {
		return userName == null ? EmailConstants.USERNAME : userName;
	}

	/**
	 * 登陆邮件发送服务器的用户名
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 登陆邮件发送服务器的密码
	 * 
	 * @return
	 */
	public String getPassword() {
		return password == null ? EmailConstants.PWD : password;
	}

	/**
	 * 登陆邮件发送服务器的密码
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 发送者昵称
	 * 
	 * @return
	 */
	public String getNickname() {
		return nickname == null ? EmailConstants.NICKNAME : nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * 邮件主题
	 * 
	 * @return
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * 邮件主题
	 * 
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	/**
	 * 邮件的文本内容
	 * 
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 收件人邮件地址
	 * 
	 * @param toAddresss
	 */
	public void setToAddresss(List<String> toAddresss) {
		this.toAddresss = toAddresss;
	}

	public void setToAddresss(String toAddresss) {
		this.toAddresss.add(toAddresss);
	}

	/**
	 * 收件人邮件地址
	 * 
	 * @return
	 */
	public List<String> getToAddresss() {
		return toAddresss;
	}

	/**
	 * 抄送人邮件地址
	 * 
	 * @param ccAddresss
	 */
	public void setCcAddresss(List<String> ccAddresss) {
		this.ccAddresss = ccAddresss;
	}

	public void setCcAddresss(String ccAddresss) {
		this.ccAddresss.add(ccAddresss);
	}

	/**
	 * 抄送人邮件地址
	 * 
	 * @return
	 */
	public List<String> getCcAddresss() {
		return ccAddresss;
	}

	/**
	 * 密送人邮件地址
	 * 
	 * @param bccAddresss
	 */
	public void setBccAddresss(List<String> bccAddresss) {
		this.bccAddresss = bccAddresss;
	}

	public void setBccAddresss(String bccAddresss) {
		this.bccAddresss.add(bccAddresss);
	}

	/**
	 * 密送人邮件地址
	 * 
	 * @return
	 */
	public List<String> getBccAddresss() {
		return bccAddresss;
	}

	/**
	 * 邮件附件
	 * 
	 * @param attachFile
	 */
	public void setAttachFile(List<File> attachFile) {
		this.attachFile = attachFile;
	}

	public void setAttachFile(File attachFile) {
		this.attachFile.add(attachFile);
	}

	/**
	 * 邮件附件
	 * 
	 * @return
	 */
	public List<File> getAttachFile() {
		return attachFile;
	}

	public String getRelativeUrl() {
		return relativeUrl;
	}

	public void setRelativeUrl(String relativeUrl) {
		this.relativeUrl = relativeUrl;
	}

}
