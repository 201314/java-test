package com.gitee.linzl.crypto;

/**
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年11月2日
 */
public interface IAlgorithm {
	/**
	 * 密钥的算法名称
	 */
	String getKeyAlgorithm();

	/**
	 * 密钥的大小
	 * 
	 * @return
	 */
	Integer getSize();

	/**
	 * 密码器算法
	 * 
	 * @return
	 */
	String getCipherAlgorithm();

	/**
	 * 签名时用的算法
	 * 
	 * @return
	 */
	String getSignAlgorithm();
}
