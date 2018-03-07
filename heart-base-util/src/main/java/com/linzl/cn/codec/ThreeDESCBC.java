package com.linzl.cn.codec;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class ThreeDESCBC {
	/**
	 *
	 * @Description ECB加密，不要IV，JDK中默认使用
	 * @param key
	 *            密钥
	 * @param data
	 *            明文
	 * @return Base64编码的密文
	 * @throws Exception
	 * @author Shindo
	 * @date 2016年11月15日 下午4:42:56
	 */
	public static byte[] des3EncodeECB(byte[] key, byte[] data) throws Exception {
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		Key deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 *
	 * @Description ECB解密，不要IV
	 * @param key
	 *            密钥
	 * @param data
	 *            Base64编码的密文
	 * @return 明文
	 * @throws Exception
	 * @author Shindo
	 * @date 2016年11月15日 下午5:01:23
	 */
	public static byte[] ees3DecodeECB(byte[] key, byte[] data) throws Exception {
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		Key deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, deskey);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 *
	 * @Description CBC加密
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param data
	 *            明文
	 * @return Base64编码的密文
	 * @throws Exception
	 * @author Shindo
	 * @date 2016年11月15日 下午5:26:46
	 */
	public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 *
	 * @Description CBC解密
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param data
	 *            Base64编码的密文
	 * @return 明文
	 * @throws Exception
	 * @author Shindo
	 * @date 2016年11月16日 上午10:13:49
	 */
	public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 *
	 * @Description 调试方法
	 * @param args
	 * @throws Exception
	 * @author Shindo
	 * @date 2016年11月22日 上午9:28:22
	 */
	public static void main(String[] args) throws Exception {
		byte[] key = "abcdefghijklmnopqrstuvwx".getBytes();

		byte[] data = "ECB加密解密".getBytes();
		System.out.println("ECB加密解密----开始");
		byte[] str3 = des3EncodeECB(key, data);
		byte[] str4 = ees3DecodeECB(key, str3);
		System.out.println(new String(str4));
		System.out.println("ECB加密解密----结束");

		data = "CBC加密解密".getBytes();
		System.out.println("CBC加密解密----开始");
		byte[] keyiv = { 1, 2, 3, 4, 5, 6, 7, 8 };
		byte[] str5 = des3EncodeCBC(key, keyiv, data);
		byte[] str6 = des3DecodeCBC(key, keyiv, str5);
		System.out.println(new String(str6));
		System.out.println("CBC加密解密----结束");
	}
}
