package com.gzzq.secure;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linzl.cn.codec.rsa.util.RSASecureUtil;

/**
 * 
 * @author linzl
 *
 * @creatDate 2016年10月31日
 */
public class RSASecureTest {
	private static final Logger logger = LoggerFactory.getLogger(RSASecureTest.class);
	// 测试字符串
	String encryptStr = "中国人林振烈";
	byte[] encryptByte = null;

	@Before
	public void init() {
		encryptByte = RSASecureUtil.encrypt(encryptStr.getBytes()).getBytes();
	}

	@Test
	public void decrypt() {
		// 解密
		String plainText = RSASecureUtil.decrypt(encryptByte);
		if (logger.isDebugEnabled()) {
			logger.debug("解密成功：" + (plainText));
		}
	}

}