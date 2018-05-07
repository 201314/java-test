package com.gitee.linzl.thread.threadPoolExecutor;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {

	public static void main(String[] args) {
		final Vector<Integer> vector = new Vector<Integer>();

		// corePoolSize,maximumPoolSize,keepAliveTime,TimeUnit,BlockingQueue
		ThreadPoolExecutor tp = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(10));

		final Random random = new Random();
		System.out.println(tp.getPoolSize());

		for (int i = 0; i < 20; i++) {
			tp.execute(new Runnable() {
				public void run() {
					vector.add(random.nextInt());
				}
			});
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		tp.shutdown();
		System.out.println("PoolSize：" + tp.getPoolSize());
		System.out.println("已完成的任务：" + tp.getCompletedTaskCount());
		System.out.println("活动的线程数：" + tp.getActiveCount());
		System.out.println("list大小：" + vector.size());
	}
}
