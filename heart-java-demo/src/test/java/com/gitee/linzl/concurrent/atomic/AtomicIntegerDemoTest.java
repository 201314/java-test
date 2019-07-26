package com.gitee.linzl.concurrent.atomic;

import org.junit.Test;

public class AtomicIntegerDemoTest {
	@Test
	public void testInc() {
		AtomicIntegerDemo demo = new AtomicIntegerDemo();
		AtomicIntegerDemo.Inc inc = demo.new Inc();

		Thread t1 = new Thread(inc, "线程1");
		Thread t2 = new Thread(inc, "线程2");
		Thread t3 = new Thread(inc, "线程3");
		Thread t4 = new Thread(inc, "线程4");
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}

	@Test
	public void testSellTicket() {
		AtomicIntegerDemo demo = new AtomicIntegerDemo();
		System.out.println(Runtime.getRuntime().availableProcessors());
		new Thread(demo.new Seller(), "SellerA").start();
		new Thread(demo.new Seller(), "SellerB").start();
	}
}
