package com.gitee.linzl.annotation;

import com.gitee.linzl.codec.ConvertUtil;

/**
 * 转为16进制
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2019年2月26日
 */
public class HexDecrypt implements Decrypt {

	@Override
	public String decrypt(String text) {
		return ConvertUtil.toHex(text);
	}
}
