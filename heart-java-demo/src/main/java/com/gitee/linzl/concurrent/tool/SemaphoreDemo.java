package com.gitee.linzl.concurrent.tool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年6月26日
 */
public class SemaphoreDemo {
	private static final int NUMBER = 5; // 限制资源访问数
	private static final Semaphore avialable = new Semaphore(NUMBER, true);

	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		Runnable r = () -> {
			try {
				avialable.acquire(); // 此方法阻塞,直到获取许可
				Thread.sleep(10 * 1000);
				System.out.println(new SimpleDateFormat("mm:ss").format(new Date()) + "--"
						+ Thread.currentThread().getName() + "--执行完毕");
				avialable.release();// 访问完后，释放
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};

		System.out.println("前：" + avialable.availablePermits());
		for (int i = 0; i < 10; i++) {
			pool.execute(r);
		}
		System.out.println("后：" + avialable.availablePermits());
		pool.shutdown();
	}
}