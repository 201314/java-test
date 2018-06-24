package com.gitee.linzl.concurrent.cyclicBarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier可以重复使用已经通过的障碍
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年3月3日
 */
public class CyclicBarrierDemo {
	final int N;
	final float[][] data;
	final CyclicBarrier barrier;

	public CyclicBarrierDemo(float[][] matrix) {
		data = matrix;
		N = matrix.length;
		barrier = new CyclicBarrier(N);
	}

	public void testRun() {
		List<Thread> threads = new ArrayList<>(N);
		for (int i = 0; i < N; i++) {
			Thread thread = new Thread(new Worker(i));
			thread.setName("Thread-Name-" + i);
			threads.add(thread);
			thread.start();
		}

		// wait until done
		try {
			for (Thread thread : threads) {
				thread.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	class Worker implements Runnable {
		int myRow;

		Worker(int row) {
			myRow = row;
		}

		public void run() {
			while (true) {
				System.out.println(Thread.currentThread().getName() + "working=" + myRow);
				try {
					barrier.await();
				} catch (InterruptedException ex) {
				} catch (BrokenBarrierException ex) {
				}
			}
		}
	}

	public static void main(String[] args) {
		CyclicBarrierDemo demo = new CyclicBarrierDemo(new float[2][3]);
		demo.testRun();
	}
}