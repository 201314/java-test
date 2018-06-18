package com.gitee.linzl.concurrent.threadPoolExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolTest {

	private ThreadPoolExecutor pool = new ThreadPoolExecutor(1, // 非频繁执行，核心线程数就1个。
			17, // 最大线程数，一般为服务器核心数*2+1
			60, // 线程池中超过核心线程数的线程存活时间
			TimeUnit.MINUTES, // 存活时间单位，秒
			new ArrayBlockingQueue<Runnable>(10), // 10容量的阻塞队列，视具体情况而定
			new MatCapitalApplyTransferThreadFactory(), // 线程工厂
			new MatCapitalApplyTransferExecutionHandler());// 超过最大线程数时，线程池将会把线程交给RejectedExecutionHandler处理

	public void destory() {
		if (pool != null) {
			pool.shutdownNow();
		}
	}

	public ExecutorService getPool() {
		return this.pool;
	}

	private class MatCapitalApplyTransferThreadFactory implements ThreadFactory {
		private AtomicInteger count = new AtomicInteger(0);

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			String threadName = ThreadPoolTest.class.getSimpleName() + count.addAndGet(1);
			System.out.println(threadName);
			t.setName(threadName);
			return t;
		}
	}

	private class MatCapitalApplyTransferExecutionHandler implements RejectedExecutionHandler {
		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			try {
				// 当发生运行线程数超出最大线程数时，线程池将会交给RejectedExecutionHandler处理，
				// 此时我们应该继续将该线程重新放入阻塞队列，从而保证每一个任务都得到处理。
				System.out.println("最大核心线程数：" + executor.getCorePoolSize() + " 最大系统线程数：" + executor.getMaximumPoolSize()
						+ "  当前系统线程数" + executor.getPoolSize());
				executor.getQueue().put(r);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// 测试构造的线程池
	public static void main(String[] args) {
		ThreadPoolTest exec = new ThreadPoolTest();

		ExecutorService pool = exec.getPool();
		for (int i = 1; i < 100; i++) {
			System.out.println("提交第" + i + "个任务!");
			pool.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(">>>task is running=====");
					try {
						TimeUnit.SECONDS.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}

		// 2.销毁----此处不能销毁,因为任务没有提交执行完,如果销毁线程池,任务也就无法执行了
		// 在实战中不能destory，此处是为了程序结束
		exec.destory();
	}
}
