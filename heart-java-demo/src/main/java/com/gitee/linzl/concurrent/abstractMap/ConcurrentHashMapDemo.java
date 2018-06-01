package com.gitee.linzl.concurrent.abstractMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Like Hashtable but unlike HashMap, this class does not allow null to be used
 * as a key or value.
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年5月23日
 */
public class ConcurrentHashMapDemo {

	public static void test1() {
		Map<String, Integer> count = new ConcurrentHashMap<>();
		CountDownLatch endLatch = new CountDownLatch(2);

		Runnable task = new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 5; i++) {
					Integer value = count.get("a");
					// 以下复合操作，在并发情况下，会出现意外
					if (null == value) {
						count.put("a", 1);
					} else {
						count.put("a", value + 1);
					}
				}
				endLatch.countDown();
			}
		};
		new Thread(task).start();
		new Thread(task).start();

		try {
			endLatch.await();
			System.out.println(count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void test2() {
		Map<String, AtomicInteger> count = new ConcurrentHashMap<>();
		CountDownLatch endLatch = new CountDownLatch(2);

		Runnable task = new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 5; i++) {
					AtomicInteger value = count.get("a");
					System.out.println("value:" + value);
					if (null == value) {
						AtomicInteger atomic = new AtomicInteger(1);
						AtomicInteger now = count.putIfAbsent("a", atomic);
						System.out.println("now:" + now);
					} else {
						value.incrementAndGet();
						System.out.println("putIfAbsent:" + count);
					}
				}
				endLatch.countDown();
			}
		};
		new Thread(task).start();
		new Thread(task).start();

		try {
			endLatch.await();
			System.out.println(count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		test2();
	}
}
