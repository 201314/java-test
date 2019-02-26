package com.gitee.linzl.annotation;

/**
 * 默认不加密
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2019年2月26日
 */
public class NoneEncrypt implements Encrypt {
	@Override
	public String encrypt(String text) {
		return text;
	}
}
