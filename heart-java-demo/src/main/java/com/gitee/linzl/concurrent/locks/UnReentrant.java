package com.gitee.linzl.concurrent.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnReentrant {
	Lock lock = new ReentrantLock();

	public void outer() {
		try {
			lock.lock();
			System.out.println("锁一次");
			inner();// 可重入锁，不然锁死自己吗
		} finally {
			lock.unlock();
		}
	}

	public void inner() {
		try {
			lock.lock();
			// do something
			System.out.println("锁2次");
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		UnReentrant test = new UnReentrant();
		test.outer();
	}
}
