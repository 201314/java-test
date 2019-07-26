package com.gitee.linzl.thread.demo;

/**
 * 有一个抽奖池,该抽奖池中存放了奖励的金额,该抽奖池用一个数组int[] arr =
 * {10,5,20,50,100,200,500,800,2,80,300}; 创建两个抽奖箱(线程)设置线程名称分别为“抽奖箱1”，“抽奖箱2”，
 * 
 * 随机从arr数组中获取奖项元素并打印在控制台上,格式如下:
 * 
 * 抽奖箱1 又产生了一个 10 元大奖
 * 
 * 抽奖箱2 又产生了一个 100 元大奖
 * 
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年3月3日
 */
public class LuckDrawThread {
	int[] arr = { 10, 5, 20, 50, 100, 200, 500, 800, 2, 80, 300 };
	int num = arr.length;
	boolean[] flag = new boolean[num];

	public void print() {
		synchronized (this) {
			int index = (int) (Math.random() * arr.length);
			int get = arr[index];

			if (!flag[index]) {
				// 代表这张抽奖券抽过了
				flag[index] = true;
				System.out.println(Thread.currentThread().getName() + " 又产生了一个" + get + "元大奖");
				num--;
			}
		}
	}

	public static void main(String[] args) {
		LuckDrawThread luck = new LuckDrawThread();

		Thread thread = new Thread(() -> {
			while (true) {
				luck.print();
			}
		}, "抽奖箱1");

		Thread thread2 = new Thread(() -> {
			while (true) {
				luck.print();
			}
		}, "抽奖箱2");

		thread.start();
		thread2.start();
	}
}
