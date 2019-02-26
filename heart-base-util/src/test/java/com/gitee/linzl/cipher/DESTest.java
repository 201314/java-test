package com.gitee.linzl.cipher;

import javax.crypto.spec.IvParameterSpec;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Before;
import org.junit.Test;

import com.gitee.linzl.cipher.IAlgorithm;
import com.gitee.linzl.cipher.symmetric.DESCipherAlgorithms;
import com.gitee.linzl.cipher.symmetric.SymmetricCipherUtil;

public class DESTest {
	private String text = null;

	@Before
	public void init() {
		text = "站在云端，敲下键盘，望着通往世界另一头的那扇窗，只为做那读懂0和1的人。。";
		System.out.println("原文：" + text);
	}

	// =============对称加密测试=============
	// CBC 要使用IV 密钥
	@Test
	public void DES_CBC_NOPADDING_56() throws Exception {
		text = "12345678";// 必须是8的倍数
		excuteCBC(DESCipherAlgorithms.DES_CBC_NOPADDING_56);
	}

	@Test
	public void DES_CBC_PKCS5PADDING_56() throws Exception {
		excuteCBC(DESCipherAlgorithms.DES_CBC_PKCS5PADDING_56);
	}

	// ECB 不支持IV 密钥
	@Test
	public void DES_ECB_NOPADDING_56() throws Exception {
		text = "12345678";// 必须是8的倍数
		excute(DESCipherAlgorithms.DES_ECB_NOPADDING_56);
	}

	@Test
	public void DES_ECB_PKCS5PADDING_56() throws Exception {
		excute(DESCipherAlgorithms.DES_ECB_PKCS5PADDING_56);
	}

	@Test
	public void DESEDE_CBC_NOPADDING_112() throws Exception {
		text = "12345678";// 必须是8的倍数
		excuteCBC(DESCipherAlgorithms.DESEDE_CBC_NOPADDING_112);
	}

	@Test
	public void DESEDE_CBC_NOPADDING_168() throws Exception {
		text = "12345678";// 必须是8的倍数
		excuteCBC(DESCipherAlgorithms.DESEDE_CBC_NOPADDING_168);
	}

	@Test
	public void DESEDE_CBC_PKCS5PADDING_112() throws Exception {
		excuteCBC(DESCipherAlgorithms.DESEDE_CBC_PKCS5PADDING_112);
	}

	@Test
	public void DESEDE_CBC_PKCS5PADDING_168() throws Exception {
		excuteCBC(DESCipherAlgorithms.DESEDE_CBC_PKCS5PADDING_168);
	}

	@Test
	public void DESEDE_ECB_NOPADDING_112() throws Exception {
		text = "12345678";// 必须是8的倍数
		excute(DESCipherAlgorithms.DESEDE_ECB_NOPADDING_112);
	}

	@Test
	public void DESEDE_ECB_NOPADDING_168() throws Exception {
		text = "12345678";// 必须是8的倍数
		excute(DESCipherAlgorithms.DESEDE_ECB_NOPADDING_168);
	}

	@Test
	public void DESEDE_ECB_PKCS5PADDING_112() throws Exception {
		excute(DESCipherAlgorithms.DESEDE_ECB_PKCS5PADDING_112);
	}

	@Test
	public void DESEDE_ECB_PKCS5PADDING_168() throws Exception {
		excute(DESCipherAlgorithms.DESEDE_ECB_PKCS5PADDING_168);
	}

	// CBC 要使用IV 密钥
	private void excuteCBC(IAlgorithm aes) throws Exception {
		byte[] key = SymmetricCipherUtil.generateKey(aes);
		System.out.println("密钥长度:" + key.length);
		System.out.println("密钥16进制：" + Hex.toHexString(key));

		byte[] keyiv = { 1, 2, 3, 4, 5, 6, 7, 8 };// 长度必须是8
		// 用密钥初始化Cipher对象
		IvParameterSpec ips = new IvParameterSpec(keyiv);

		byte[] encryptData = SymmetricCipherUtil.encrypt(text, key, aes, ips);
		System.out.println("加密后：" + encryptData);
		System.out.println("加密16进制：" + Hex.toHexString(encryptData));

		byte[] decryptData = SymmetricCipherUtil.decrypt(encryptData, key, aes, ips);
		System.out.println("解密: " + new String(decryptData));
	}

	private void excute(IAlgorithm aes) throws Exception {
		byte[] key = SymmetricCipherUtil.generateKey(aes);
		System.out.println("密钥长度:" + key.length);
		System.out.println("密钥16进制：" + Hex.toHexString(key));

		byte[] encryptData = SymmetricCipherUtil.encrypt(text, key, aes);
		System.out.println("加密后：" + encryptData);
		System.out.println("加密16进制：" + Hex.toHexString(encryptData));

		byte[] decryptData = SymmetricCipherUtil.decrypt(encryptData, key, aes);
		System.out.println("解密: " + new String(decryptData));
	}
}