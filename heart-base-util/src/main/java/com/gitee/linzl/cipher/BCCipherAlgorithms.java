package com.gitee.linzl.cipher;

/**
 * 支持BouncyCastleProvider
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年11月2日
 */
public enum BCCipherAlgorithms implements IAlgorithm {
	AES_ECB_PKCS7PADDING_128("AES", "AES/ECB/PKCS7Padding", 128),

	AES_ECB_PKCS7PADDING_192("AES", "AES/ECB/PKCS7Padding", 192),

	AES_ECB_PKCS7PADDING_256("AES", "AES/ECB/PKCS7Padding", 256),
	// 非对称加解密 RSA, DSA, EC
	// ============RSA============
	RSA_None_PKCS1PADDING_1024("RSA", "RSA/None/PKCS1Padding", 1024);

	private String keyAlgorithm;
	private String cipherAlgorithm;
	private Integer size;

	private BCCipherAlgorithms(String keyAlgorithm, String cipherAlgorithm, Integer size) {
		this.keyAlgorithm = keyAlgorithm;
		this.size = size;
		this.cipherAlgorithm = cipherAlgorithm;
	}

	@Override
	public String getKeyAlgorithm() {
		return keyAlgorithm;
	}

	@Override
	public Integer getSize() {
		return size;
	}

	@Override
	public String getCipherAlgorithm() {
		return cipherAlgorithm;
	}

	@Override
	public String getSignAlgorithm() {
		// TODO Auto-generated method stub
		return null;
	}
}
