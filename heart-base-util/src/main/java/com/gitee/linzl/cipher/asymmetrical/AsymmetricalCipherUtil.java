package com.gitee.linzl.cipher.asymmetrical;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.gitee.linzl.cipher.IAlgorithm;

/**
 * 非对称加解密
 * 
 * 密钥管理: 密钥管理容易
 * 
 * 安全性:高
 * 
 * 速度:慢
 * 
 * 适合场景:适合小量数据加密，支持数字签名
 * 
 * 实际应用:采用非对称加密算法管理对称算法的密钥，用对称加密算法加密数据，即提高了加密速度，
 * 
 * 又实现了解密的安全RSA建议采用1024位的数字，ECC建议采用160位，AES采用128为即可。
 * 
 * @description
 * 
 * 				数据加密传输:
 * 
 *              非对称加解密：如果密钥是自己生成的话,
 *              <p>
 *              在网页端，使用公钥加密传数据给自己，自己再用私钥解密。
 *              </p>
 *              在服务端，使用私钥加密数据传给其他人，其他人再用公钥解密。
 * 
 *              签名：只能加签验签，无法解签
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
	public static byte[] encrypt(byte[] data, PublicKey key, IAlgorithm algorithm) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm());
		// 实例化Cipher对象，它用于完成实际的加密操作
		cipher.init(Cipher.ENCRYPT_MODE, key);
		// 初始化Cipher对象，设置为加密模式
		byte[] output = cipher.doFinal(data);
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
	public static byte[] encrypt(byte[] data, PublicKey key, IAlgorithm algorithm, IvParameterSpec iv)
			throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm());
		// 实例化Cipher对象，它用于完成实际的加密操作
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		// 初始化Cipher对象，设置为加密模式
		byte[] output = cipher.doFinal(data);
		// 执行加密操作,加密后的结果通常都会用Base64编码进行传输
		return output;
	}

	public static byte[] bcEncrypt(byte[] data, PublicKey secretKey, IAlgorithm algorithm) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm(), new BouncyCastleProvider());
		// 实例化Cipher对象，它用于完成实际的加密操作
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		// 初始化Cipher对象，设置为加密模式
		byte[] output = cipher.doFinal(data);
		// 执行加密操作,加密后的结果通常都会用Base64编码进行传输
		return output;
	}

	public static byte[] bcEncrypt(byte[] data, Key secretKey, IAlgorithm algorithm, IvParameterSpec iv)
			throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm(), new BouncyCastleProvider());
		// 实例化Cipher对象，它用于完成实际的加密操作
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		// 初始化Cipher对象，设置为加密模式
		byte[] output = cipher.doFinal(data);
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

	/**
	 * 对数据进行签名
	 * 
	 * @param data
	 *            需要签名的数据
	 * @param privateKey
	 *            私钥
	 * @param algorithm
	 *            签名算法
	 * @return
	 * @throws Exception
	 */
	public static byte[] sign(byte[] data, PrivateKey privateKey, IAlgorithm algorithm) throws Exception {
		// 用私钥对信息生成数字签名
		String signAlgorithm = algorithm.getSignAlgorithm();
		Signature signature = Signature.getInstance(signAlgorithm);
		signature.initSign(privateKey);
		signature.update(data);
		return signature.sign();
	}

	/**
	 * 验证签名
	 * 
	 * @param data
	 *            签名前的数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            签名后的数据
	 * @return
	 * @throws Exception
	 */
	public static boolean verifySign(byte[] data, PublicKey publicKey, byte[] sign, IAlgorithm algorithm)
			throws Exception {
		// 用私钥对信息生成数字签名
		String signAlgorithm = algorithm.getSignAlgorithm();
		Signature signature = Signature.getInstance(signAlgorithm);
		signature.initVerify(publicKey);
		signature.update(data);

		// 执行加密操作,加密后的结果通常都会用Base64编码进行传输
		// 验证签名是否正常
		return signature.verify(sign);
	}
}
