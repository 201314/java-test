package com.gitee.linzl.cipher;

import java.util.Base64;

import org.bouncycastle.util.encoders.Hex;

public class BasePrint {
	public static void printSecretKey(byte[] secretKey) {
		System.out.println("========================");
		System.out.println("密钥长度=>" + secretKey.length);
		System.out.println("密钥16进制=>" + Hex.toHexString(secretKey));
		System.out.println("密钥base64=>" + Base64.getEncoder().encodeToString(secretKey));
		System.out.println("密钥base64 2=>" + org.apache.commons.codec.binary.Base64.encodeBase64String(secretKey));
	}

	public static void printEncryptData(byte[] encryptData) {
		System.out.println("========================");
		System.out.println("明文加密后16进制=>" + Hex.toHexString(encryptData));
		System.out.println("明文加密base64=>" + Base64.getEncoder().encodeToString(encryptData));
		System.out.println("明文加密base64 2=>" + org.apache.commons.codec.binary.Base64.encodeBase64String(encryptData));

	}

	public static void printDecryptData(byte[] encryptData) {
		System.out.println("========================");
		System.out.println("密文解密后=>" + new String(encryptData));
	}

	public static void printPrivateKey(byte[] privateKey) {
		System.out.println("=========私钥===============");
		System.out.println("私钥长度=>" + privateKey.length);
		System.out.println("私钥16进制=>" + Hex.toHexString(privateKey));
		System.out.println("私钥base64=>" + Base64.getEncoder().encodeToString(privateKey));
		System.out.println("私钥base64 2=>" + org.apache.commons.codec.binary.Base64.encodeBase64String(privateKey));
	}

	public static void printPublicKey(byte[] publicKey) {
		System.out.println("=========公钥===============");
		System.out.println("公钥长度=>" + publicKey.length);
		System.out.println("公钥16进制=>" + Hex.toHexString(publicKey));
		System.out.println("公钥base64=>" + Base64.getEncoder().encodeToString(publicKey));
		System.out.println("公钥base64 2=>" + org.apache.commons.codec.binary.Base64.encodeBase64String(publicKey));
	}
}
