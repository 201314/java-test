package com.gitee.linzl.crypto;

/**
 * JDK默认支持
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年11月2日
 */
public enum SignatureAlgorithms implements IAlgorithm {
	RawDSA("DSA", "RawDSA"), // 只能20个字节

	SHA1withDSA("DSA", "SHA1withDSA"),

	SHA224withDSA("DSA", "SHA224withDSA"),

	SHA256withDSA("DSA", "SHA256withDSA"),

	MD2withRSA("RSA", "MD2withRSA"),

	MD5withRSA("RSA", "MD5withRSA"),

	SHA1withRSA("RSA", "SHA1withRSA"), // RSA,对RSA密钥的长度不限制，推荐使用2048位以上

	SHA256withRSA("RSA", "SHA256withRSA"), // RSA2,支付宝强制要求RSA密钥的长度至少为2048

	SHA384withRSA("RSA", "SHA384withRSA"),

	SHA512withRSA("RSA", "SHA512withRSA");
	private String keyAlgorithm;
	private String signAlgorithm;

	private SignatureAlgorithms(String keyAlgorithm, String signAlgorithm) {
		this.keyAlgorithm = keyAlgorithm;
		this.signAlgorithm = signAlgorithm;
	}

	@Override
	public String getKeyAlgorithm() {
		return keyAlgorithm;
	}

	@Override
	public Integer getSize() {
		return null;
	}

	@Override
	public String getCipherAlgorithm() {
		return null;
	}

	@Override
	public String getSignAlgorithm() {
		return signAlgorithm;
	}

}
