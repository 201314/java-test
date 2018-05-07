package com.gitee.linzl.ext;

import java.util.Calendar;
import java.util.UUID;

/**
 * 主鍵生成器
 * 
 * @author linzl
 */
public class KeyGeneratorUtil {

	/**
	 * 主键生成器使用这个做为ID
	 * 
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String getTimstamp() {
		return String.valueOf(Calendar.getInstance().getTimeInMillis());
	}

	public static void main(String args[]) {
		System.out.println("111==>" + getUUID());
		System.out.println("222==>" + getTimstamp().length());
	}

}