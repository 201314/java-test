package com.gitee.linzl.concurrent.executors;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletionServiceTest {

	public static void main(String[] args) {
		ExecutorService pool = Executors.newFixedThreadPool(8);
		CompletionService<Boolean> cs = new ExecutorCompletionService<Boolean>(pool);
		Callable<Boolean> task = new Callable<Boolean>() {
			public Boolean call() {
				try {
					Thread.sleep(1000);
					System.out.println("插入1000条数据完成");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return true;
			};
		};
		System.out.println(new SimpleDateFormat("mm:ss").format(new Date()) + "--开始插入数据");
		for (int i = 0; i < 10; i++) {
			cs.submit(task);
		}
		for (int i = 0; i < 10; i++) {
			try {
				// ExecutorCompletionService.take()方法是阻塞的，如果当前没有完成的任务则阻塞
				System.out.println(cs.take().get());
				// 实际使用时，take()方法获取的结果可用于处理，如果插入失败，则可以进行重试或记录等操作
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		System.out.println(new SimpleDateFormat("mm:ss").format(new Date()) + "--插入数据完成");
		pool.shutdown();
	}
}