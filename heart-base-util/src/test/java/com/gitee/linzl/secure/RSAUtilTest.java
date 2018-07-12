package com.gitee.linzl.secure;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gitee.linzl.codec.rsa.util.RSAUtil;

/**
 * 
 * @author linzl
 *
 * @creatDate 2016年10月31日
 */
public class RSAUtilTest {
	private static final Logger logger = LoggerFactory.getLogger(RSAUtilTest.class);
	// 测试字符串
	String encryptStr = "中国人林振烈";
	byte[] encryptByte = null;

	@Before
	public void init() {
		encryptByte = RSAUtil.encrypt(encryptStr.getBytes()).getBytes();
	}

	@Test
	public void decrypt() {
		// 解密
		String plainText = RSAUtil.decrypt(encryptByte);
		if (logger.isDebugEnabled()) {
			logger.debug("解密成功：" + (plainText));
		}
	}

}