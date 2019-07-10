package com.gitee.linzl.concurrent.locks;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {
	public static void main(String[] args) {
		// 获取当前线程
		final Thread currentThread = Thread.currentThread();

		new Thread(() -> {
			try {
				// 睡眠5秒，等待主线程调用park
				Thread.sleep(5000);
				System.out.println("子线程进行unpark操作！");
				// 进行唤醒给定的currentThread线程
				LockSupport.unpark(currentThread);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

		System.out.println("开始阻塞！");
		// 进行阻塞给定的currentThread线程
		LockSupport.park(currentThread);
		System.out.println("结束阻塞！");
	}
}