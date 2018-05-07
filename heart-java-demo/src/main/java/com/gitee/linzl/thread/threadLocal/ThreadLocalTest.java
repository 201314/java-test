package com.gitee.linzl.thread.threadLocal;

public class ThreadLocalTest {
	public static ThreadLocal tl = new ThreadLocal();

	class ThreadA implements Runnable {
		public void run() {
			try {
				for (int i = 0; i < 100; i++) {
					if (tl.get() == null) {
						tl.set("ThreadA" + (i + 1));
					} else {
						System.out.println("ThreadA get Value=" + tl.get());
					}
					Thread.sleep(200);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	class ThreadB implements Runnable {
		public void run() {
			try {
				for (int i = 0; i < 100; i++) {
					if (tl.get() == null) {
						tl.set("ThreadB" + (i + 1));
					} else {
						System.out.println("ThreadB get Value=" + tl.get());
					}
					Thread.sleep(200);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		ThreadLocalTest test = new ThreadLocalTest();
		ThreadLocalTest.ThreadA tA = test.new ThreadA();
		ThreadLocalTest.ThreadB tB = test.new ThreadB();

		new Thread(tA).start();
		new Thread(tB).start();
	}
}
