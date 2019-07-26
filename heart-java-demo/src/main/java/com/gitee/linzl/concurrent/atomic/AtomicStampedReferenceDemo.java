package com.gitee.linzl.concurrent.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

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
 * AtomicReference，AtomicMarkableReference，AtomicStampedReference(防止ABA问题)
 * 
 * 我们一般常用的AtomicInteger、AtomicReference和AtomicStampedReference
 * 
 * 使用AtomicMarkableReference，AtomicStampedReference进行操作。
 * 他们在实现compareAndSet指令的时候除了要比较当对象的前值和预期值以外，
 * 还要比较当前（操作的）戳值和预期（操作的）戳值，当全部相同时，compareAndSet方法才能成功。每次更新成功，戳值都会发生变化，戳值的设置是由编程人员自己控制的。
 */
/**
 * ABA问题的描述如下：
 * 
 * 1、进程P1在共享变量中读到值为A
 * 
 * 2、P1被抢占，进程P2获得CPU时间片并执行
 * 
 * 3、P2把共享变量里的值从A改成了B， 再改回到A
 * 
 * 4、P2被抢占，进程P1获得CPU时间片并执行
 * 
 * 5、P1回来看到共享变量里的值没有被改变，继续按共享变量没有被改变的逻辑执行
 */
public class AtomicStampedReferenceDemo {
	private static AtomicInteger atomicInt = new AtomicInteger(100);
	private static AtomicStampedReference<Integer> atomicStampedRef = new AtomicStampedReference<Integer>(100, 0);

	/**
	 * AtomicInteger发生ABA问题，能正常修改成功
	 */
	public void ABAerror() {
		Thread intT1 = new Thread(() -> {
			atomicInt.compareAndSet(100, 101);
			atomicInt.compareAndSet(101, 100);
		});

		Thread intT2 = new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			boolean c3 = atomicInt.compareAndSet(100, 101);
			System.out.println(c3); // true
		});

		intT1.start();
		intT2.start();
	}

	public void ABAright() {
		Thread thread1 = new Thread(() -> {
			int stamp = atomicStampedRef.getStamp();
			System.out.println("thread1 before sleep : stamp = " + stamp);

			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("thread1 after sleep : stamp = " + atomicStampedRef.getStamp());

			// A --> B
			atomicStampedRef.compareAndSet(100, 101, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
			System.out.println("thread1 A --> B : stamp = " + atomicStampedRef.getStamp());
			// B --> A
			atomicStampedRef.compareAndSet(101, 100, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
			System.out.println("thread1 B --> A : stamp = " + atomicStampedRef.getStamp());
		}, "thread1");

		Thread thread2 = new Thread(() -> {
			int stamp = atomicStampedRef.getStamp();
			System.out.println("thread2 before sleep : stamp = " + stamp); // stamp

			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("thread2 after sleep : stamp = " + atomicStampedRef.getStamp());
			// A --> B 此时时间戳stamp已经发生变化了，不再是第一次的A的时间戳
			boolean c3 = atomicStampedRef.compareAndSet(100, 101, stamp, stamp + 1);
			// 所以正确的写法应该是
			// atomicStampedRef.compareAndSet(100, 101, atomicStampedRef.getStamp(),
			// atomicStampedRef.getStamp() + 1);
			System.out.println("thread2 A --> B : stamp = " + atomicStampedRef.getStamp());
			System.out.println(c3); // false
		}, "thread2");
		thread1.start();
		thread2.start();
	}

	public static void main(String[] args) {
		AtomicStampedReferenceDemo demo = new AtomicStampedReferenceDemo();
//		demo.ABAerror();

		demo.ABAright();
	}

}