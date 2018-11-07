package com.gitee.linzl.cipher;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Before;
import org.junit.Test;

import com.gitee.linzl.cipher.IAlgorithm;
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
		System.out.println("密钥长度:" + key.length);
		System.out.println("密钥16进制：" + Hex.toHexString(key));

		byte[] encryptData = SymmetricCipherUtil.encrypt(text, key, aes);
		System.out.println("加密后：" + encryptData);
		System.out.println("加密16进制：" + Hex.toHexString(encryptData));

		byte[] decryptData = SymmetricCipherUtil.decrypt(encryptData, key, aes);
		System.out.println("解密: " + new String(decryptData));
	}

}
