package com.linzl.single.pattern;

/**
 * 懒汉模式：不想干太多的活嘛，有的话就直接拿来用，但是人家正在用的话（synchronized），你就得等咯
 * 
 * @author GDCC i心灵鸡汤you email:2225010489@qq.com 2013-4-27 下午08:38:31
 */

public class LazySingleton {

	private static LazySingleton single;

	private LazySingleton() {
	};

	public static synchronized LazySingleton getInstance() {
		if (single == null)
			single = new LazySingleton();
		return single;
	}

	// 双重检查标准声明对象,多线程 考虑加锁
	public static LazySingleton MultiThreadGetInstance() {
		if (single == null) {
			synchronized (LazySingleton.class) {
				if (single == null) {
					single = new LazySingleton();
				}
			}
		}
		return single;
	}
}
