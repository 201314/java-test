package com.gitee.linzl.concurrent.locks;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class ProduceAndConsumer {
	private int Max = 10;
	private Object lock = new Object();
	private Queue<String> buffer = new LinkedList<>();

	class Producer implements Runnable {
		public void run() {
			while (true) {
				synchronized (lock) {
					if (buffer.size() == Max) {// 最多只能生产10个
						System.out.println("等待消费");
						try {
							lock.wait();// 队列放不下，等待消费掉
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println("生产者开始:" + Thread.currentThread().getName());
					buffer.offer(UUID.randomUUID().toString());
					lock.notify();
				}
			}
		}
	}

	class Consumer implements Runnable {
		public void run() {
			try {
				while (true) {
					synchronized (lock) {
						if (buffer.isEmpty()) {
							System.out.println("等待生产");
							lock.wait();// 没有可以消费的，要等待
						}
						System.out.println("消费者" + Thread.currentThread().getName() + "开始:大小" + buffer.size() + ",取出"
								+ buffer.poll());
						lock.notify();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		ProduceAndConsumer test = new ProduceAndConsumer();
		ProduceAndConsumer.Producer producer = test.new Producer();
		ProduceAndConsumer.Consumer consumer = test.new Consumer();

		for (int i = 0; i < 10; i++) {
			new Thread(consumer).start();
			new Thread(producer).start();
		}
	}
}
