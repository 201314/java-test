package com.gitee.linzl.single.pattern;

/**
 * 懒汉模式：推荐使用此种方式
 * 
 * @author GDCC i心灵鸡汤you email:2225010489@qq.com 2013-4-27 下午08:38:31
 */
public class LazySingletonV2 {

	private LazySingletonV2() {
	}

	/**
	 * 1、加载一个类时，其内部类不会同时被加载
	 * 
	 * 2、一个类加载，当且仅当其某个静态成员（静态域，构造器，静态方法等）被调用时发生
	 */
	static class SingletonHolder {
		// 由于对象实例化是在内部类加载的时候去创建的，因此是线程安全的。
		// 因为在方法中创建对象，才存在并发问题，静态内部类随着方法调用而被加载，只加载一次，并不存在并发问题，所以是线程安全的。
		private static final LazySingletonV2 INSTANCE = new LazySingletonV2();
	}

	// 没有加锁，不会有性能损耗
	public static LazySingletonV2 getInstance() {
		return SingletonHolder.INSTANCE;
	}
}
