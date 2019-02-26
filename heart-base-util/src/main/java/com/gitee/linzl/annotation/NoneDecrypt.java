package com.gitee.linzl.annotation;

/**
 * 默认不解密
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2019年2月26日
 */
public class NoneDecrypt implements Decrypt {

	@Override
	public String decrypt(String text) {
		return text;
	}
}
