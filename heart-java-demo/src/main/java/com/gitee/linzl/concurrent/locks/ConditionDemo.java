package com.gitee.linzl.concurrent.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通过Lock实现生产者，消费者
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年3月6日
 */
public class ConditionDemo {
	final Lock lock = new ReentrantLock();
	final Condition ifFull = lock.newCondition();
	final Condition ifEmpty = lock.newCondition();

	final Object[] items = new Object[10];

	int putptr = 0;// 生产的数量
	int takeptr = 0;// 消费的数量
	int count = 0;

	public void put(Object x) throws InterruptedException {
		lock.lock();
		try {
			while (count == items.length) {// 队列满，循环等待
				ifFull.await();
			}

			// 生产消息，入队
			items[putptr] = x;

			if (++putptr == items.length) {// 满了，置回0队列
				putptr = 0;
			}
			System.out.println("放入==" + x);
			++count;
			ifEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	public Object take() throws InterruptedException {
		lock.lock();
		try {
			while (count == 0) {// 队列空，循环等待
				ifEmpty.await();
			}

			Object x = items[takeptr];

			if (++takeptr == items.length) {
				takeptr = 0;
			}
			System.out.println("取出==" + x);
			--count;
			ifFull.signal();
			return x;
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ConditionDemo test = new ConditionDemo();

		for (int i = 0; i < 10; i++) {
			final int index = i;
			new Thread(() -> {
				try {
					test.put("Math>>" + index);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
			new Thread(() -> {
				try {
					test.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		}
	}
}
