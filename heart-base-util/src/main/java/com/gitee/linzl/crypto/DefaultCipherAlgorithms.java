package com.gitee.linzl.crypto;

/**
 * JDK默认支持
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年11月2日
 */
public enum DefaultCipherAlgorithms implements IAlgorithm {
	// ============AES============
	AEC_CBC_NOPADDING_128("AES", "AES/CBC/NoPadding", 128),

	AES_CBC_PKCS5PADDING_128("AES", "AES/CBC/PKCS5Padding", 128),

	AES_ECB_NOPADDING_128("AES", "AES/ECB/NoPadding", 128),

	AES_ECB_PKCS5PADDING_128("AES", "AES/ECB/PKCS5Padding", 128),

	AES_ECB_PKCS7PADDING_128("AES", "AES/ECB/PKCS7Padding", 128),
	//
	AEC_CBC_NOPADDING_192("AES", "AES/CBC/NoPadding", 192),

	AES_CBC_PKCS5PADDING_192("AES", "AES/CBC/PKCS5Padding", 192),

	AES_ECB_NOPADDING_192("AES", "AES/ECB/NoPadding", 192),

	AES_ECB_PKCS5PADDING_192("AES", "AES/ECB/PKCS5Padding", 192),

	AES_ECB_PKCS7PADDING_192("AES", "AES/ECB/PKCS7Padding", 192),
	//
	AEC_CBC_NOPADDING_256("AES", "AES/CBC/NoPadding", 256),

	AES_CBC_PKCS5PADDING_256("AES", "AES/CBC/PKCS5Padding", 256),

	AES_ECB_NOPADDING_256("AES", "AES/ECB/NoPadding", 256),
	// 超过JDK默认长度,扩展的JCE
	AES_ECB_PKCS5PADDING_256("AES", "AES/ECB/PKCS5Padding", 256),

	AES_ECB_PKCS7PADDING_256("AES", "AES/ECB/PKCS7Padding", 256),
	// ============DES============
	DES_CBC_NOPADDING_56("DES", "DES/CBC/NoPadding", 56),

	DES_CBC_PKCS5PADDING_56("DES", "DES/CBC/PKCS5Padding", 56),

	DES_ECB_NOPADDING_56("DES", "DES/ECB/NoPadding", 56),

	DES_ECB_PKCS5PADDING_56("DES", "DES/ECB/PKCS5Padding", 56),

	// ============DESede============
	DESEDE_CBC_NOPADDING_112("DESede", "DESede/CBC/NoPadding", 112),

	DESEDE_CBC_PKCS5PADDING_112("DESede", "DESede/CBC/PKCS5Padding", 112),

	DESEDE_ECB_NOPADDING_112("DESede", "DESede/ECB/NoPadding", 112),

	DESEDE_ECB_PKCS5PADDING_112("DESede", "DESede/ECB/PKCS5Padding", 112),
	//
	DESEDE_CBC_NOPADDING_168("DESede", "DESede/CBC/NoPadding", 168),

	DESEDE_CBC_PKCS5PADDING_168("DESede", "DESede/CBC/PKCS5Padding", 168),

	DESEDE_ECB_NOPADDING_168("DESede", "DESede/ECB/NoPadding", 168),

	DESEDE_ECB_PKCS5PADDING_168("DESede", "DESede/ECB/PKCS5Padding", 168),

	// 非对称加解密 RSA, DSA, EC
	// ============RSA============
	RSA_None_PKCS1PADDING_1024("RSA", "RSA/None/PKCS1Padding", 1024),

	RSA_ECB_PKCS1PADDING_1024("RSA", "RSA/ECB/PKCS1Padding", 1024),

	RSA_ECB_OAEPWITHSHA_1ANDMGF1PADDING_1024("RSA", "RSA/ECB/OAEPWithSHA-1AndMGF1Padding", 1024),

	RSA_ECB_OAEPWITHSHA_256ANDMGF1PADDING_1024("RSA", "RSA/ECB/OAEPWithSHA-256AndMGF1Padding", 1024),

	RSA_ECB_PKCS1PADDING_2048("RSA", "RSA/ECB/PKCS1Padding", 2048),

	RSA_ECB_OAEPWITHSHA_1ANDMGF1PADDING_2048("RSA", "RSA/ECB/OAEPWithSHA-1AndMGF1Padding", 2048),

	RSA_ECB_OAEPWITHSHA_256ANDMGF1PADDING_2048("RSA", "RSA/ECB/OAEPWithSHA-256AndMGF1Padding", 2048);

	private String keyAlgorithm;
	private Integer size;
	private String cipherAlgorithm;

	private DefaultCipherAlgorithms(String keyAlgorithm, String cipherAlgorithm, Integer size) {
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
