package com.gitee.linzl.crypto;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Before;
import org.junit.Test;

public class CipherUtilTest {
	private String text = null;

	@Before
	public void init() {
		text = "站在云端，敲下键盘，望着通往世界另一头的那扇窗，只为做那读懂0和1的人。。";
		System.out.println("原文：" + text);
	}

	// =============对称加密测试=============
	@Test
	public void testSymmetric() throws Exception {
		byte[] key = CipherUtil.generateKey(DefaultCipherAlgorithms.DESEDE_ECB_PKCS5PADDING_168);
		System.out.println("密钥长度:" + key.length);
		System.out.println("密钥：" + Hex.toHexString(key));

		String encryptData = CipherUtil.encrypt(text, key, DefaultCipherAlgorithms.DESEDE_ECB_PKCS5PADDING_168);
		System.out.println("加密：" + encryptData);

		String decryptData = CipherUtil.decrypt(encryptData, key, DefaultCipherAlgorithms.DESEDE_ECB_PKCS5PADDING_168);
		System.out.println("解密: " + decryptData);
	}

	// =============非对称加密测试=============
	@Test
	public void notSymmetricForRSA() throws Exception {
		byte[] publicKey = KeyPathUtil.getPublicKeyFile();
		PublicKey pubKey = CipherUtil.generatePublicKey(Base64.decodeBase64(publicKey),
				DefaultCipherAlgorithms.RSA_None_PKCS1PADDING_1024);

		byte[] privateKey = KeyPathUtil.getPrivateKeyFile();
		PrivateKey priKey = CipherUtil.generatePrivateKey(Base64.decodeBase64(privateKey),
				DefaultCipherAlgorithms.RSA_None_PKCS1PADDING_1024);

		String encryptData = CipherUtil.encrypt(text, pubKey, DefaultCipherAlgorithms.RSA_None_PKCS1PADDING_1024);
		System.out.println("加密：" + encryptData);

		String decryptData = CipherUtil.decrypt(encryptData, priKey,
				DefaultCipherAlgorithms.RSA_None_PKCS1PADDING_1024);
		System.out.println("解密: " + decryptData);
	}

	// =============非对称签名=============
	@Test
	public void RSASign() throws Exception {
		byte[] publicKey = KeyPathUtil.getPublicKeyFile();
		PublicKey pubKey = CipherUtil.generatePublicKey(Base64.decodeBase64(publicKey), SignatureAlgorithms.MD2withRSA);

		byte[] privateKey = KeyPathUtil.getPrivateKeyFile();
		PrivateKey priKey = CipherUtil.generatePrivateKey(Base64.decodeBase64(privateKey),
				SignatureAlgorithms.MD2withRSA);

		String encryptData = CipherUtil.sign(text, priKey, SignatureAlgorithms.MD2withRSA);
		System.out.println("加密：" + encryptData);

		boolean verifyResult = CipherUtil.verifySign(text, pubKey, encryptData, SignatureAlgorithms.MD2withRSA);
		System.out.println("解密: " + verifyResult);
	}

	@Test
	public void DSASign() throws Exception {
		text = "123456789012345678901";// 只能加密20个字节
		KeyPair keyPair = CipherUtil.generateKeyPair(SignatureAlgorithms.SHA256withDSA);
		PrivateKey privateKey = keyPair.getPrivate();
		System.out.println("私钥长度：" + privateKey.getEncoded().length);

		String encryptData = CipherUtil.sign(text, privateKey, SignatureAlgorithms.SHA256withDSA);
		System.out.println("加密：" + encryptData);

		PublicKey publicKey = keyPair.getPublic();
		System.out.println("公钥长度：" + Hex.toHexString(publicKey.getEncoded()));
		boolean verifyResult = CipherUtil.verifySign(text, publicKey, encryptData, SignatureAlgorithms.SHA256withDSA);
		System.out.println("解密: " + verifyResult);
	}
}
