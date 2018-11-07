package com.gitee.linzl.cipher;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Before;
import org.junit.Test;

import com.gitee.linzl.cipher.asymmetrical.AsymmetricalCipherUtil;
import com.gitee.linzl.cipher.asymmetrical.SignatureAlgorithms;

public class DSASignTest {
	private String text = null;

	@Before
	public void init() {
		text = "站在云端，敲下键盘，望着通往世界另一头的那扇窗，只为做那读懂0和1的人。。";
		System.out.println("原文：" + text);
	}

	// =============非对称签名=============
	// 私钥签名，公钥验签
	@Test
	public void RawDSA() throws Exception {
		text = "12345678901234567890"; // 只能20个字节
		rsaSignRandom(SignatureAlgorithms.RawDSA);
	}

	@Test
	public void SHA1withDSA() throws Exception {
		rsaSignRandom(SignatureAlgorithms.SHA1withDSA);
	}

	@Test
	public void SHA224withDSA() throws Exception {
		rsaSignRandom(SignatureAlgorithms.SHA224withDSA);
	}

	@Test
	public void SHA256withDSA() throws Exception {
		rsaSignRandom(SignatureAlgorithms.SHA256withDSA);
	}

	private void rsaSignRandom(SignatureAlgorithms algorithm) throws Exception {
		System.out.println("start===========JDK随机密钥===========start");
		KeyPair keyPair = AsymmetricalCipherUtil.generateKeyPair(algorithm);
		PrivateKey privateKey = keyPair.getPrivate();
		System.out.println("私钥长度：" + privateKey.getEncoded().length);

		byte[] encryptData = AsymmetricalCipherUtil.sign(text.getBytes(), privateKey, algorithm);
		System.out.println("加密：" + encryptData);

		PublicKey publicKey = keyPair.getPublic();
		System.out.println("公钥长度：" + Hex.toHexString(publicKey.getEncoded()));
		boolean verifyResult = AsymmetricalCipherUtil.verifySign(text.getBytes(), publicKey, encryptData, algorithm);
		System.out.println("解密: " + verifyResult);
		System.out.println("end===========JDK随机密钥===========end");
	}

}
