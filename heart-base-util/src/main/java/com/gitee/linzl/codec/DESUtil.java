package com.gitee.linzl.codec;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * java 自带的DES/3DES/AES 三种对称加密算法
 * 
 * @author linzl
 * 
 */
public class DESUtil {
	private final static String DES = "DES";

	private final static String THREEDES = "desede";
	// 工作模式/填充方式
	private final static String ECBPadding = "/ECB/PKCS5Padding";
	private final static String CBCPadding = "/CBC/PKCS5Padding";

	private final static String AES = "AES";

	/**
	 * 第一步：随机生成密钥
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] generateKey(String algorithm, int keysize) throws NoSuchAlgorithmException {
		KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);// 密钥生成器
		keyGen.init(keysize);// 初始化密钥生成器
		SecretKey secretKey = keyGen.generateKey();// 生成密钥
		return secretKey.getEncoded();// 密钥字节数组
	}

	public static byte[] generateDESKey() throws NoSuchAlgorithmException {
		return generateKey(DES, 56);
	}

	/**
	 * 采用密钥长度112加密
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] generate3DESKey() throws NoSuchAlgorithmException {
		return generateKey(THREEDES, 112);
	}

	/**
	 * 采用密钥长度168加密
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] generate3DES168Key() throws NoSuchAlgorithmException {
		return generateKey(THREEDES, 168);
	}

	/**
	 * 默认采用密钥长度128加密
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] generateAESKey() throws NoSuchAlgorithmException {
		return generateKey(AES, 128);
	}

	public static byte[] generateAES192Key() throws NoSuchAlgorithmException {
		return generateKey(AES, 192);
	}

	public static byte[] generateAES256Key() throws NoSuchAlgorithmException {
		return generateKey(AES, 256);
	}

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @param algorithm
	 *            加密算法
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key, String algorithm) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象,会检查key的长度
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(algorithm);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		return cipher.doFinal(data);
	}

	public static byte[] encryptDES(byte[] data, byte[] key) throws Exception {
		return encrypt(data, key, DES);
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key, String algorithm) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom random = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(algorithm);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		return cipher.doFinal(data);
	}

	public static byte[] decryptDES(byte[] data, byte[] key) throws Exception {
		return decrypt(data, key, DES);
	}

	/**
	 * @param data
	 *            需要加密的数据
	 * @param key
	 *            加密密钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt3DESForECB(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom random = new SecureRandom();

		// 从原始密钥数据创建DESedeKeySpec对象
		DESedeKeySpec spec = new DESedeKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(THREEDES);
		Key deskey = keyfactory.generateSecret(spec);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(THREEDES + ECBPadding);
		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, deskey, random);
		return cipher.doFinal(data);
	}

	public static byte[] decrypt3DESForECB(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom random = new SecureRandom();

		// 从原始密钥数据创建DESedeKeySpec对象
		DESedeKeySpec spec = new DESedeKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(THREEDES);
		Key deskey = keyfactory.generateSecret(spec);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(THREEDES + ECBPadding);
		// 用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, deskey, random);
		return cipher.doFinal(data);
	}

	/**
	 * @param data
	 *            明文
	 * @param key
	 *            加密密钥
	 * @param keyiv
	 *            IV密钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt3DESForCBC(byte[] data, byte[] key, byte[] keyiv) throws Exception {
		// 从原始密钥数据创建DESedeKeySpec对象
		DESedeKeySpec spec = new DESedeKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(THREEDES);
		Key deskey = keyfactory.generateSecret(spec);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(THREEDES + CBCPadding);
		// 用密钥初始化Cipher对象
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		return cipher.doFinal(data);
	}

	/**
	 * @param data
	 *            密文
	 * @param key
	 *            加密密钥
	 * @param keyiv
	 *            IV密钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt3DESForCBC(byte[] data, byte[] key, byte[] keyiv) throws Exception {
		// 从原始密钥数据创建DESedeKeySpec对象
		DESedeKeySpec spec = new DESedeKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(THREEDES);
		Key deskey = keyfactory.generateSecret(spec);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(THREEDES + CBCPadding);
		// 用密钥初始化Cipher对象
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
		return cipher.doFinal(data);
	}

	public static byte[] encryptAES(byte[] data, byte[] key) throws Exception {
		// 成SecretKey对象
		SecretKey deskey = new SecretKeySpec(key, AES);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(AES);
		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		return cipher.doFinal(data);
	}

	public static byte[] decryptAES(byte[] data, byte[] key) throws Exception {
		// 成SecretKey对象
		SecretKey deskey = new SecretKeySpec(key, AES);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(AES);
		// 用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, deskey);
		return cipher.doFinal(data);
	}
}
