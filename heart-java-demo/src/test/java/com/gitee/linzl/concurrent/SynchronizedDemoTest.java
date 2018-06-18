package com.gitee.linzl.concurrent;

import org.junit.Test;

public class SynchronizedDemoTest {
	/**
	 * 同一资源，产生竞争，锁同步，必须按顺序
	 */
	@Test
	public void test1() {
		SynchronizedDemo demo = new SynchronizedDemo();

		Thread thread1 = new Thread(() -> {
			demo.print();
		}, "thread1");

		Thread thread2 = new Thread(() -> {
			demo.print();
		}, "thread2");

		thread1.start();
		thread2.start();
	}

	/**
	 * 一个是this对象锁,一个是Class锁,锁不同,不互斥,异步执行
	 */
	@Test
	public void test2() {
		SynchronizedDemo demo = new SynchronizedDemo();

		Thread thread1 = new Thread(() -> {
			demo.print();
		}, "thread1");

		Thread thread2 = new Thread(() -> {
			SynchronizedDemo.print2();
		}, "thread2");

		thread1.start();
		thread2.start();
	}

	/**
	 * 一个是this对象锁,一个是Object对象锁,锁不同,不互斥,异步执行
	 */
	@Test
	public void test3() {
		SynchronizedDemo demo = new SynchronizedDemo();

		Thread thread1 = new Thread(() -> {
			demo.print();
		}, "thread1");

		Thread thread2 = new Thread(() -> {
			demo.print3();
		}, "thread2");

		thread1.start();
		thread2.start();
	}

	/**
	 * 两个都是this对象锁，互斥，同步
	 */
	@Test
	public void test4() {
		SynchronizedDemo demo = new SynchronizedDemo();

		Thread thread1 = new Thread(() -> {
			demo.print();
		}, "thread1");

		Thread thread2 = new Thread(() -> {
			demo.print4();
		}, "thread2");

		thread1.start();
		thread2.start();

		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 两个都是this对象锁，互斥，同步
	 */
	@Test
	public void test5() {
		SynchronizedDemo demo = new SynchronizedDemo();

		final Integer param = 11;

		Thread thread1 = new Thread(() -> {
			demo.print5(param);
		}, "thread1");

		Thread thread2 = new Thread(() -> {
			demo.print5(param);
		}, "thread2");

		thread1.start();
		thread2.start();
		try {
			thread1.join();
			thread2.join();
		} catch (Exception e) {

		}
	}
}
