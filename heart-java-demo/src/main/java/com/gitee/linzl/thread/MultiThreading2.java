package com.gitee.linzl.thread;

/**
 * 请编写一个多线程程序，实现2个线程， 其中一个线程完成对某个对象的int成员变量的增加操作，即每次加1，
 * 
 * 另一个线程完成对该对象的成员变量的减操作，即每次减1，同时要保证该变量的值不会小于0，不会大于1，该变量的初始值为0.
 * 
 * 即01010101不断的间隔 在命令行打印出该值
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年3月1日
 */
public class MultiThreading2 {
	private int x = 0;

	public synchronized void add() {// 不能大于1,也不能小于0
		if (x < 1) {
			System.out.print(x);
			x++;
		}
	}

	public synchronized void sub() {// 不能大于1,也不能小于0
		if (x > 0) {
			System.out.print(x);
			x--;
		}
	}

	class Add implements Runnable {
		public void run() {
			while (true) {
				add();
			}
		}
	}

	class Sub implements Runnable {
		public void run() {
			while (true) {
				sub();
			}
		}
	}

	public static void main(String[] args) {
		MultiThreading2 test = new MultiThreading2();

		Thread thread1 = new Thread(test.new Add());
		thread1.start();
		Thread thread2 = new Thread(test.new Sub());
		thread2.start();
	}
}
