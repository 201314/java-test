package com.gitee.linzl.cipher.symmetric;

import com.gitee.linzl.cipher.IAlgorithm;

/**
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
public enum AESCipherAlgorithms implements IAlgorithm {
	// ============AES============
	AES_CBC_NOPADDING_128("AES", "AES/CBC/NoPadding", 128),

	AES_CBC_NOPADDING_192("AES", "AES/CBC/NoPadding", 192),

	AES_CBC_NOPADDING_256("AES", "AES/CBC/NoPadding", 256),

	AES_CBC_PKCS5PADDING_128("AES", "AES/CBC/PKCS5Padding", 128),

	AES_CBC_PKCS5PADDING_192("AES", "AES/CBC/PKCS5Padding", 192),

	AES_CBC_PKCS5PADDING_256("AES", "AES/CBC/PKCS5Padding", 256),

	AES_ECB_NOPADDING_128("AES", "AES/ECB/NoPadding", 128),

	AES_ECB_NOPADDING_192("AES", "AES/ECB/NoPadding", 192),

	AES_ECB_NOPADDING_256("AES", "AES/ECB/NoPadding", 256),
	// 默认使用
	AES_ECB_PKCS5PADDING_128("AES", "AES/ECB/PKCS5Padding", 128),

	AES_ECB_PKCS5PADDING_192("AES", "AES/ECB/PKCS5Padding", 192),
	// 超过JDK默认长度,扩展的JCE
	AES_ECB_PKCS5PADDING_256("AES", "AES/ECB/PKCS5Padding", 256);

	private String keyAlgorithm;
	private String cipherAlgorithm;
	private Integer size;

	private AESCipherAlgorithms(String keyAlgorithm, String cipherAlgorithm, Integer size) {
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
