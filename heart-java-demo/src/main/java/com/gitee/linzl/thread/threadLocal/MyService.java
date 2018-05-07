package com.gitee.linzl.thread.threadLocal;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyService {
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	public void testMethod() {
		try {
			lock.lock();
			System.out.println("开始wait");
			condition.await();
			for (int i = 0; i < 5; i++) {
				System.out.println("ThreadName=" + Thread.currentThread().getName() + (" " + (i + 1)));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}