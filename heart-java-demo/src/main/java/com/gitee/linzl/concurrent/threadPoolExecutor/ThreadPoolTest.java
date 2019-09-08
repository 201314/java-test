package com.gitee.linzl.concurrent.threadPoolExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolTest {
	/**
	 * 在使用有界队列时，若有新的任务需要执行，如果线程池实际线程数小于corePoolSize，则优先创建线程，
	 * 若大于corePoolSize则会将任务加入队列，
	 * 
	 * 若队列已满，则在总线程数不大于maximumPoolSize的前提下，创建新的线程，
	 * 若线程数大于maximumPoolSize，则执行拒绝策略或其他自定义方式。
	 */
	private TimingThreadPool pool = new TimingThreadPool(
			1, // 非频繁执行，核心线程数就1个。
			3, // 最大线程数，一般为服务器核心数*2+1
			60, // 线程池中超过核心线程数的线程存活时间
			TimeUnit.MINUTES, // 存活时间单位，秒
			new ArrayBlockingQueue<Runnable>(4), // 4容量的阻塞队列，视具体情况而定
			new DiyThreadFactory(), // 线程工厂,统一设置参数
			new DiyRejectedExecutionHandler());// 超过最大线程数时，线程池将会把线程交给RejectedExecutionHandler处理

	public void destory() {
		if (pool != null) {
			pool.shutdownNow();
		}
	}

	public ExecutorService getPool() {
		return this.pool;
	}

	private class DiyThreadFactory implements ThreadFactory {
		private AtomicInteger count = new AtomicInteger(0);

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			String threadName = ThreadPoolTest.class.getSimpleName() + count.addAndGet(1);
			t.setName(threadName);
			return t;
		}
	}

	private class DiyRejectedExecutionHandler implements RejectedExecutionHandler {
		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			try {
				// 当发生运行线程数超出最大线程数时，线程池将会交给RejectedExecutionHandler处理，
				// 此时我们应该继续将该线程重新放入阻塞队列，从而保证每一个任务都得到处理。
				System.out.println("当前线程:" + r.toString() + ",最大核心线程数：" + executor.getCorePoolSize() + ",最大系统线程数："
						+ executor.getMaximumPoolSize() + ",当前系统线程数" + executor.getPoolSize());
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
			final int taskId = i;
			pool.execute(() -> {
				System.out.println("第" + taskId + "个task is running=====");
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}

		// 2.销毁----此处不能销毁,因为任务没有提交执行完,如果销毁线程池,任务也就无法执行了
		// 在实战中不能destory，此处是为了程序结束
		exec.destory();
	}
}
