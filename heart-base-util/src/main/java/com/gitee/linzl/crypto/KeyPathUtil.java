package com.gitee.linzl.crypto;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gitee.linzl.properties.ReadResourceUtil;

/**
 * PS:数据加密传输:获得平台的公钥加密，平台自己的私钥解密，不能颠倒顺序
 * 
 * PS:签名：私钥签名，公钥验签。用自己的私钥签名，把公钥给别人。
 * 
 * @author linzl
 * 
 *         非对称加密
 * @creatDate 2016年10月31日
 */
public class KeyPathUtil {
	private static final Logger logger = LoggerFactory.getLogger(KeyPathUtil.class);

	private static byte[] public_key_byte;
	private static byte[] private_key_byte;

	public static byte[] getPublicKeyFile() {
		if (null == public_key_byte || public_key_byte.length <= 0) {
			InputStream publicKey = null;
			try {
				publicKey = ReadResourceUtil.getInputStream("com/gitee/linzl/codec/rsa/rsa_public_key.pem");

				if (logger.isDebugEnabled()) {
					logger.debug("加载公钥路径成功");
				}
				public_key_byte = IOUtils.toByteArray(publicKey);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return public_key_byte;
	}

	/**
	 * 打成jar包后，读取资源文件，只有是以流的形式获取
	 * 
	 * @return
	 */
	public static byte[] getPrivateKeyFile() {
		if (null == private_key_byte || private_key_byte.length <= 0) {
			InputStream privateKey = null;
			try {
				privateKey = ReadResourceUtil.getInputStream("com/gitee/linzl/codec/rsa/pkcs8_rsa_private_key.pem");
				if (logger.isDebugEnabled()) {
					logger.debug("加载私钥路径成功");
				}
				private_key_byte = IOUtils.toByteArray(privateKey);
				return private_key_byte;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return private_key_byte;
	}
}