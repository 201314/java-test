package com.gitee.linzl.thread.demo;

public class Service4 {
	public void service() {
		for (int i = 6; i > 3; i--) {
			System.out.println("当前线程：" + Thread.currentThread().getName() + ":" + i);
		}
		synchronized (this) {
			for (int i = 3; i > 0; i--) {
				System.out.println(Thread.currentThread().getName() + ":加锁:" + i);
			}
		}
	}

	public static void main(String[] args) {
		Service4 service = new Service4();

		Thread thread = new Thread(() -> {
			service.service();
		});
		Thread thread2 = new Thread(() -> {
			service.service();
		});

		thread.start();
		thread2.start();
	}

}