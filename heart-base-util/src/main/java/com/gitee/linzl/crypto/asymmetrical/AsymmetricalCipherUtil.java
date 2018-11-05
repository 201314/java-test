package com.gitee.linzl.crypto.asymmetrical;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.gitee.linzl.crypto.IAlgorithm;

/**
 * 非对称加解密
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年11月6日
 */
public class AsymmetricalCipherUtil {
	/**
	 * 随机生成密钥对,非对称加解密，一般情况下不使用，只用于测试
	 * 
	 * @param algorithm
	 * @return
	 */
	public static KeyPair generateKeyPair(IAlgorithm algorithm) {
		// 实例化密钥生成器
		String algorithmName = algorithm.getKeyAlgorithm();
		// Security.addProvider(new BouncyCastleProvider());

		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance(algorithmName);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (algorithm.getSize() != null && algorithm.getSize() >= 0) {
			keyPairGen.initialize(algorithm.getSize());
		}
		KeyPair keyPair = keyPairGen.generateKeyPair();
		return keyPair;
	}

	/**
	 * 非对称加密，加载私钥字节
	 * 
	 * @param privateKeyByte
	 *            私钥字节
	 * @throws Exception
	 *             加载私钥时产生的异常
	 */
	public static PrivateKey generatePrivateKey(byte[] privateKeyByte, IAlgorithm algorithm) throws Exception {
		// 实例化密钥生成器
		String algorithmName = algorithm.getKeyAlgorithm();
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(algorithmName);
			EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyByte);
			return keyFactory.generatePrivate(privateKeySpec);// RSAPrivateKey
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法");
		}
	}

	/**
	 * 非对称加密，加载公钥字节
	 * 
	 * @param pubicKeyByte
	 *            公钥字节
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public static PublicKey generatePublicKey(byte[] pubicKeyByte, IAlgorithm algorithm) throws Exception {
		// 实例化密钥生成器
		String algorithmName = null;
		algorithmName = algorithm.getKeyAlgorithm();
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(algorithmName);
			EncodedKeySpec keySpec = new X509EncodedKeySpec(pubicKeyByte);
			return keyFactory.generatePublic(keySpec);// RSAPublicKey
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}

	/**
	 * 非对称加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            公钥
	 * @param algorithm
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(String data, PublicKey key, IAlgorithm algorithm) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm());
		// 实例化Cipher对象，它用于完成实际的加密操作
		cipher.init(Cipher.ENCRYPT_MODE, key);
		// 初始化Cipher对象，设置为加密模式
		byte[] output = cipher.doFinal(data.getBytes());
		// 执行加密操作,加密后的结果通常都会用Base64编码进行传输
		return output;
	}

	/**
	 * 非对称加密时，key为公钥
	 * 
	 * @param data
	 * @param key
	 * @param algorithm
	 * @param iv
	 *            IV密钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(String data, PublicKey key, IAlgorithm algorithm, IvParameterSpec iv)
			throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm());
		// 实例化Cipher对象，它用于完成实际的加密操作
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		// 初始化Cipher对象，设置为加密模式
		byte[] output = cipher.doFinal(data.getBytes());
		// 执行加密操作,加密后的结果通常都会用Base64编码进行传输
		return output;
	}

	public static byte[] bcEncrypt(String data, PublicKey secretKey, IAlgorithm algorithm) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm(), new BouncyCastleProvider());
		// 实例化Cipher对象，它用于完成实际的加密操作
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		// 初始化Cipher对象，设置为加密模式
		byte[] output = cipher.doFinal(data.getBytes());
		// 执行加密操作,加密后的结果通常都会用Base64编码进行传输
		return output;
	}

	/**
	 * 非对称加密时，key为私钥
	 * 
	 * @param data
	 *            待解密数据
	 * @param secretKey
	 *            密钥
	 * @param algorithm
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, PrivateKey secretKey, IAlgorithm algorithm) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm());
		// 初始化Cipher对象，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		// 执行解密操作
		return cipher.doFinal(data);
	}

	public static byte[] decrypt(byte[] data, PrivateKey secretKey, IAlgorithm algorithm, IvParameterSpec iv)
			throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm());
		// 初始化Cipher对象，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		// 执行解密操作
		return cipher.doFinal(data);
	}

	public static byte[] bcDecrypt(byte[] data, PrivateKey secretKey, IAlgorithm algorithm) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm(), new BouncyCastleProvider());
		// 初始化Cipher对象，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		// 执行解密操作
		return cipher.doFinal(data);
	}
}
