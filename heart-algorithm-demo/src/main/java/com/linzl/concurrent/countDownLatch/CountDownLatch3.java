package com.linzl.concurrent.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CountDownLatch3 {
	private static int N = 10;

	public static void main(String[] args) throws InterruptedException {
		CountDownLatch doneSignal = new CountDownLatch(N);
		Executor e = Executors.newFixedThreadPool(N);

		for (int i = 0; i < N; ++i) {
			// create and start threads
			e.execute(new WorkerRunnable(doneSignal, i));
		}
		doneSignal.await(); // wait for all to finish
		System.out.println("这里最后执行，等所有线程执行完");
	}
}

class WorkerRunnable implements Runnable {
	private CountDownLatch doneSignal;
	private int i;

	WorkerRunnable(CountDownLatch doneSignal, int i) {
		this.doneSignal = doneSignal;
		this.i = i;
	}

	public void run() {
		doWork(i);
		doneSignal.countDown();
	}

	void doWork(int i) {
		System.out.println(Thread.currentThread().getName() + "==" + i);
	}
}