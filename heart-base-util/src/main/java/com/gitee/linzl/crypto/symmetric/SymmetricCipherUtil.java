package com.gitee.linzl.crypto.symmetric;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.gitee.linzl.crypto.IAlgorithm;

/**
 * JCE(Java Cryptography Extension)即Java密码扩展，是JDK1.4的一个重要部分。
 * 
 * 它是一组包，它们提供用于加密、密钥生成算法和协商以及 Message Authentication Code（MAC）算法的框架和实现
 * 
 * 
 * 对称加解密工具
 * 
 * @author linzl
 */
public class SymmetricCipherUtil {
	/**
	 * 生成密钥
	 * 
	 * @return
	 */
	public static byte[] generateKey(IAlgorithm algorithm) throws Exception {
		// 实例化密钥生成器
		String algorithmName = null;
		// if (algorithm instanceof DefaultCipherAlgorithms) {
		algorithmName = algorithm.getKeyAlgorithm();
		// } else {
		// Security.addProvider(new BouncyCastleProvider());
		// algorithmName = algorithm.getAlgorithm();
		// }
		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithmName);
		keyGenerator.init(algorithm.getSize());
		// 生成密钥
		SecretKey secretKey = keyGenerator.generateKey();
		// 获取二进制密钥编码形式
		return secretKey.getEncoded();
	}

	/**
	 * 转换密钥
	 * 
	 * @param key
	 * @param algorithm
	 * @return
	 * @throws Exception
	 */
	public static Key toKey(byte[] key, IAlgorithm algorithm) throws Exception {
		String algorithmName = null;
		// if (algorithm instanceof DefaultCipherAlgorithms) {
		algorithmName = algorithm.getKeyAlgorithm();
		// } else {
		// algorithmName = algorithm.getAlgorithm();
		// }
		return new SecretKeySpec(key, algorithmName);
	}

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
	 * 加密数据,用于对称加密。非对称加密，是不可能还原密钥的
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            公钥
	 * @return 加密后的数据
	 * @throws Exception
	 */
	public static byte[] encrypt(String data, byte[] key, IAlgorithm algorithm) throws Exception {
		// 还原密钥
		Key secretKey = toKey(key, algorithm);
		return encrypt(data, secretKey, algorithm);
	}

	/**
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @param algorithm
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(String data, Key key, IAlgorithm algorithm) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm());
		// 实例化Cipher对象，它用于完成实际的加密操作
		cipher.init(Cipher.ENCRYPT_MODE, key);
		// 初始化Cipher对象，设置为加密模式
		byte[] output = cipher.doFinal(data.getBytes());
		// 执行加密操作,加密后的结果通常都会用Base64编码进行传输
		return output;
	}

	public static byte[] encrypt(String data, byte[] key, IAlgorithm algorithm, IvParameterSpec iv) throws Exception {
		// 还原密钥
		Key secretKey = toKey(key, algorithm);
		return encrypt(data, secretKey, algorithm, iv);
	}

	/**
	 * @param data
	 * @param key
	 *            密钥
	 * @param algorithm
	 * @param iv
	 *            IV密钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(String data, Key key, IAlgorithm algorithm, IvParameterSpec iv) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm());
		// 实例化Cipher对象，它用于完成实际的加密操作
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		// 初始化Cipher对象，设置为加密模式
		byte[] output = cipher.doFinal(data.getBytes());
		// 执行加密操作,加密后的结果通常都会用Base64编码进行传输
		return output;
	}

	public static byte[] bcEncrypt(String data, byte[] key, IAlgorithm algorithm) throws Exception {
		// 还原密钥
		Key secretKey = toKey(key, algorithm);
		return bcEncrypt(data, secretKey, algorithm);
	}

	public static byte[] bcEncrypt(String data, Key secretKey, IAlgorithm algorithm) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm(), new BouncyCastleProvider());
		// 实例化Cipher对象，它用于完成实际的加密操作
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		// 初始化Cipher对象，设置为加密模式
		byte[] output = cipher.doFinal(data.getBytes());
		// 执行加密操作,加密后的结果通常都会用Base64编码进行传输
		return output;
	}

	/**
	 * 解密数据
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return 解密后的数据
	 */
	public static byte[] decrypt(byte[] data, byte[] key, IAlgorithm algorithm) throws Exception {
		Key secretKey = toKey(key, algorithm);
		return decrypt(data, secretKey, algorithm);
	}

	/**
	 * 非对称加密时，key为私钥
	 * 
	 * @param data
	 * @param secretKey
	 * @param algorithm
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, Key secretKey, IAlgorithm algorithm) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm());
		// 初始化Cipher对象，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		// 执行解密操作
		return cipher.doFinal(data);
	}

	public static byte[] decrypt(byte[] data, byte[] key, IAlgorithm algorithm, IvParameterSpec iv) throws Exception {
		Key secretKey = toKey(key, algorithm);
		return decrypt(data, secretKey, algorithm, iv);
	}

	public static byte[] decrypt(byte[] data, Key secretKey, IAlgorithm algorithm, IvParameterSpec iv)
			throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm());
		// 初始化Cipher对象，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		// 执行解密操作
		return cipher.doFinal(data);
	}

	public static byte[] bcDecrypt(byte[] data, byte[] key, IAlgorithm algorithm) throws Exception {
		Key secretKey = toKey(key, algorithm);
		return bcDecrypt(data, secretKey, algorithm);
	}

	public static byte[] bcDecrypt(byte[] data, Key secretKey, IAlgorithm algorithm) throws Exception {
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
