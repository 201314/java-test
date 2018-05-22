package com.gitee.linzl.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ++count等价于虚拟机顺次执行如下5条字节码指令
 * 
 * getstatic 获取指定类的静态域，并将其值压入栈顶
 * 
 * iconst_1 将int型1推送至栈顶
 * 
 * iadd 将栈顶两int型数值相加并将结果压入栈顶
 * 
 * dup 复制栈顶数值并将复制值压入栈顶
 * 
 * putstatic 为指定类的静态域赋值
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
// AtomicBoolean，AtomicInteger，AtomicLong 线程安全的基本类型的原子性操作
public class AtomicIntegerDemo implements Runnable {
	private final AtomicInteger count = new AtomicInteger(0);

	public void run() {
		// 自增在底层中使用 Unsafe.compareAndSwapInt
		System.out.println(Thread.currentThread().getName() + ":" + count.incrementAndGet());
	}

	public static void main(String[] args) {
		AtomicIntegerDemo counter = new AtomicIntegerDemo();
		Thread t1 = new Thread(counter);
		Thread t2 = new Thread(counter);
		Thread t3 = new Thread(counter);
		Thread t4 = new Thread(counter);
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
}