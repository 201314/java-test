package com.gitee.linzl.crypto;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * JCE(Java Cryptography Extension)即Java密码扩展，是JDK1.4的一个重要部分。
 * 
 * 它是一组包，它们提供用于加密、密钥生成算法和协商以及 Message Authentication Code（MAC）算法的框架和实现
 * 
 * @author linzl
 */
public class CipherUtil {
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
		String algorithmName = null;
		if (algorithm instanceof DefaultCipherAlgorithms) {
			algorithmName = algorithm.getKeyAlgorithm();
		} else {
			Security.addProvider(new BouncyCastleProvider());
			algorithmName = algorithm.getKeyAlgorithm();
		}

		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance(algorithmName);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (algorithm.getSize() != null && algorithm.getSize() >= 0) {
			keyPairGen.initialize(algorithm.getSize());
		}
		// keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		return keyPair;
	}

	/**
	 * 加载私钥字节
	 * 
	 * @param privateKeyByte
	 *            私钥字节
	 * @throws Exception
	 *             加载私钥时产生的异常
	 */
	public static PrivateKey generatePrivateKey(byte[] privateKeyByte, IAlgorithm algorithm) throws Exception {
		// 实例化密钥生成器
		String algorithmName = null;
		algorithmName = algorithm.getKeyAlgorithm();
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
	 * 加载公钥字节
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
	 * 加密数据,用于对称加密。非对称加密，是不可能还原密钥的
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return 加密后的数据
	 * @throws Exception
	 */
	public static String encrypt(String data, byte[] key, IAlgorithm algorithm) throws Exception {
		// 还原密钥
		Key secretKey = toKey(key, algorithm);
		return encrypt(data, secretKey, algorithm);
	}

	public static String encrypt(String data, Key secretKey, IAlgorithm algorithm) throws Exception {
		Cipher cipher = null;
		if (algorithm instanceof DefaultCipherAlgorithms) {
			cipher = Cipher.getInstance(algorithm.getCipherAlgorithm());
		} else {
			cipher = Cipher.getInstance(algorithm.getCipherAlgorithm(), new BouncyCastleProvider());
		}
		// 实例化Cipher对象，它用于完成实际的加密操作
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		// 初始化Cipher对象，设置为加密模式
		byte[] output = cipher.doFinal(data.getBytes());
		// 执行加密操作,加密后的结果通常都会用Base64编码进行传输
		return Base64.encodeBase64String(output);
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
	public static String decrypt(String data, byte[] key, IAlgorithm algorithm) throws Exception {
		Key secretKey = toKey(key, algorithm);
		return decrypt(data, secretKey, algorithm);
	}

	public static String decrypt(String data, Key secretKey, IAlgorithm algorithm) throws Exception {
		Cipher cipher = null;
		if (algorithm instanceof DefaultCipherAlgorithms) {
			cipher = Cipher.getInstance(algorithm.getCipherAlgorithm());
		} else {
			cipher = Cipher.getInstance(algorithm.getCipherAlgorithm(), new BouncyCastleProvider());
		}
		// 初始化Cipher对象，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		// 执行解密操作
		return new String(cipher.doFinal(Base64.decodeBase64(data)));
	}

	/**
	 * 对数据进行签名
	 * 
	 * @param data
	 * @param secretKey
	 * @param algorithm
	 * @return
	 * @throws Exception
	 */
	public static String sign(String data, PrivateKey secretKey, IAlgorithm algorithm) throws Exception {
		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(algorithm.getSignAlgorithm());
		signature.initSign(secretKey);
		signature.update(data.getBytes());

		// 执行加密操作,加密后的结果通常都会用Base64编码进行传输
		return Base64.encodeBase64String(signature.sign());
	}

	/**
	 * 验证签名
	 * 
	 * @param data
	 *            签名前的数据
	 * @param secretKey
	 * @param sign
	 *            签名后的数据
	 * @return
	 * @throws Exception
	 */
	public static boolean verifySign(String data, PublicKey secretKey, String sign, IAlgorithm algorithm)
			throws Exception {
		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(algorithm.getSignAlgorithm());
		signature.initVerify(secretKey);
		signature.update(data.getBytes());

		// 执行加密操作,加密后的结果通常都会用Base64编码进行传输
		// 验证签名是否正常
		return signature.verify(Base64.decodeBase64(sign));
	}
}
