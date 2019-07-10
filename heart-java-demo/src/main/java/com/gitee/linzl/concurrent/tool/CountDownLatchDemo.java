package com.gitee.linzl.concurrent.tool;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch计数器，不允许在执行过程中变更线程数量
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年3月3日
 */
public class CountDownLatchDemo {
	private static int N = 10;

	public static void main(String[] args) throws InterruptedException {
		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch doneSignal = new CountDownLatch(N);

		for (int i = 0; i < N; ++i) {
			// create and start threads
			Thread thread = new Thread(() -> {
				try {
					startSignal.await();// 因为此处设置了等待，所以只有startSignal执行了countDown才会往下走
					System.out.println("当前线程=" + Thread.currentThread().getName());
					doneSignal.countDown();
				} catch (InterruptedException ex) {
				}
			});
			thread.setName("线程-" + i);
			thread.start();
		}

		doSomethingElse("don't let run yet");
		startSignal.countDown(); // let all threads proceed
		doSomethingElse("等待子线程执行完");
		doneSignal.await(); // wait for all to finish
		doSomethingElse("等所有执行完，才办轮到我");
	}

	public static void doSomethingElse(String message) {
		System.out.println(message);
	}
}