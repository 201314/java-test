package com.gitee.linzl.thread.demo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnReentrant {
	Lock lock = new ReentrantLock();

	public void outer() {
		lock.lock();
		System.out.println("锁一次");
		inner();
		lock.unlock();
	}

	public void inner() {
		lock.lock();
		// do something
		System.out.println("锁2次");
		lock.unlock();
	}

	public static void main(String[] args) {
		UnReentrant test = new UnReentrant();
		test.outer();
	}
}
