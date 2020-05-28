package com.gitee.linzl.cipher.symmetric;

import com.gitee.linzl.cipher.IAlgorithm;

/**
 * JCE(Java Cryptography
 * Extension)即Java密码扩展，是JDK1.4的一个重要部分。它是一组包，它们提供用于加密、密钥生成算法和协商以及 Message
 * Authentication Code（MAC）算法的框架和实现
 * 
 * java 自带的DES/3DES/AES 三种对称加密算法
 * 
 * JDK默认支持
 * 
 * SupportedPaddings=NOPADDING|PKCS5PADDING|ISO10126PADDING,
 * SupportedKeyFormats=RAW,
 * SupportedModes=ECB|CBC|PCBC|CTR|CTS|CFB|OFB|CFB8|CFB16|CFB24|CFB32|CFB40|CFB48|CFB56|CFB64|OFB8|OFB16|OFB24|OFB32|OFB40|OFB48|OFB56|OFB64|GCM|CFB72|CFB80|CFB88|CFB96|CFB104|CFB112|CFB120|CFB128|OFB72|OFB80|OFB88|OFB96|OFB104|OFB112|OFB120|OFB128
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年11月2日
 */
public enum DESCipherAlgorithms implements IAlgorithm {
	// ============DES 单倍长============,密钥长度必须是8字节
	// 算法/工作模式/填充方式
	DES_CBC_NOPADDING_56("DES", "DES/CBC/NoPadding", 56),

	DES_CBC_PKCS5PADDING_56("DES", "DES/CBC/PKCS5Padding", 56),

	DES_ECB_NOPADDING_56("DES", "DES/ECB/NoPadding", 56),
	// 默认使用该模式
	DES_ECB_PKCS5PADDING_56_DEFAULT("DES", "DES/ECB/PKCS5Padding", 56),

	// ============DESede ============
	// 3DES，也称为3DESede或TripleDES,(3倍长)密钥长度必须是24字节
	DESEDE_CBC_NOPADDING_112("DESede", "DESede/CBC/NoPadding", 112),

	DESEDE_CBC_NOPADDING_168("DESede", "DESede/CBC/NoPadding", 168),

	DESEDE_CBC_PKCS5PADDING_112("DESede", "DESede/CBC/PKCS5Padding", 112),

	DESEDE_CBC_PKCS5PADDING_168("DESede", "DESede/CBC/PKCS5Padding", 168),

	DESEDE_ECB_NOPADDING_112("DESede", "DESede/ECB/NoPadding", 112),

	DESEDE_ECB_NOPADDING_168("DESede", "DESede/ECB/NoPadding", 168),

	DESEDE_ECB_PKCS5PADDING_112("DESede", "DESede/ECB/PKCS5Padding", 112),

	DESEDE_ECB_PKCS5PADDING_168("DESede", "DESede/ECB/PKCS5Padding", 168);

	private String keyAlgorithm;
	private String cipherAlgorithm;
	private Integer size;

	DESCipherAlgorithms(String keyAlgorithm, String cipherAlgorithm, Integer size) {
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
