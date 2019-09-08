package com.gitee.linzl.single.pattern;

/**
 * 懒汉模式：因为懒，所以等到要用的声明才准备吃东西(初始化)，但是人家正在用的话(synchronized)，你就得等咯
 * 
 * @author GDCC i心灵鸡汤you email:2225010489@qq.com 2013-4-27 下午08:38:31
 */
public class LazySingleton {
	private volatile static LazySingleton single;

	private LazySingleton() {
	}

	// 双重检查标准声明对象,多线程 考虑加锁
	public static LazySingleton getInstance() {
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
