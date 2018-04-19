package com.linzl.cn.codec.aes;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * AES对称加密算法
 * <p>
 * 这里演示的是其Java6.0的实现,理所当然的BouncyCastle也支持AES对称加密算法
 * <p>
 * 另外,我们也可以以AES算法实现为参考,完成RC2,RC4和Blowfish算法的实现
 * <p>
 * 由于DES的不安全性以及DESede算法的低效,于是催生了AES算法(Advanced Encryption Standard)
 * <p>
 * 该算法比DES要快,安全性高,密钥建立时间短,灵敏性好,内存需求低,在各个领域应用广泛
 * <p>
 * 目前,AES算法通常用于移动通信系统以及一些软件的安全外壳,还有一些无线路由器中也是用AES算法构建加密协议
 * <p>
 * 由于Java6.0支持大部分的算法,但受到出口限制,其密钥长度不能满足需求
 * <p>
 * 所以特别注意:如果使用256位的密钥,则需要无政策限制文件(Unlimited Strength Jurisdiction Policy Files)
 * <p>
 * jdk是通过权限文件local_poblicy.jar和US_export_policy.jar做相应限制,可以搜索java 8 Unlimited
 * Strength Jurisdiction Policy Files下载替换文件,减少相关限制
 * <p>
 * 然后覆盖本地JDK目录和JRE目录下的security目录下的文件即可
 * <p>
 * 关于AES的更多详细介绍,可以参考此爷的博客http://blog.csdn.net/kongqz/article/category/800296
 * 
 * @author linzl 2016年11月21日
 */
public class AESSecure {
	// 密钥算法
	private static final String KEY_ALGORITHM = "AES";

	// 加解密算法/工作模式/填充方式,Java6.0支持PKCS5Padding填充方式,BouncyCastle支持PKCS7Padding填充方式
	private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";

	// 密钥长度
	private static final int KEYSIZE = 256; // 初始化密钥生成器:AES要求密钥长度为128,192,256位

	// base64 编码
	private static final String SECRETKEY = "XoLoisqmCHkOSS4cG4SpWQyWJ5avMb0O7wj9IL3Re+s=";

	/**
	 * 生成密钥
	 */
	public static String initkey() throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM); // 实例化密钥生成器
		keyGenerator.init(KEYSIZE);
		SecretKey secretKey = keyGenerator.generateKey(); // 生成密钥
		return Base64.encodeBase64String(secretKey.getEncoded()); // 获取二进制密钥编码形式
	}

	/**
	 * 转换密钥
	 */
	public static Key toKey(byte[] key) throws Exception {
		return new SecretKeySpec(key, KEY_ALGORITHM);
	}

	/**
	 * 加密数据
	 * 
	 * @param data
	 *            待加密数据
	 * @return 加密后的数据
	 * @throws Exception
	 */
	public static String encrypt(String data) throws Exception {
		return encrypt(data, SECRETKEY);
	}

	/**
	 * 加密数据
	 * 
	 * @param data
	 *            待加密数据
	 * @param secretKey
	 *            密钥
	 * @return 加密后的数据
	 * @throws Exception
	 */
	public static String encrypt(String data, String secretKey) throws Exception {
		// 还原密钥
		Key key = toKey(Base64.decodeBase64(secretKey));
		// 使用PKCS7Padding填充方式,即调用BouncyCastle组件实现
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, new BouncyCastleProvider());
		// 实例化Cipher对象，它用于完成实际的加密操作
		cipher.init(Cipher.ENCRYPT_MODE, key);
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
	public static String decrypt(String data, String secretKey) throws Exception {
		Key key = toKey(Base64.decodeBase64(secretKey));
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, new BouncyCastleProvider());
		// 初始化Cipher对象，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, key);
		// 执行解密操作
		return new String(cipher.doFinal(Base64.decodeBase64(data)));
	}

	public static void main(String[] args) throws Exception {
		String source = "站在云端，敲下键盘，望着通往世界另一头的那扇窗，只为做那读懂0和1的人。。";
		System.out.println("原文：" + source);

		String key = initkey();
		System.out.println("密钥：" + key);

		String encryptData = encrypt(source, key);
		System.out.println("加密：" + encryptData);

		String decryptData = decrypt(encryptData, key);
		System.out.println("解密: " + decryptData);
	}
}