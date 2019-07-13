package com.gitee.linzl.cipher;

import org.junit.Before;
import org.junit.Test;

import com.gitee.linzl.cipher.symmetric.BlowfishCipherAlgorithms;
import com.gitee.linzl.cipher.symmetric.SymmetricCipherUtil;

public class BlowfishTest {
	private String text = null;

	@Before
	public void init() {
		text = "站在云端，敲下键盘，望着通往世界另一头的那扇窗，只为做那读懂0和1的人。。";
		System.out.println("原文：" + text);
	}

	// =============对称加密测试=============
	@Test
	public void Blowfish() throws Exception {
		excute(BlowfishCipherAlgorithms.Blowfish);
	}

	private void excute(IAlgorithm aes) throws Exception {
		byte[] key = SymmetricCipherUtil.generateKey(aes);
		BasePrint.printSecretKey(key);

		byte[] encryptData = SymmetricCipherUtil.encrypt(text.getBytes(), key, aes);
		BasePrint.printEncryptData(encryptData);

		byte[] decryptData = SymmetricCipherUtil.decrypt(encryptData, key, aes);
		BasePrint.printDecryptData(decryptData);
	}

}
