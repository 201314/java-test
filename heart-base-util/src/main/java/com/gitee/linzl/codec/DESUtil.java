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
	 * 默认采用密钥长度112加密
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] generate3DESKey() throws NoSuchAlgorithmException {
		return generateKey(THREEDES, 112);
	}

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
	 *            需要加密的数据
	 * @param key
	 *            加密密钥
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

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		String data = "我是个中国人";

		// byte[] randomkey = generateDESKey();
		// byte[] encryptData = encryptDES(data.getBytes(), randomkey);
		// byte[] descryptData = decryptDES(encryptData, randomkey);
		// System.out.println("DESData==>" + new String(descryptData));

		byte[] randomkey = generateAESKey();
		byte[] encode = encryptAES(data.getBytes("utf-8"), randomkey);
		// 传输过程,不转成16进制的字符串，就等着程序崩溃掉吧
		String code = parseByte2HexStr(encode);
		byte[] decode = parseHexStr2Byte(code);
		// 解密
		byte[] descryptData = decryptAES(decode, randomkey);
		System.out.println("AESData==>" + new String(descryptData, "utf-8"));

		// randomkey = generate3DES168Key();// "abcdefghijklmnopqrstuvwx".getBytes();//
		// encryptData = encrypt3DESForECB(data.getBytes(), randomkey);
		// descryptData = decrypt3DESForECB(encryptData, randomkey);
		// System.out.println("ECB===3DESForECBData==>" + new String(descryptData));
		//
		// byte[] keyiv = { 1, 2, 3, 4, 5, 6, 7, 8 };// 长度必须是8
		// encryptData = encrypt3DESForCBC(data.getBytes(), randomkey, keyiv);
		// descryptData = decrypt3DESForCBC(encryptData, randomkey, keyiv);
		// System.out.println("CBC===3DESForCBCData==>" + new String(descryptData));

	}
}
