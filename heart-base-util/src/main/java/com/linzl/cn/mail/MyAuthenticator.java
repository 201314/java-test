package com.linzl.cn.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthenticator extends Authenticator {
	private String user;
	private String pwd;

	public MyAuthenticator(String user, String pwd) {
		this.user = user;
		this.pwd = pwd;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, pwd);
	}
}
