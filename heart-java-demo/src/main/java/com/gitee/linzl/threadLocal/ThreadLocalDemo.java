package com.gitee.linzl.threadLocal;

/**
 * ThreadLocal，数据与当前线程绑定，相当于一个副本，达到共享的作用，且多个线程前互不影响
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年10月14日
 */
public class ThreadLocalDemo {
	public static ThreadLocal<String> tl = new ThreadLocal<>();

	class ThreadA implements Runnable {
		@Override
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
		@Override
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
		ThreadLocalDemo test = new ThreadLocalDemo();
		ThreadLocalDemo.ThreadA tA = test.new ThreadA();
		ThreadLocalDemo.ThreadB tB = test.new ThreadB();

		new Thread(tA).start();
		new Thread(tB).start();
	}
}
