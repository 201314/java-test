package com.gitee.linzl.codec.rsa.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gitee.linzl.codec.rsa.RSASecure;

/**
 * Java生成的公私钥格式为 pkcs8, 而openssl默认生成的公私钥格式为 pkcs1，两者的密钥实际上是不能直接互用的
 * 
 * openssl默认使用的是PEM格式，经过base64编码
 * 
 * java加载密钥文件，不能带有注解
 * 
 * @author linzl
 *
 *         非对称加密
 * @creatDate 2016年10月31日
 */
public class RSASecureUtil {
	private static final Logger logger = LoggerFactory.getLogger(RSASecureUtil.class);

	/**
	 * 前台JSEncrypt 插件加密的内容，在后台进行解密
	 * 
	 * @param encryptText
	 * @return
	 */
	public static String decrypt(String encryptText) {
		return decrypt(encryptText.getBytes());
	}

	/**
	 * 前台JSEncrypt 插件加密的内容，在后台进行解密
	 * 
	 * @param encryptText
	 * @return
	 */
	public static String decrypt(byte[] encryptText) {
		return decrypt(encryptText, RSASecure.getPrivateKeyFile());
	}

	/**
	 * 前台JSEncrypt 插件加密的内容，在后台进行解密
	 * 
	 * @param encryptText
	 *            加密的内容
	 * @param privateKeyFile
	 *            私钥文件所在位置
	 * @return
	 */
	public static String decrypt(String encryptText, File privateKeyFile) {
		try {
			return decrypt(encryptText.getBytes(), FileUtils.readFileToByteArray(privateKeyFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 前台JSEncrypt 插件加密的内容，在后台进行解密
	 * 
	 * @param encryptText
	 * @return
	 */
	public static String decrypt(byte[] encryptText, InputStream privateKeyFile) {
		try {
			return decrypt(encryptText, IOUtils.toByteArray(privateKeyFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 前台JSEncrypt 插件加密的内容，在后台进行解密
	 * 
	 * @param encryptText
	 * @return
	 */
	public static String decrypt(byte[] encryptText, byte[] privateKeyFile) {
		RSASecure rsaEncrypt = new RSASecure();
		RSAPrivateKey privateKey = null;
		try {
			// 加载私钥 ,私钥的头尾不能出现注释
			privateKey = rsaEncrypt.loadBase64PriKey(privateKeyFile);
		} catch (Exception e) {
			logger.error("加载私钥失败{}", e.getMessage());
		}

		String decrypt = "";
		try {
			// 解密
			decrypt = rsaEncrypt.decrypt(privateKey, encryptText);
		} catch (Exception e) {
			logger.error("解密失败{}", e.getMessage());
		}
		return decrypt;
	}

	/**
	 * RSA 加密
	 * 
	 * @param encryptText
	 *            需要加密的文本
	 * @return
	 */
	public static String encrypt(String encryptText) {
		return encrypt(encryptText.getBytes());
	}

	/**
	 * RSA 加密
	 * 
	 * @param decryptText
	 *            需要加密的文本
	 * @return
	 */
	public static String encrypt(byte[] decryptText) {
		return encrypt(decryptText, RSASecure.getPublicKeyFile());
	}

	/**
	 * RSA 加密
	 * 
	 * @param decryptText
	 *            需要加密的文本
	 * @param publicKeyFile
	 *            公钥文件
	 * @return
	 */
	public static String encrypt(String decryptText, File publicKeyFile) {
		try {
			return encrypt(decryptText.getBytes(), FileUtils.readFileToByteArray(publicKeyFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * RSA 加密
	 * 
	 * @param decryptText
	 *            需要加密的文本
	 * @param publicKeyFile
	 *            公钥文件
	 * @return
	 */
	public static String encrypt(byte[] decryptText, InputStream publicKeyFile) {
		try {
			return encrypt(decryptText, IOUtils.toByteArray(publicKeyFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * RSA 加密
	 * 
	 * @param decryptText
	 *            需要加密的文本
	 * @return
	 */
	public static String encrypt(byte[] decryptText, byte[] publicKeyFile) {
		RSASecure rsaEncrypt = new RSASecure();
		RSAPublicKey publicKey = null;
		try {
			// 加载私钥 ,私钥的头尾不能出现注释
			publicKey = rsaEncrypt.loadBase64PubKey(publicKeyFile);
		} catch (Exception e) {
			logger.error("加载公钥失败{}", e.getMessage());
		}

		String encrypt = "";
		try {
			// 解密
			encrypt = rsaEncrypt.encrypt(publicKey, decryptText);
		} catch (Exception e) {
			logger.error("加密失败{}", e.getMessage());
		}
		return encrypt;
	}
}