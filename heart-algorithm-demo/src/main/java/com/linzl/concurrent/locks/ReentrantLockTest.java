package com.linzl.concurrent.locks;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
	private ReentrantLock lock = new ReentrantLock();
	private static int num = 0;

	public int getNum() {
		return num;
	}

	public void add() {
		lock.lock(); // block until condition holds
		try {
			for (int j = 0; j < 100; j++) {
				num++;// 自加操作
			}
		} finally {
			lock.unlock();
			System.out.println(Thread.currentThread().getName() + "=num值为:" + getNum());
		}
	}

	public static void main(String[] args) {
		ReentrantLockTest test = new ReentrantLockTest();
		// 开启30个线程进行累加操作
		for (int i = 0; i < 30; i++) {
			new Thread() {
				public void run() {
					// for (int j = 0; j < 5; j++) {
					test.add();
					// }
				}
			}.start();
		}
	}
}
