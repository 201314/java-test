package com.gitee.linzl.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * 直接使用Executors
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年5月16日
 */
public class ThreadPoolExecutorDemo {

	public static void testArrayBlockingQueue() {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 20, 60, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(5));
		System.out.println("有界队列:ArrayBlockingQueue");
		for (int i = 0; i < 20; i++) {
			final int index = i;
			executor.submit(() -> {
				System.out.println("乱序执行:" + index);
			});
		}
	}

	public static void testDelayQueue() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
		System.out.println("延迟队列线程池");
		for (int i = 0; i < 5; i++) {
			final int index = i;
			executor.schedule(() -> {
				System.out.println("延迟顺序执行:" + index + ",时间：" + simpleDateFormat.format(new Date()));
			}, 10, TimeUnit.SECONDS);
		}
		System.out.println("end main时间:" + simpleDateFormat.format(new Date()));
	}

	public static void testLinkedBlockingDeque() {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 20, 60, TimeUnit.SECONDS,
				new LinkedBlockingDeque<Runnable>());
		System.out.println("双向队列:LinkedBlockingDeque");
		System.out.println("是一种具有队列和栈的性质的数据结构");
		for (int i = 0; i < 20; i++) {
			final int index = i;
			executor.submit(() -> {
				System.out.println("顺序执行:" + index);
			});
		}
	}

	public static void testLinkedBlockingQueue() {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 20, 60, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
		System.out.println("无界队列:LinkedBlockingQueue");
		for (int i = 0; i < 20; i++) {
			final int index = i;
			executor.submit(() -> {
				System.out.println("顺序执行:" + index);
			});
		}
	}

	@Test
	public void testLinkedTransferQueue() {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 20, 60, TimeUnit.SECONDS,
				new LinkedTransferQueue<Runnable>());
		System.out.println("无界队列:LinkedTransferQueue");
		for (int i = 0; i < 20; i++) {
			final int index = i;
			executor.submit(() -> {
				System.out.println("顺序执行:" + index);
			});
		}
	}

}
