package com.gzzq.secure;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.linzl.cn.codec.sha.SHA256Secure;

/**
 * 后面可考虑使用 apache shiro
 * 
 * @author linzl
 *
 * @creatDate 2016年11月2日
 */
public class SHA256SecureTest {

	@Test
	public void encrypt() {
		String randomText = SHA256Secure.getRandomSalt();
		String pass = SHA256Secure.encrypt("1", randomText);
		boolean flag = false;
		try {
			flag = SHA256Secure.equalsCipher("1", pass, randomText);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (flag) {
			System.out.println("密码相同");
		}
	}

}
