package com.linzl.cn.mail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class SendMailUtil {
	/**
	 * Message对象将存储我们实际发送的电子邮件信息，
	 * Message对象被作为一个MimeMessage对象来创建并且需要知道应当选择哪一个JavaMail session。
	 */
	private MimeMessage message;

	/**
	 * Session类代表JavaMail中的一个邮件会话。 每一个基于JavaMail的应用程序至少有一个Session（可以有任意多的Session）。
	 * 
	 * JavaMail需要Properties来创建一个session对象。 寻找"mail.smtp.host" 属性值就是发送邮件的主机
	 * 寻找"mail.smtp.auth" 身份验证，目前免费邮件服务器都需要这一项
	 */
	private Session session;

	/***
	 * 邮件是既可以被发送也可以被收到。JavaMail使用了两个不同的类来完成这两个功能：Transport 和 Store。 Transport
	 * 是用来发送信息的，而Store用来收信。对于这的教程我们只需要用到Transport对象。
	 */
	private Transport transport;

	private String mailHost = "";
	private String sender_username = "";
	private String sender_password = "";

	private Properties properties = new Properties();

	/*
	 * 初始化方法
	 */
	public SendMailUtil(boolean debug) {
		InputStream in = SendMailUtil.class.getResourceAsStream("mail.properties");
		try {
			properties.load(in);
			// this.mailHost = properties.getProperty("mail.smtp.host");
			this.sender_username = properties.getProperty("mail.sender.username");
			this.sender_password = properties.getProperty("mail.sender.password");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Authenticator ma = new MyAuthenticator(sender_username, sender_password);
		session = Session.getInstance(properties, ma);
		session.setDebug(debug);// 开启后有调试信息
		message = new MimeMessage(session);
	}

	/**
	 * 发送邮件
	 * 
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @param receiveUser
	 *            收件人地址
	 */
	public void doSendHtmlEmail(String subject, String content, List<String> receiveUsers) {
		try {
			// 下面这个是设置发送人的Nick name
			InternetAddress from = new InternetAddress(MimeUtility.encodeWord("姓名") + " <" + sender_username + ">");
			message.setFrom(from);

			// 收件人
			List<InternetAddress> recipientsList = new ArrayList<InternetAddress>();
			for (String receiveUser : receiveUsers) {
				recipientsList.add(new InternetAddress(receiveUser));
			}
			InternetAddress[] to = recipientsList.toArray(new InternetAddress[recipientsList.size()]);
			message.setRecipients(Message.RecipientType.TO, to);// 还可以有CC、BCC

			// 邮件主题
			message.setSubject(subject);
			message.setSentDate(new Date());

			Multipart multipart = new MimeMultipart("related");// related意味着可以发送html格式的邮件

			BodyPart bodyPart = new MimeBodyPart();// 正文
			bodyPart.setDataHandler(new DataHandler(content, "text/html;charset=GBK"));
			multipart.addBodyPart(bodyPart);

			// 添加附件的内容
//			File attachment = new File("D:\\测试包.zip");
//			if (attachment != null) {
//				BodyPart attachmentBodyPart = new MimeBodyPart();
//				DataSource source = new FileDataSource(attachment);
//				attachmentBodyPart.setDataHandler(new DataHandler(source));
//
//				// MimeUtility.encodeWord可以避免文件名乱码
//				attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
//				multipart.addBodyPart(attachmentBodyPart);
//			}

			message.setContent(multipart);// 设置邮件内容对象
			// 保存邮件
			message.saveChanges();
			transport = session.getTransport("smtp");
			// smtp验证，就是你用来发邮件的邮箱用户名密码
			transport.connect();
			// 发送
			transport.sendMessage(message, message.getAllRecipients());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				} finally {
					transport = null;
				}
			}
		}
	}

	public static void main(String[] args) {
		SendMailUtil se = new SendMailUtil(true);
		List<String> receiveUsers = new ArrayList<String>();
		receiveUsers.add("995876884@qq.com");
		se.doSendHtmlEmail("欢迎新生入学主题", "入学时需要带齐证件：身份证，居住证，学生证", receiveUsers);
	}
}