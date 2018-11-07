package com.gitee.linzl.cipher.symmetric;

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
import org.bouncycastle.util.encoders.Hex;

import com.gitee.linzl.cipher.IAlgorithm;

/**
 * 对称加解密工具：加密密钥与解密密钥相同。
 * 
 * 密钥管理:比较难，不适合互联网，一般用于内部系统
 * 
 * 安全性:中
 * 
 * 速度:快好几个数量级
 * 
 * 适合场景:适合大量数据加密处理，不支持数字签名
 * 
 * 实际应用:采用非对称加密算法管理对称算法的密钥，用对称加密算法加密数据，即提高了加密速度，
 * 
 * 又实现了解密的安全RSA建议采用1024位的数字，ECC建议采用160位，AES采用128为即可。
 * 
 * DES(Data Encryption Standard): 数据加密标准，速度较快，适用于加密大量数据的场合;
 * 
 * 3DES(Triple DES): 是基于DES，对一块数据用三个不同的密钥进行三次加密，强度更高;
 * 
 * AES(Advanced Encryption Standard): 高级加密标准，是下一代的加密算法标准，速度快，安全级别高，AES 标准的一个实现是
 * Rijndael 算法;
 * 
 * RC2和RC4:用变长密钥对大量数据进行加密，比 DES 快;
 * 
 * Blowfish
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
		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm.getKeyAlgorithm());
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
		return new SecretKeySpec(key, algorithm.getKeyAlgorithm());
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

	public static String encryptHex(String data, byte[] key, IAlgorithm algorithm) throws Exception {
		return Hex.toHexString(encrypt(data, key, algorithm));
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

	public static String encryptHex(String data, Key key, IAlgorithm algorithm) throws Exception {
		return Hex.toHexString(encrypt(data, key, algorithm));
	}

	public static byte[] encrypt(String data, byte[] key, IAlgorithm algorithm, IvParameterSpec iv) throws Exception {
		// 还原密钥
		Key secretKey = toKey(key, algorithm);
		return encrypt(data, secretKey, algorithm, iv);
	}

	public static String encryptHex(String data, byte[] key, IAlgorithm algorithm, IvParameterSpec iv)
			throws Exception {
		return Hex.toHexString(encrypt(data, key, algorithm, iv));
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

	public static String encryptHex(String data, Key key, IAlgorithm algorithm, IvParameterSpec iv) throws Exception {
		return Hex.toHexString(encrypt(data, key, algorithm, iv));
	}

	public static byte[] bcEncrypt(String data, byte[] key, IAlgorithm algorithm) throws Exception {
		// 还原密钥
		Key secretKey = toKey(key, algorithm);
		return bcEncrypt(data, secretKey, algorithm);
	}

	public static String bcEncryptHex(String data, byte[] key, IAlgorithm algorithm) throws Exception {
		return Hex.toHexString(bcEncrypt(data, key, algorithm));
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

	public static String bcEncryptHex(String data, Key secretKey, IAlgorithm algorithm) throws Exception {
		return Hex.toHexString(bcEncrypt(data, secretKey, algorithm));
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

	public static String decryptHex(byte[] data, byte[] key, IAlgorithm algorithm) throws Exception {
		return Hex.toHexString(decrypt(data, key, algorithm));
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

	public static String decryptHex(byte[] data, Key secretKey, IAlgorithm algorithm) throws Exception {
		return Hex.toHexString(decrypt(data, secretKey, algorithm));
	}

	public static byte[] decrypt(byte[] data, byte[] key, IAlgorithm algorithm, IvParameterSpec iv) throws Exception {
		Key secretKey = toKey(key, algorithm);
		return decrypt(data, secretKey, algorithm, iv);
	}

	public static String decryptHex(byte[] data, byte[] key, IAlgorithm algorithm, IvParameterSpec iv)
			throws Exception {
		return Hex.toHexString(decrypt(data, key, algorithm, iv));
	}

	public static byte[] decrypt(byte[] data, Key secretKey, IAlgorithm algorithm, IvParameterSpec iv)
			throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm());
		// 初始化Cipher对象，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		// 执行解密操作
		return cipher.doFinal(data);
	}

	public static String decryptHex(byte[] data, Key secretKey, IAlgorithm algorithm, IvParameterSpec iv)
			throws Exception {
		return Hex.toHexString(decrypt(data, secretKey, algorithm, iv));
	}

	public static byte[] bcDecrypt(byte[] data, byte[] key, IAlgorithm algorithm) throws Exception {
		Key secretKey = toKey(key, algorithm);
		return bcDecrypt(data, secretKey, algorithm);
	}

	public static String bcDecryptHex(byte[] data, byte[] key, IAlgorithm algorithm) throws Exception {
		return Hex.toHexString(bcDecrypt(data, key, algorithm));
	}

	public static byte[] bcDecrypt(byte[] data, Key secretKey, IAlgorithm algorithm) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm(), new BouncyCastleProvider());
		// 初始化Cipher对象，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		// 执行解密操作
		return cipher.doFinal(data);
	}

	public static String bcDecryptHex(byte[] data, Key secretKey, IAlgorithm algorithm) throws Exception {
		return Hex.toHexString(bcDecrypt(data, secretKey, algorithm));
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

	public static String signHex(byte[] data, PrivateKey privateKey, IAlgorithm algorithm) throws Exception {
		return Hex.toHexString(sign(data, privateKey, algorithm));
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
