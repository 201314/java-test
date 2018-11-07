package com.gitee.linzl.cipher;

import org.junit.Test;

import com.gitee.linzl.cipher.message.DigestUtilsExt;

/**
 * 后面可考虑使用 apache shiro
 * 
 * @author linzl
 *
 * @creatDate 2016年11月2日
 */
public class DigestUtilsExtTest {

	@Test
	public void encrypt() {
		String randomText = DigestUtilsExt.getRandomSalt();
		String pass = DigestUtilsExt.sha256Hex("我是用户的密码进行加密", randomText);
		System.out.println("加密后结果:" + pass);
	}

}
