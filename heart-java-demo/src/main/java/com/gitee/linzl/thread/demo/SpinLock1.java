package com.gitee.linzl.thread.demo;

import java.util.concurrent.atomic.AtomicReference;

public class SpinLock1 {
	private AtomicReference<Thread> owner = new AtomicReference<>();
	private int count = 0;

	public void lock() {
		Thread current = Thread.currentThread();
		if (current == owner.get()) {
			count++;
			return;
		}
		while (!owner.compareAndSet(null, current)) {
			System.out.println("当前线程==" + Thread.currentThread().getName());
		}
	}

	public void unlock() {
		Thread current = Thread.currentThread();
		if (current == owner.get()) {
			if (count != 0) {
				count--;
			} else {
				owner.compareAndSet(current, null);
			}
		}
	}

	public static void main(String[] args) {
		SpinLock1 ss = new SpinLock1();
		new Thread(new Runnable() {
			@Override
			public void run() {
				ss.lock();
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				ss.lock();
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				ss.lock();
			}
		}).start();
	}

}
