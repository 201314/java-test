package com.gitee.linzl.thread.demo;

public class SynchronizedDemo {
	private Object obj = new Object();

	/**
	 * 直接锁普通方法，就是this对象锁
	 */
	public synchronized void print() {
		// print4();
		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + "=print打印当前：" + i);
		}

	}

	/**
	 * 可重入锁
	 */
	public synchronized void print1() {
		print4();
		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + "=print打印当前：" + i);
		}

	}

	public synchronized static void print2() {
		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + "=print222222打印当前：" + i);
		}
	}

	public void print3() {
		synchronized (obj) {
			for (int i = 0; i < 100; i++) {
				System.out.println(Thread.currentThread().getName() + "=print333333打印当前：" + i);
			}
		}
	}

	public void print4() {
		synchronized (this) {
			for (int i = 0; i < 100; i++) {
				System.out.println(Thread.currentThread().getName() + "=print444444打印当前：" + i);
			}
		}
	}

	/**
	 * 一般不这么做，锁要被控制在使用的类中。不能靠传入。
	 * 
	 * @param param
	 */
	public void print5(Integer param) {
		synchronized (param) {
			for (int i = 0; i < 10; i++) {
				System.out.println(Thread.currentThread().getName() + "=print555555打印当前：" + param);
			}
		}
	}
}
