package com.gitee.linzl.concurrent.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * Executors,并行框架特别合适递归操作
 * 
 * @description
 * 
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年5月16日
 */
public class RecursiveActionDemo extends RecursiveAction {
	private static final long serialVersionUID = 4742479835250263278L;
	private int start;
	private int end;
	private int count = 20;

	public RecursiveActionDemo(int start, int end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * 大任务是：打印0-100的数值。
	 * 
	 * 小任务是：每次只能打印20个数值。
	 */
	@Override
	protected void compute() {
		if (end - start < count) {
			for (int i = start; i < end; i++) {
				System.out.println(Thread.currentThread().getName() + "的i值:" + i);
			}
		} else {
			System.err.println("=====任务分解======");
			// 将大任务分解成两个小任务
			int middle = (start + end) / 2;
			RecursiveActionDemo first = new RecursiveActionDemo(start, middle);
			RecursiveActionDemo second = new RecursiveActionDemo(middle, end);
			// 并行执行两个小任务
			first.fork();
			second.fork();
		}
	}

	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool();
		pool.submit(new RecursiveActionDemo(0, 100));
		try {
			// 线程阻塞2秒，等待所有任务完成
			pool.awaitTermination(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pool.shutdown();
	}
}
