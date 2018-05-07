package day11.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入（防止自己卡死自己）
 * 
 * 可中断
 * 
 * 
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

	public static void main(String[] args) throws InterruptedException {
		ReentrantLockDemo test = new ReentrantLockDemo();
		// 开启30个线程进行累加操作
		Thread first = new Thread() {
			public void run() {
				test.add();
			}
		};
		Thread second = new Thread() {
			public void run() {
				test.add();
			}
		};

		first.start();
		second.start();

		// 等待线程结束
		first.join();
		second.join();
		System.out.println("线程结束:" + test.getNum());
	}
}
