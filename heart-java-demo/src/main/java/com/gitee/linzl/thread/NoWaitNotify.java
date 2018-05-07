package com.gitee.linzl.thread;

import java.util.ArrayList;
import java.util.List;

public class NoWaitNotify {

	public static void main(String[] args) throws InterruptedException {
		NoWaitNotify myThread = new NoWaitNotify();

		Object lock = new Object();
		List<Integer> list = new ArrayList<>();
		Thread2 thread2 = myThread.new Thread2(list, lock);
		Thread3 thread3 = myThread.new Thread3(list, lock);

		Thread run1 = new Thread(thread2);
		run1.start();
		Thread run2 = new Thread(thread3);
		run2.start();
	}

	class Thread2 implements Runnable {
		Object lock;
		List<Integer> list;

		public Thread2(List<Integer> list, Object lock) {
			this.list = list;
			this.lock = lock;
		}

		public void run() {
			synchronized (lock) {
				try {
					for (int i = 0; i < 10; i++) {
						list.add(i);
						if (list.size() == 5) {
							lock.notify();
						}
						System.out.println("添加了" + (i + 1) + "个元素");
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class Thread3 implements Runnable {
		Object lock;
		List<Integer> list;

		public Thread3(List<Integer> list, Object lock) {
			this.list = list;
			this.lock = lock;
		}

		public void run() {
			while (true) {
				synchronized (lock) {
					if (list.size() != 5) {
						System.out.println("wait begin " + System.currentTimeMillis());
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println("wait end  " + System.currentTimeMillis());
					}
				}
			}
		}
	}
}