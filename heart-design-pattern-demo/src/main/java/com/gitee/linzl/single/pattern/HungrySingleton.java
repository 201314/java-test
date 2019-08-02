package com.gitee.linzl.single.pattern;

/**
 * 饿汉模式：肚子饿嘛，要提前吃
 * 
 * @author GDCC i心灵鸡汤you email:2225010489@qq.com 2013-4-27 下午08:38:31
 */

public class HungrySingleton {

	private static HungrySingleton single = new HungrySingleton();

	private HungrySingleton() {
	}

	public static HungrySingleton getInstance() {
		return single;
	}
}
