package com.gitee.linzl.cipher;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;

import com.gitee.linzl.cipher.asymmetrical.AsymmetricalCipherUtil;
import com.gitee.linzl.cipher.asymmetrical.SignatureAlgorithms;

public class RSASignTest {
	private String text = null;

	@Before
	public void init() {
		text = "站在云端，敲下键盘，望着通往世界另一头的那扇窗，只为做那读懂0和1的人。。";
		System.out.println("原文：" + text);
	}

	// =============非对称签名=============
	// 私钥签名，公钥验签
	@Test
	public void MD2withRSA() throws Exception {
		rsaSignRandom(SignatureAlgorithms.MD2withRSA);
		rsaSign(SignatureAlgorithms.MD2withRSA);
	}

	@Test
	public void MD5withRSA() throws Exception {
		rsaSignRandom(SignatureAlgorithms.MD5withRSA);
		rsaSign(SignatureAlgorithms.MD5withRSA);
	}

	@Test
	public void SHA1withRSA() throws Exception {
		rsaSignRandom(SignatureAlgorithms.SHA1withRSA);
		rsaSign(SignatureAlgorithms.SHA1withRSA);
	}

	@Test
	public void SHA256withRSA() throws Exception {
		rsaSignRandom(SignatureAlgorithms.SHA256withRSA);
		rsaSign(SignatureAlgorithms.SHA256withRSA);
	}

	@Test
	public void SHA384withRSA() throws Exception {
		rsaSignRandom(SignatureAlgorithms.SHA384withRSA);
		rsaSign(SignatureAlgorithms.SHA384withRSA);
	}

	@Test
	public void SHA512withRSA() throws Exception {
		rsaSignRandom(SignatureAlgorithms.SHA512withRSA);
		rsaSign(SignatureAlgorithms.SHA512withRSA);
	}

	private void rsaSignRandom(SignatureAlgorithms algorithm) throws Exception {
		System.out.println("start===========JDK随机密钥===========start");
		KeyPair keyPair = AsymmetricalCipherUtil.generateKeyPair(algorithm);
		PrivateKey priKey = keyPair.getPrivate();
		BasePrint.printPrivateKey(priKey.getEncoded());

		byte[] encryptData = AsymmetricalCipherUtil.sign(text.getBytes(), priKey, algorithm);
		BasePrint.printEncryptData(encryptData);

		PublicKey pubKey = keyPair.getPublic();
		BasePrint.printPublicKey(pubKey.getEncoded());

		boolean verifyResult = AsymmetricalCipherUtil.verifySign(text.getBytes(), pubKey, encryptData, algorithm);
		System.out.println("验签结果: " + verifyResult);
		System.out.println("end===========JDK随机密钥===========end");
	}

	private void rsaSign(SignatureAlgorithms algorithm) throws Exception {
		System.out.println("start===========指定密钥===========start");
		byte[] privateKey = KeyPairPathUtil.getPrivateKeyFile();
		PrivateKey priKey = AsymmetricalCipherUtil.generatePrivateKey(Base64.decodeBase64(privateKey), algorithm);
		BasePrint.printPrivateKey(priKey.getEncoded());

		byte[] encryptData = AsymmetricalCipherUtil.sign(text.getBytes(), priKey, algorithm);
		BasePrint.printEncryptData(encryptData);

		byte[] publicKey = KeyPairPathUtil.getPublicKeyFile();
		PublicKey pubKey = AsymmetricalCipherUtil.generatePublicKey(Base64.decodeBase64(publicKey), algorithm);
		BasePrint.printPublicKey(pubKey.getEncoded());

		boolean verifyResult = AsymmetricalCipherUtil.verifySign(text.getBytes(), pubKey, encryptData, algorithm);
		System.out.println("验签结果: " + verifyResult);
		System.out.println("end===========指定密钥===========end");
	}

}
