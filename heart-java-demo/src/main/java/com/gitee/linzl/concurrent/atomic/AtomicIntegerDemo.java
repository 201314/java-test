package com.gitee.linzl.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

// AtomicBoolean，AtomicInteger，AtomicLong 线程安全的基本类型的原子性操作
public class AtomicIntegerDemo {
	private AtomicInteger count = new AtomicInteger(0);

	class Inc implements Runnable {
		@Override
		public void run() {
			// 自增在底层中使用 Unsafe.compareAndSwapInt
			System.out.println(Thread.currentThread().getName() + ":" + count.incrementAndGet());
		}
	}

	private AtomicInteger tickets = new AtomicInteger(100);

	class Seller implements Runnable {
		@Override
		public void run() {
			while (tickets.get() > 0) {
				int tmp = tickets.get();
				if (tickets.compareAndSet(tmp, tmp - 1)) {
					System.out.println(Thread.currentThread().getName() + " " + tmp);
				}
			}
		}
	}
}