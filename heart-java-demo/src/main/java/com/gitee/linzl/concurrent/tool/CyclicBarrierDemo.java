package com.gitee.linzl.concurrent.tool;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier可以重复使用已经通过的障碍
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年3月3日
 */
public class CyclicBarrierDemo {

	static class Runner implements Runnable {
		private CyclicBarrier barrier;
		private String name;

		public Runner(CyclicBarrier barrier, String name) {
			this.barrier = barrier;
			this.name = name;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(1000 * (new Random()).nextInt(5));
				System.out.println(name + " 准备OK.");
				barrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
			System.out.println(name + " Go!!");
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		CyclicBarrier barrier = new CyclicBarrier(3); // 3
		ExecutorService executor = Executors.newFixedThreadPool(3);

		executor.submit(new Thread(new Runner(barrier, "zhangsan")));
		executor.submit(new Thread(new Runner(barrier, "lisi")));
		executor.submit(new Thread(new Runner(barrier, "wangwu")));

		executor.shutdown();
	}
}