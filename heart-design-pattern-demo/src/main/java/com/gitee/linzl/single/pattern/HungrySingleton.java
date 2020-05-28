package com.gitee.linzl.single.pattern;

/**
 * 饿汉模式：肚子饿嘛，要提前准备好吃的
 * 
 * @author GDCC i心灵鸡汤you email:2225010489@qq.com 2013-4-27 下午08:38:31
 */

public class HungrySingleton {

	private static final HungrySingleton SINGLE = new HungrySingleton();

	private HungrySingleton() {
	}

	public static HungrySingleton getInstance() {
		return SINGLE;
	}
}
