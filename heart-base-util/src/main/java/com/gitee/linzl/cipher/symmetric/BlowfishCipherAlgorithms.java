package com.gitee.linzl.cipher.symmetric;

import com.gitee.linzl.cipher.IAlgorithm;

/**
 * JDK默认支持
 * 
 * SunJCE: Cipher.Blowfish -> com.sun.crypto.provider.BlowfishCipher attributes:
 * {SupportedPaddings=NOPADDING|PKCS5PADDING|ISO10126PADDING,
 * SupportedKeyFormats=RAW,
 * SupportedModes=ECB|CBC|PCBC|CTR|CTS|CFB|OFB|CFB8|CFB16|CFB24|CFB32|CFB40|CFB48|CFB56|CFB64|OFB8|OFB16|OFB24|OFB32|OFB40|OFB48|OFB56|OFB64}
 * 
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年11月2日
 */
public enum BlowfishCipherAlgorithms implements IAlgorithm {
	// ============AES============
	Blowfish("Blowfish", "Blowfish", 128);
	private String keyAlgorithm;
	private String cipherAlgorithm;
	private Integer size;

	BlowfishCipherAlgorithms(String keyAlgorithm, String cipherAlgorithm, Integer size) {
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
