package com.gitee.linzl.cipher;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;

import com.gitee.linzl.cipher.asymmetrical.AsymmetricalCipherUtil;
import com.gitee.linzl.cipher.asymmetrical.RSACipherAlgorithms;

public class RSATest {
	private String text = null;

	@Before
	public void init() {
		text = "11站在云端，敲下键盘，望着通往世界另一头的那扇窗，只为做那读懂0和1的人。。";
		System.out.println("原文：" + text);
	}

	// =============非对称加密测试=============
	@Test
	public void RSA_None_PKCS1PADDING_1024() throws Exception {
		execute(RSACipherAlgorithms.RSA_None_PKCS1PADDING_1024);
		execute22(RSACipherAlgorithms.RSA_None_PKCS1PADDING_1024);
	}

	@Test
	public void RSA_ECB_PKCS1PADDING_1024() throws Exception {
		execute(RSACipherAlgorithms.RSA_ECB_PKCS1PADDING_1024);
		executeRandom(RSACipherAlgorithms.RSA_ECB_PKCS1PADDING_1024);
	}

	@Test
	public void RSA_ECB_PKCS1PADDING_2048() throws Exception {
		execute(RSACipherAlgorithms.RSA_ECB_PKCS1PADDING_2048);
		executeRandom(RSACipherAlgorithms.RSA_ECB_PKCS1PADDING_2048);
	}

	private void execute(IAlgorithm algorithm) throws Exception {
		System.out.println("start===========指定密钥===========start");
		byte[] publicKey = KeyPairPathUtil.getPublicKeyFile();
		PublicKey pubKey = AsymmetricalCipherUtil.generatePublic(Base64.decodeBase64(publicKey), algorithm);
		BasePrint.printPublicKey(pubKey.getEncoded());

		byte[] encryptData = AsymmetricalCipherUtil.encrypt(text.getBytes(), pubKey, algorithm);
		BasePrint.printEncryptData(encryptData);

		byte[] privateKey = KeyPairPathUtil.getPrivateKeyFile();
		PrivateKey priKey = AsymmetricalCipherUtil.generatePrivate(Base64.decodeBase64(privateKey), algorithm);
		BasePrint.printPrivateKey(priKey.getEncoded());

		byte[] decryptData = AsymmetricalCipherUtil.decrypt(encryptData, priKey, algorithm);
		BasePrint.printDecryptData(decryptData);
		System.out.println("end===========指定密钥===========end");
	}

	private void execute22(IAlgorithm algorithm) throws Exception {
		byte[] privateKey = KeyPairPathUtil.getPrivateKeyFile();
		PrivateKey priKey = AsymmetricalCipherUtil.generatePrivate(Base64.decodeBase64(privateKey), algorithm);
		BasePrint.printPrivateKey(priKey.getEncoded());

		byte[] encryptData = AsymmetricalCipherUtil.encrypt(text.getBytes(), priKey, algorithm);
		BasePrint.printEncryptData(encryptData);

		byte[] publicKey = KeyPairPathUtil.getPublicKeyFile();
		PublicKey pubKey = AsymmetricalCipherUtil.generatePublic(Base64.decodeBase64(publicKey), algorithm);
		BasePrint.printPublicKey(pubKey.getEncoded());

		byte[] decryptData = AsymmetricalCipherUtil.decrypt(encryptData, pubKey, algorithm);
		BasePrint.printDecryptData(decryptData);
		System.out.println("end===========指定密钥===========end");
	}

	private void executeRandom(IAlgorithm algorithm) throws Exception {
		System.out.println("start===========JDK随机密钥===========start");
		KeyPair keyPair = AsymmetricalCipherUtil.generateKeyPair(algorithm);
		PublicKey pubKey = keyPair.getPublic();
		BasePrint.printPublicKey(pubKey.getEncoded());

		byte[] encryptData = AsymmetricalCipherUtil.encrypt(text.getBytes(), pubKey, algorithm);
		BasePrint.printEncryptData(encryptData);

		PrivateKey priKey = keyPair.getPrivate();
		BasePrint.printPrivateKey(priKey.getEncoded());

		byte[] decryptData = AsymmetricalCipherUtil.decrypt(encryptData, priKey, algorithm);
		BasePrint.printDecryptData(decryptData);
		System.out.println("end===========JDK随机密钥===========end");
	}
}
