package com.gitee.linzl.thread.join;

public class ThreadA extends Thread {
	@Override
	public void run() {
		int count = 1;
		for (int i = 0; i < 10; i++) {
			count = (count + 1) * 2;
		}
		System.out.println("ThreadA:" + count);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			ThreadA threadTest = new ThreadA();
			threadTest.start();
			threadTest.join();// 等待threadTest线程结束
			System.out.println("threadTest对象执行完，我再执行");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
