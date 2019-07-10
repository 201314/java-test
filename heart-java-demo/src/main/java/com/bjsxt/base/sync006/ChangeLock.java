package com.bjsxt.base.sync006;

/**
 * 锁对象的改变问题：
 * 
 * 可以看出当synchronized()中被加锁的对象如果是基本类型的包装类型或者是String类型，在一个线程获得该对象的锁之后在线程体里面如果改变了其引用对象，那么他所持有的
 * 锁会立即被释放，此时极有可能会造成安全问题，如果是一些其它类型，当我们改变它们的属性（成员变量）时，锁并不会被释放。
 */
public class ChangeLock {
	private String lock = "lock";

	private void method() {
		synchronized (lock) {
			try {
				System.out.println("当前线程 : " + Thread.currentThread().getName() + "开始,lock=" + lock);
				lock = "change lock";
				Thread.sleep(10000);
				System.out.println("当前线程 : " + Thread.currentThread().getName() + "结束");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		ChangeLock changeLock = new ChangeLock();
		new Thread(() -> {
			changeLock.method();
		}, "t1").start();

		new Thread(() -> {
			changeLock.method();
		}, "t2").start();
	}
}
