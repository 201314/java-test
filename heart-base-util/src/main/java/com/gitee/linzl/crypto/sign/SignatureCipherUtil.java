package com.gitee.linzl.crypto.sign;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import com.gitee.linzl.crypto.IAlgorithm;

/**
 * 签名工具
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年11月6日
 */
public class SignatureCipherUtil {
	/**
	 * 对数据进行签名
	 * 
	 * @param data
	 *            需要签名的数据
	 * @param privateKey
	 *            私钥
	 * @param algorithm
	 *            签名算法
	 * @return
	 * @throws Exception
	 */
	public static byte[] sign(byte[] data, PrivateKey privateKey, IAlgorithm algorithm) throws Exception {
		// 用私钥对信息生成数字签名
		String signAlgorithm = algorithm.getSignAlgorithm();
		Signature signature = Signature.getInstance(signAlgorithm);
		signature.initSign(privateKey);
		signature.update(data);
		return signature.sign();
	}

	/**
	 * 验证签名
	 * 
	 * @param data
	 *            签名前的数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            签名后的数据
	 * @return
	 * @throws Exception
	 */
	public static boolean verifySign(byte[] data, PublicKey publicKey, byte[] sign, IAlgorithm algorithm)
			throws Exception {
		// 用私钥对信息生成数字签名
		String signAlgorithm = algorithm.getSignAlgorithm();
		Signature signature = Signature.getInstance(signAlgorithm);
		signature.initVerify(publicKey);
		signature.update(data);

		// 执行加密操作,加密后的结果通常都会用Base64编码进行传输
		// 验证签名是否正常
		return signature.verify(sign);
	}
}
