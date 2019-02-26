package com.gitee.linzl.cipher.symmetric;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

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
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @param algorithm
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, Key secretKey, IAlgorithm algorithm) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm());
		// 实例化Cipher对象，它用于完成实际的加密操作
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		// 初始化Cipher对象，设置为加密模式
		byte[] output = cipher.doFinal(data);
		// 执行加密操作,加密后的结果通常都会用Base64编码进行传输
		return output;
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
	public static byte[] encrypt(byte[] data, Key secretKey, IAlgorithm algorithm, IvParameterSpec iv)
			throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm());
		// 实例化Cipher对象，它用于完成实际的加密操作
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		// 初始化Cipher对象，设置为加密模式
		byte[] output = cipher.doFinal(data);
		// 执行加密操作,加密后的结果通常都会用Base64编码进行传输
		return output;
	}

	public static byte[] bcEncrypt(byte[] data, Key secretKey, IAlgorithm algorithm) throws Exception {
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

	public static byte[] decrypt(byte[] data, Key secretKey, IAlgorithm algorithm, IvParameterSpec iv)
			throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm());
		// 初始化Cipher对象，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		// 执行解密操作
		return cipher.doFinal(data);
	}

	public static byte[] bcDecrypt(byte[] data, Key secretKey, IAlgorithm algorithm) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm(), new BouncyCastleProvider());
		// 初始化Cipher对象，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		// 执行解密操作
		return cipher.doFinal(data);
	}

	public static byte[] bcDecrypt(byte[] data, Key secretKey, IAlgorithm algorithm, IvParameterSpec iv)
			throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm.getCipherAlgorithm(), new BouncyCastleProvider());
		// 初始化Cipher对象，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		// 执行解密操作
		return cipher.doFinal(data);
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
	public static byte[] encrypt(byte[] data, byte[] key, IAlgorithm algorithm) throws Exception {
		// 还原密钥
		Key secretKey = toKey(key, algorithm);
		return encrypt(data, secretKey, algorithm);
	}

	public static byte[] encrypt(byte[] data, byte[] key, IAlgorithm algorithm, IvParameterSpec iv) throws Exception {
		// 还原密钥
		Key secretKey = toKey(key, algorithm);
		return encrypt(data, secretKey, algorithm, iv);
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

	public static byte[] decrypt(byte[] data, byte[] key, IAlgorithm algorithm, IvParameterSpec iv) throws Exception {
		Key secretKey = toKey(key, algorithm);
		return decrypt(data, secretKey, algorithm, iv);
	}

	/**
	 * 使用BouncyCastleProvider
	 * 
	 * @param data
	 * @param key
	 * @param algorithm
	 * @return
	 * @throws Exception
	 */
	public static byte[] bcEncrypt(byte[] data, byte[] key, IAlgorithm algorithm) throws Exception {
		// 还原密钥
		Key secretKey = toKey(key, algorithm);
		return bcEncrypt(data, secretKey, algorithm);
	}

	public static byte[] bcEncrypt(byte[] data, byte[] key, IAlgorithm algorithm, IvParameterSpec iv) throws Exception {
		// 还原密钥
		Key secretKey = toKey(key, algorithm);
		return bcEncrypt(data, secretKey, algorithm);
	}

	public static byte[] bcDecrypt(byte[] data, byte[] key, IAlgorithm algorithm) throws Exception {
		Key secretKey = toKey(key, algorithm);
		return bcDecrypt(data, secretKey, algorithm);
	}

	public static byte[] bcDecrypt(byte[] data, byte[] key, IAlgorithm algorithm, IvParameterSpec iv) throws Exception {
		Key secretKey = toKey(key, algorithm);
		return bcDecrypt(data, secretKey, algorithm, iv);
	}

}
