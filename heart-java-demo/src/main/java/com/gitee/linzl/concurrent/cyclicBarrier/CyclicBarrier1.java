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
public class CyclicBarrier1 {
	final int N;
	final float[][] data;
	final CyclicBarrier barrier;

	public CyclicBarrier1(float[][] matrix) {
		data = matrix;
		N = matrix.length;
		Runnable barrierAction = new Runnable() {
			public void run() {
				System.out.println("Runnable running");
			}
		};
		barrier = new CyclicBarrier(N, barrierAction);

		List<Thread> threads = new ArrayList<Thread>(N);
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

	public static void main(String[] args) {
		new CyclicBarrier1(new float[2][3]);
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
					return;
				} catch (BrokenBarrierException ex) {
					return;
				}
			}
		}
	}
}