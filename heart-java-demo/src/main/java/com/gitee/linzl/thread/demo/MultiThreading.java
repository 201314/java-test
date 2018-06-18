package com.gitee.linzl.thread.demo;

/**
 * 有一静态整形变量X，初始值为0，用JAVA写四个线程，二个对其加1，二个对其减一
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年3月1日
 */
public class MultiThreading {
	private static int x = 0;

	public synchronized void add() {
		x++;
		System.out.println(Thread.currentThread().getName() + "-inc:" + x);
	}

	public synchronized void sub() {
		x--;
		System.out.println(Thread.currentThread().getName() + "-sub:" + x);
	}

	public static void main(String[] args) throws InterruptedException {
		MultiThreading multi = new MultiThreading();
		Runnable inc = () -> {
			for (int i = 0; i < 5; i++) {
				multi.add();
			}
		};

		Runnable sub = () -> {
			for (int i = 0; i < 5; i++) {
				multi.sub();
			}
		};

		Thread thread1 = new Thread(inc);
		Thread thread2 = new Thread(inc);

		Thread thread3 = new Thread(sub);
		Thread thread4 = new Thread(sub);

		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();

		// join表示main主线程要等4个线程结束才可结束
		thread1.join();
		thread2.join();
		thread3.join();
		thread4.join();

		System.out.println("最后结果:" + x);
	}
}
