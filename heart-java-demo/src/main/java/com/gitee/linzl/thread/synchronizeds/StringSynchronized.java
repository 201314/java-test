package com.gitee.linzl.thread.synchronizeds;

/**
 * synchronized代码块对字符串的锁，注意String常量池的缓存功能
 */
public class StringSynchronized {
	public void method() {
		// 容易导致死锁，使用成 new String("字符串常量")
		synchronized ("字符串常量") {
			try {
				while (true) {
					System.out.println("当前线程 : " + Thread.currentThread().getName() + "开始");
					Thread.sleep(1000);
					System.out.println("当前线程 : " + Thread.currentThread().getName() + "结束");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		StringSynchronized stringSynchronized = new StringSynchronized();
		Thread t1 = new Thread(() -> {
			stringSynchronized.method();
		}, "t1");
		Thread t2 = new Thread(() -> {
			stringSynchronized.method();
		}, "t2");

		t1.start();
		t2.start();
	}
}
