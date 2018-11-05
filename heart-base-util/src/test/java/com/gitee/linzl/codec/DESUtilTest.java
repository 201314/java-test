package com.gitee.linzl.codec;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

public class DESUtilTest {
	@Test
	public void testAESKey() throws Exception {
		String data = "我是个中国人qENl``";
		byte[] randomkey = DESUtil.generateAESKey();
		System.out.println("aes密钥长度:" + randomkey.length);
		System.out.println("密钥:" + Hex.toHexString(randomkey));
		byte[] encryptData = DESUtil.encryptAES(data.getBytes(), randomkey);
		System.out.println("加密数据："+Hex.toHexString(encryptData));
		// 解密
		byte[] descryptData = DESUtil.decryptAES(encryptData, randomkey);
		System.out.println("AESData==>" + new String(descryptData));
	}

	@Test
	public void testDESKey() throws Exception {
		String data = "我是个中国人";

		byte[] randomkey = DESUtil.generateDESKey();
		byte[] encryptData = DESUtil.encryptDES(data.getBytes(), randomkey);
		byte[] descryptData = DESUtil.decryptDES(encryptData, randomkey);
		System.out.println("DESData==>" + new String(descryptData));
	}

	@Test
	public void test3DESKeyForECB() throws Exception {
		String data = "我是个中国人";
		// byte[] randomkey = "abcdefghijklmnopqrstuvwx".getBytes();
		// byte[] randomkey = DESUtil.generate3DES168Key();
		byte[] randomkey = DESUtil.generate3DESKey();
		byte[] encryptData = DESUtil.encrypt3DESForECB(data.getBytes(), randomkey);
		byte[] descryptData = DESUtil.decrypt3DESForECB(encryptData, randomkey);
		System.out.println("ECB===3DESForECBData==>" + new String(descryptData));
	}

	@Test
	public void test3DESKeyForCBC() throws Exception {
		String data = "我是个中国人，CBC加密解密";
		// byte[] randomkey = "abcdefghijklmnopqrstuvwx".getBytes();
		// byte[] randomkey = DESUtil.generate3DES168Key();
		byte[] randomkey = DESUtil.generate3DESKey();
		byte[] keyiv = { 1, 2, 3, 4, 5, 6, 7, 8 };// 长度必须是8
		byte[] encryptData = DESUtil.encrypt3DESForCBC(data.getBytes(), randomkey, keyiv);
		byte[] descryptData = DESUtil.decrypt3DESForCBC(encryptData, randomkey, keyiv);
		System.out.println("CBC===3DESForCBCData==>" + new String(descryptData));
	}
}
