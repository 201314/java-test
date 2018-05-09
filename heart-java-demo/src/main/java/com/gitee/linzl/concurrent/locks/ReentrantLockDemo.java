package com.gitee.linzl.concurrent.locks;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入：防止自己锁死自己
 * 
 * 可中断
 * 
 * 可限时
 * 
 * 公平锁
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年5月7日
 */
public class ReentrantLockDemo {
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

	public void tryAdd() {
		lock.tryLock();
	}

	public static void main(String[] args) {
		ReentrantLockDemo test = new ReentrantLockDemo();
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
