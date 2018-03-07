package com.linzl.thread;

public class Test {
	public volatile static int count = 0;

	public static void inc() {
		// 这里延迟1毫秒，使得结果明显
		for (int i = 0; i < 10000; i++) {
			System.out.println(Thread.currentThread().getName() + "=starting=" + count);
			System.out.println(Thread.currentThread().getName() + "=processing=" + (++count));
		}
	}

	public static void main(String[] args) {
		// 同时启动1000个线程，去进行i++计算，看看实际结果
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() + "=before=" + Test.count);
					Test.inc();
					System.out.println(Thread.currentThread().getName() + "=after=" + Test.count);
				}
			}).start();
		}
		// 这里每次运行的值都有可能不同,可能为1000
		System.out.println("运行结果:Counter.count=" + Test.count);
	}
}