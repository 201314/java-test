package com.gitee.linzl.secure;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.gitee.linzl.crypto.message.SHA256Util;

/**
 * 后面可考虑使用 apache shiro
 * 
 * @author linzl
 *
 * @creatDate 2016年11月2日
 */
public class SHA256UtilTest {

	@Test
	public void encrypt() {
		String randomText = SHA256Util.getRandomSalt();
		String pass = SHA256Util.encrypt("我是用户的密码进行加密", randomText);
		boolean flag = false;
		try {
			// 通过用户再次输入原来的密码，进行校验
			flag = SHA256Util.equalsCipher("我是用户的密码进行加密", pass, randomText);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (flag) {
			System.out.println("密码相同");
		}
	}

}
