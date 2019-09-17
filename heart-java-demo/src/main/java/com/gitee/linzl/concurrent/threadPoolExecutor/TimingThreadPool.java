package com.gitee.linzl.concurrent.threadPoolExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 * 增加日志和计时功能的线程池
 * 
 * 实际应用中对ThreadPoolExecutor进行扩展，可以实现对线程池运行状态的跟踪，输出一些有用的调试，帮助系统故障诊断
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年6月17日
 */
public class TimingThreadPool extends ThreadPoolExecutor {
	private ThreadLocal<Long> startTime = new ThreadLocal<>();
	private final Logger log = Logger.getLogger("TimingThreadPool");
	private final AtomicLong numTasks = new AtomicLong();
	private final AtomicLong totalTime = new AtomicLong();

	public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
	}

	public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
	}

	public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
	}

	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		log.fine(String.format("Thread %s: start %s", t, r));
		// nanTime是计时器，纳秒; currentTime是时钟
		startTime.set(System.nanoTime());
	}

	protected void afterExecute(Runnable r, Throwable t) {
		try {
			long endTime = System.nanoTime();
			long taskTime = endTime - startTime.get();
			numTasks.incrementAndGet();
			totalTime.addAndGet(taskTime);
			log.fine(String.format("Thread %s:end %s,time=%dns", t, r, taskTime));
		} finally {
			super.afterExecute(r, t);
		}
	}

	protected void terminated() {
		try {
			log.fine(String.format("Terminated:avg time=%dns", totalTime.get() / numTasks.get()));
		} finally {
			super.terminated();
		}
	}
}
