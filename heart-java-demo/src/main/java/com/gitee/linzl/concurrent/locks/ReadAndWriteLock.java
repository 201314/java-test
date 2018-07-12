package com.gitee.linzl.concurrent.locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description 解决synchronized读读之间互斥的性能问题，做到读和读互不影响，读和写互斥，写和写互斥
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年5月16日
 */
public class ReadAndWriteLock {
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	// 读操作
	public void readFile(Thread thread) {
		lock.readLock().lock();
		boolean readLock = lock.isWriteLocked();
		if (!readLock) {
			System.out.println("当前为读锁！");
		}
		try {
			for (int i = 0; i < 5; i++) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(thread.getName() + ":正在进行读操作……");
			}
			System.out.println(thread.getName() + ":读操作完毕！");
		} finally {
			System.out.println("释放读锁！");
			lock.readLock().unlock();
		}
	}

	// 写操作
	public void writeFile(Thread thread) {
		lock.writeLock().lock();
		boolean writeLock = lock.isWriteLocked();
		if (writeLock) {
			System.out.println("当前为写锁！");
		}
		try {
			for (int i = 0; i < 5; i++) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(thread.getName() + ":正在进行写操作……");
			}
			System.out.println(thread.getName() + ":写操作完毕！");
		} finally {
			System.out.println("释放写锁！");
			lock.writeLock().unlock();
		}
	}

	public static void main(String[] args) {
		final ReadAndWriteLock lock = new ReadAndWriteLock();
		// 建N个线程，同时读
		ExecutorService excute = Executors.newCachedThreadPool();
		excute.execute(new Runnable() {// 两个读锁，不互斥
			@Override
			public void run() {
				lock.readFile(Thread.currentThread());
			}
		});
		excute.execute(new Runnable() {// 两个读锁，不互斥
			@Override
			public void run() {
				lock.readFile(Thread.currentThread());
			}
		});

		excute.execute(new Runnable() {// 写锁与读锁、写锁互斥
			@Override
			public void run() {
				lock.writeFile(Thread.currentThread());
			}
		});
		excute.execute(new Runnable() {// 写锁与读锁、写锁互斥
			@Override
			public void run() {
				lock.writeFile(Thread.currentThread());
			}
		});
	}

}