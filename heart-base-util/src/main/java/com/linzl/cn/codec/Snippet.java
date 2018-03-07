package com.linzl.cn.codec;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Snippet {
	public static byte[] key() throws NoSuchAlgorithmException {
		KeyGenerator keyGen = KeyGenerator.getInstance("DESede");// 密钥生成器
		keyGen.init(168); // 默认128，获得无政策权限后可为192或256
		SecretKey secretKey = keyGen.generateKey();// 生成密钥
		byte[] key = secretKey.getEncoded();// 密钥字节数组
		return key;
	}

	public static byte[] jiami(byte[] key, byte[] data) throws Exception {
		SecretKey secretKey = new SecretKeySpec(key, "DESede");// 恢复密钥
		Cipher cipher = Cipher.getInstance("DESede");// Cipher完成加密或解密工作类
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);// 对Cipher初始化，解密模式
		byte[] cipherByte = cipher.doFinal(data);// 加密data
		return cipherByte;
	}

	public static byte[] jiemi(byte[] key, byte[] data) throws Exception {
		SecretKey secretKey = new SecretKeySpec(key, "DESede");// 恢复密钥
		Cipher cipher = Cipher.getInstance("DESede");// Cipher完成加密或解密工作类
		cipher.init(Cipher.DECRYPT_MODE, secretKey);// 对Cipher初始化，解密模式
		byte[] cipherByte = cipher.doFinal(data);// 解密data
		return cipherByte;
	}

	public static void main(String[] args) throws Exception {
		byte[] key = key();
		String data = "我是一个中国人";
		byte[] jiamiData = jiami(key, data.getBytes());
		System.out.println(new String(jiemi(key, jiamiData)));
	}
}
