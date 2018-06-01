package com.gitee.linzl.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Atomic包中的类按照操作的数据类型可以分成4组
 * 
 * 线程安全的基本类型的原子性操作:AtomicBoolean，AtomicInteger，AtomicLong
 * 
 * 线程安全的数组类型的原子性操作，它操作的不是整个数组，而是数组中的单个元素:
 * 
 * AtomicIntegerArray，AtomicLongArray，AtomicReferenceArray
 * 
 * 基于反射原理对象中的基本类型（长整型、整型和引用类型）进行线程安全的操作:
 * AtomicLongFieldUpdater，AtomicIntegerFieldUpdater，AtomicReferenceFieldUpdater
 * 
 * 线程安全的引用类型及防止ABA问题的引用类型的原子操作:
 * AtomicReference，AtomicMarkableReference，AtomicStampedReference
 * 
 * 我们一般常用的AtomicInteger、AtomicReference和AtomicStampedReference
 * 
 * 使用AtomicMarkableReference，AtomicStampedReference。
 * 使用上述两个Atomic类进行操作。他们在实现compareAndSet指令的时候除了要比较当对象的前值和预期值以外，还要比较当前（操作的）戳值和预期（操作的）戳值，当全部相同时，compareAndSet方法才能成功。每次更新成功，戳值都会发生变化，戳值的设置是由编程人员自己控制的。
 */
public class SellTickets {
	AtomicInteger tickets = new AtomicInteger(100);

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

	public static void main(String[] args) {
		SellTickets st = new SellTickets();
		System.out.println(Runtime.getRuntime().availableProcessors());
		new Thread(st.new Seller(), "SellerA").start();
		new Thread(st.new Seller(), "SellerB").start();
	}
}