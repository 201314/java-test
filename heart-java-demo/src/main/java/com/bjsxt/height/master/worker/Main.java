package com.bjsxt.height.master.worker;

public class Main {
	// 例如求 1^2+2^2+3^2……+100^2
	public static void main(String[] args) {
		System.out.println("我的机器可用Processor数量:" + Runtime.getRuntime().availableProcessors());
		Master master = new Master(new MyWorker(), Runtime.getRuntime().availableProcessors());

		for (int i = 1; i <= 100; i++) {
			Task t = new Task();
			t.setId(i);
			t.setName("任务" + i);
			t.setPrice(i);
			master.submit(t);
		}
		master.execute();
		long start = System.currentTimeMillis();

		while (true) {
			if (master.isComplete()) {
				long end = System.currentTimeMillis() - start;
				int priceResult = master.getResult();
				System.out.println("最终结果：" + priceResult + ", 执行时间：" + end);
				break;
			}
		}
	}
}
