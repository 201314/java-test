package com.linzl.thread.join;

public class ThreadB extends Thread {

	public void run() {
		try {
			ThreadA a = new ThreadA();
			a.start();
			a.join();
			System.out.println("线程B在run end处打印了");
		} catch (InterruptedException e) {
			System.out.println("线程B在catch处打印了");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ThreadB threadB = new ThreadB();
		threadB.start();
		try {
			threadB.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("最后执行");
	}
}