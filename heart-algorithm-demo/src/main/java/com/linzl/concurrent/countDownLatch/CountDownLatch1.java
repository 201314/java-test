package com.linzl.concurrent.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * Executors\Future
 * 
 * CountDownLatch计数器，不允许在执行过程中变更线程数量
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年3月3日
 */
public class CountDownLatch1 {
	public static volatile int num = 0;
	/**
	 * 使用CountDownLatch来等待30个线程执行完
	 */
	static CountDownLatch countDownLatch = new CountDownLatch(30);

	public static void main(String[] args) throws InterruptedException {
		// 开启30个线程进行累加操作
		for (int i = 0; i < 30; i++) {
			new Thread() {
				public void run() {
					for (int j = 0; j < 10000; j++) {
						num++;// 自加操作
					}
					countDownLatch.countDown();
				}
			}.start();
		}

		// 等待计算线程执行完
		countDownLatch.await();
		System.out.println("等待计算线程执行完,最后输出:" + num);
	}
}