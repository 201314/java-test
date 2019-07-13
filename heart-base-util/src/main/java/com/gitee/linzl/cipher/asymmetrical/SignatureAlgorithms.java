package com.gitee.linzl.cipher.asymmetrical;

import com.gitee.linzl.cipher.IAlgorithm;

/**
 * JDK默认支持
 * 
 * 签名算法：私钥签名，公钥验签 ，用于身份认证
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年11月2日
 */
public enum SignatureAlgorithms implements IAlgorithm {
	RawDSA("DSA", "RawDSA", null), // 只能20个字节
	// 将正文通过SHA1散列之后，将密文再次通过生成的DSA密钥加密，生成数字签名
	SHA1withDSA("DSA", "SHA1withDSA", null),
	// 将正文通过SHA224散列之后，将密文再次通过生成的DSA密钥加密，生成数字签名
	SHA224withDSA("DSA", "SHA224withDSA", null),
	// 将正文通过SHA256散列之后，将密文再次通过生成的DSA密钥加密，生成数字签名
	SHA256withDSA("DSA", "SHA256withDSA", null),
	// 将正文通过MD2数字摘要后，将密文再次通过生成的RSA密钥加密，生成数字签名
	MD2withRSA("RSA", "MD2withRSA", null),
	// 将正文通过MD5数字摘要后，将密文再次通过生成的RSA密钥加密，生成数字签名
	MD5withRSA("RSA", "MD5withRSA", 512),
	// 通过SHA1算法签名并通过RSA算法加密之后得到签名
	SHA1withRSA("RSA", "SHA1withRSA", 2048), // RSA,对RSA密钥的长度不限制，推荐使用2048位以上

	SHA256withRSA("RSA", "SHA256withRSA", 2048), // RSA2,支付宝强制要求RSA密钥的长度至少为2048

	SHA384withRSA("RSA", "SHA384withRSA", null),

	SHA512withRSA("RSA", "SHA512withRSA", null);
	private String keyAlgorithm;
	private String signAlgorithm;
	private Integer size;

	private SignatureAlgorithms(String keyAlgorithm, String signAlgorithm, Integer size) {
		this.keyAlgorithm = keyAlgorithm;
		this.signAlgorithm = signAlgorithm;
		this.size = size;
	}

	@Override
	public String getKeyAlgorithm() {
		return keyAlgorithm;
	}

	@Override
	public Integer getSize() {
		return this.size;
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
