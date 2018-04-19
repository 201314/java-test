package com.linzl.cn.email;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;

public class EmailClient {

	/**
	 * 发送邮件
	 * 
	 * @param info
	 * @throws Exception
	 */
	public static boolean sendEmail(EmailSenderInfo info) {
		boolean sendFlag = true;
		try {
			ImageHtmlEmail email = new ImageHtmlEmail();
			// 登录邮箱
			email.setHostName(info.getHost());
			email.setAuthentication(info.getUserName(), info.getPassword());
			// email.setSmtpPort(info.getPort());

			// 发件人
			email.setFrom(info.getUserName(), info.getNickname());

			// 邮件主题
			email.setSubject(info.getSubject());

			// 邮件内容
			email.setHtmlMsg(info.getContent());
			// 如果邮箱不支持html,则显示html纯文本
			email.setMsg(info.getContent());
			email.setCharset("UTF-8");

			email.setDataSourceResolver(new DataSourceUrlResolver(null));

			for (String receiveUser : info.getToAddresss()) {
				email.addTo(receiveUser);
			}

			for (String receiveUser : info.getBccAddresss()) {
				email.addBcc(receiveUser);
			}
			for (String receiveUser : info.getCcAddresss()) {
				email.addCc(receiveUser);
			}

			for (File file : info.getAttachFile()) {
				email.attach(file);
			}

			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
			sendFlag = false;
		}
		return sendFlag;
	}

	public static void main(String[] args) {
		List<String> toAddresss = new ArrayList<>();
		toAddresss.add("2225010489@qq.com");
		EmailSenderInfo info = new EmailSenderInfo("11", "22", toAddresss);
		sendEmail(info);
	}
}