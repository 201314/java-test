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

	class Inner implements Runnable {
		public void run() {
			service();
		}
	}

	public static void main(String[] args) {
		Service4 service = new Service4();
		Service4.Inner inner = service.new Inner();

		Thread thread = new Thread(inner);
		thread.start();

		Thread thread2 = new Thread(inner);
		thread2.start();
	}

}