package com.gitee.linzl.concurrent.locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * @description 解决synchronized读读之间互斥的性能问题，做到读和读互不影响，读和写互斥，写和写互斥
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年5月16日
 */
public class ReadAndWriteLock {
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private ReadLock readLock = lock.readLock();
	private WriteLock writeLock = lock.writeLock();

	// 读操作
	public void readFile(String name) {
		readLock.lock();
		boolean writeLocked = lock.isWriteLocked();
		if (!writeLocked) {
			System.out.println("当前为读锁！");
		}
		try {
			System.out.println(name + ":正在进行读操作……");
			Thread.sleep(3000);
			System.out.println(name + ":读操作完毕！");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println("释放读锁！");
			readLock.unlock();
		}
	}

	// 写操作
	public void writeFile(String name) {
		writeLock.lock();
		boolean writeLocked = lock.isWriteLocked();
		if (writeLocked) {
			System.out.println("当前为写锁！");
		}
		try {
			System.out.println(name + ":正在进行写操作……");
			Thread.sleep(3000);
			System.out.println(name + ":写操作完毕！");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println("释放写锁！");
			writeLock.unlock();
		}
	}

	public static void main(String[] args) {
		final ReadAndWriteLock lock = new ReadAndWriteLock();
		// 建N个线程，同时读
		ExecutorService excute = Executors.newCachedThreadPool();
		Thread t1 = new Thread(() -> {
			lock.readFile("我是读T111");
		});
		Thread t2 = new Thread(() -> {
			lock.readFile("我是读T222");
		});
		Thread t3 = new Thread(() -> {
			lock.writeFile("我是写T333");
		});
		Thread t4 = new Thread(() -> {
			lock.writeFile("我是写T444");
		});
		// 两个读锁，不互斥
		excute.execute(t1);
		// 两个读锁，不互斥
		excute.execute(t2);
		// 只要出现写就互斥
		excute.execute(t3);
		excute.execute(t4);
	}
}