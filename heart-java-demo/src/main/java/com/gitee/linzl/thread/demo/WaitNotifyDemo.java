package com.gitee.linzl.thread.demo;

public class WaitNotifyDemo {
	public void testWait(Object obj) {
		synchronized (obj) {
			try {
				System.out.println("我在等待");
				obj.wait();
				System.out.println("====1、锁被释放了，我永远执行不到=====");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void testNotify(Object obj) {
		synchronized (obj) {
			System.out.println("通知");
			obj.notify();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("====2、必须等我执行完,才会释放锁=====");
		}
	}

	public static void main(String[] args) {
		WaitNotifyDemo demo = new WaitNotifyDemo();
		Object obj = new Object();
		Thread first = new Thread(() -> {
			demo.testWait(obj);
		});

		Thread second = new Thread(() -> {
			demo.testNotify(obj);
		});

		second.start();
		first.start();
	}
}
