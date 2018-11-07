package com.gitee.linzl.cipher.asymmetrical;

import com.gitee.linzl.cipher.IAlgorithm;

/**
 * JDK默认支持
 * 
 * SunJCE: Cipher.RSA -> com.sun.crypto.provider.RSACipher attributes:
 * {SupportedPaddings=NOPADDING|PKCS1PADDING|OAEPPADDING|OAEPWITHMD5ANDMGF1PADDING
 * 
 * |OAEPWITHSHA1ANDMGF1PADDING|OAEPWITHSHA-1ANDMGF1PADDING|OAEPWITHSHA-224ANDMGF1PADDING
 * 
 * |OAEPWITHSHA-256ANDMGF1PADDING|OAEPWITHSHA-384ANDMGF1PADDING|OAEPWITHSHA-512ANDMGF1PADDING,
 * SupportedModes=ECB,
 * SupportedKeyClasses=java.security.interfaces.RSAPublicKey|java.security.interfaces.RSAPrivateKey}
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年11月2日
 */
public enum RSACipherAlgorithms implements IAlgorithm {
	// 非对称加解密 RSA, DSA, EC
	// ============RSA============
	// JDK支持
	RSA_ECB_PKCS1PADDING_1024("RSA", "RSA/ECB/PKCS1Padding", 1024),
	// JDK支持
	RSA_ECB_PKCS1PADDING_2048("RSA", "RSA/ECB/PKCS1Padding", 2048);

	// 加密的数据长度受限 TODO
	// RSA_ECB_OAEPWITHSHA_1ANDMGF1PADDING_1024("RSA",
	// "RSA/ECB/OAEPWithSHA-1AndMGF1Padding", 1024),
	//
	// RSA_ECB_OAEPWITHSHA_1ANDMGF1PADDING_2048("RSA",
	// "RSA/ECB/OAEPWithSHA-1AndMGF1Padding", 2048),
	//
	// RSA_ECB_OAEPWITHSHA_256ANDMGF1PADDING_1024("RSA",
	// "RSA/ECB/OAEPWithSHA-256AndMGF1Padding", 1024),
	//
	// RSA_ECB_OAEPWITHSHA_256ANDMGF1PADDING_2048("RSA",
	// "RSA/ECB/OAEPWithSHA-256AndMGF1Padding", 2048);

	private String keyAlgorithm;
	private String cipherAlgorithm;
	private Integer size;

	private RSACipherAlgorithms(String keyAlgorithm, String cipherAlgorithm, Integer size) {
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
