package com.gitee.linzl.concurrent.callable;

import java.util.concurrent.*;

public class CallableDemo implements Callable<String> {
	/**
	 * 这里是真实的业务逻辑，其执行可能很慢
	 */
	@Override
	public String call() throws Exception {
		// 模拟执行耗时
		Thread.sleep(3000);
		String result = Math.random() + "处理完成";
		System.out.println("result ---》" + result);
		return result;
	}

	public static String run2() throws Exception{
// 构造FutureTask，并且传入需要真正进行业务逻辑处理的类,该类一定是实现了Callable接口的类
		FutureTask<String> future = new FutureTask<>(new CallableDemo());
		// 创建一个固定线程的线程池且线程数为1,
		ExecutorService executor = Executors.newFixedThreadPool(1);
		// 这里提交任务future,则开启线程执行RealData的call()方法执行
		executor.submit(future);
		// 调用获取数据方法,如果call()方法没有执行完成,则依然会进行等待
		//executor.shutdown();
		return future.get(1,TimeUnit.SECONDS);
	}

	// 主控制函数
	public static void main(String[] args)  throws Exception{
		String str = run2();
		if (str!=null){
			System.out.println("不为空");
		}else {
			System.out.println("pw");
		}
	}
}
