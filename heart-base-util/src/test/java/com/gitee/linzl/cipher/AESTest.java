package com.gitee.linzl.cipher;

import javax.crypto.spec.IvParameterSpec;

import org.junit.Before;
import org.junit.Test;

import com.gitee.linzl.cipher.symmetric.AESCipherAlgorithms;
import com.gitee.linzl.cipher.symmetric.SymmetricCipherUtil;

public class AESTest {
	private String text = null;

	@Before
	public void init() {
		text = "站在云端，敲下键盘，望着通往世界另一头的那扇窗，只为做那读懂0和1的人。。";
		System.out.println("原文：" + text);
	}

	// =============对称加密测试=============
	@Test
	public void AES_CBC_NOPADDING_128() throws Exception {
		text = "1234567890123456";// 必须是16的倍数
		excuteCBC(AESCipherAlgorithms.AES_CBC_NOPADDING_128);
	}

	@Test
	public void AEC_CBC_NOPADDING_192() throws Exception {
		text = "1234567890123456";// 必须是16的倍数
		excuteCBC(AESCipherAlgorithms.AES_CBC_NOPADDING_192);
	}

	@Test
	public void AEC_CBC_NOPADDING_256() throws Exception {
		text = "1234567890123456";// 必须是16的倍数
		excuteCBC(AESCipherAlgorithms.AES_CBC_NOPADDING_256);
	}

	@Test
	public void AES_CBC_PKCS5PADDING_128() throws Exception {
		//用这个测试
		text = "我是加密数据";
		excuteCBC(AESCipherAlgorithms.AES_CBC_PKCS5PADDING_128);
	}

	@Test
	public void AES_CBC_PKCS5PADDING_192() throws Exception {
		excuteCBC(AESCipherAlgorithms.AES_CBC_PKCS5PADDING_192);
	}

	@Test
	public void AES_CBC_PKCS5PADDING_256() throws Exception {
		excuteCBC(AESCipherAlgorithms.AES_CBC_PKCS5PADDING_256);
	}

	@Test
	public void AES_ECB_NOPADDING_128() throws Exception {
		text = "1234567890123456";// 必须是16的倍数
		excute(AESCipherAlgorithms.AES_ECB_NOPADDING_128);
	}

	@Test
	public void AES_ECB_NOPADDING_192() throws Exception {
		text = "1234567890123456";// 必须是16的倍数
		excute(AESCipherAlgorithms.AES_ECB_NOPADDING_192);
	}

	@Test
	public void AES_ECB_NOPADDING_256() throws Exception {
		text = "1234567890123456";// 必须是16的倍数
		excute(AESCipherAlgorithms.AES_ECB_NOPADDING_256);
	}

	@Test
	public void AES_ECB_PKCS5PADDING_128() throws Exception {
		excute(AESCipherAlgorithms.AES_ECB_PKCS5PADDING_128_DEFAULT);
	}

	@Test
	public void AES_ECB_PKCS5PADDING_192() throws Exception {
		excute(AESCipherAlgorithms.AES_ECB_PKCS5PADDING_192);
	}

	@Test
	public void AES_ECB_PKCS5PADDING_256() throws Exception {
		excute(AESCipherAlgorithms.AES_ECB_PKCS5PADDING_256);
	}

	@Test
	public void BC_AES_CBC_PKCS7PADDING_128() throws Exception {
		excuteCBC(AESCipherAlgorithms.BC_AES_CBC_PKCS7PADDING_128);
	}

	@Test
	public void BC_AES_CBC_PKCS7PADDING_192() throws Exception {
		excuteCBC(AESCipherAlgorithms.BC_AES_CBC_PKCS7PADDING_192);
	}

	@Test
	public void BC_AES_CBC_PKCS7PADDING_256() throws Exception {
		excuteCBC(AESCipherAlgorithms.BC_AES_CBC_PKCS7PADDING_256);
	}
	
	@Test
	public void BC_AES_ECB_PKCS7PADDING_128() throws Exception {
		excute(AESCipherAlgorithms.BC_AES_ECB_PKCS7PADDING_128);
	}

	@Test
	public void BC_AES_ECB_PKCS7PADDING_192() throws Exception {
		excute(AESCipherAlgorithms.BC_AES_ECB_PKCS7PADDING_192);
	}

	@Test
	public void BC_AES_ECB_PKCS7PADDING_256() throws Exception {
		excute(AESCipherAlgorithms.BC_AES_ECB_PKCS7PADDING_256);
	}

	/**
	 * jdk默认 CBC 要使用IV 密钥
	 * 
	 * @param aes
	 * @throws Exception
	 */
	private void excuteCBC(IAlgorithm aes) throws Exception {
		byte[] key = SymmetricCipherUtil.generateKey(aes);
		BasePrint.printSecretKey(key);

		//byte[] keyiv = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6 };// 长度必须是16
		// 用密钥初始化Cipher对象
		//IvParameterSpec ips = new IvParameterSpec(keyiv);

		byte[] encryptData = SymmetricCipherUtil.encrypt(text.getBytes(), key, aes);
		BasePrint.printEncryptData(encryptData);

		byte[] decryptData = SymmetricCipherUtil.decrypt(encryptData, key, aes);
		BasePrint.printDecryptData(decryptData);
	}

	/**
	 * jdk默认
	 * 
	 * @param aes
	 * @throws Exception
	 */
	private void excute(IAlgorithm aes) throws Exception {
		byte[] key = "Ux5di0G9pYHdkDtk1hYfKQ==".getBytes();//SymmetricCipherUtil.generateKey(aes);
		BasePrint.printSecretKey(key);

		byte[] encryptData = SymmetricCipherUtil.encrypt(text.getBytes(), key, aes);
		BasePrint.printEncryptData(encryptData);

		byte[] decryptData = SymmetricCipherUtil.decrypt(encryptData, key, aes);
		BasePrint.printDecryptData(decryptData);
	}

}
