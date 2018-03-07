package com.linzl.thread.threadPoolExecutor;

public class Go {
	static {
		System.out.println("Go只执行一次");
	}

	{
		System.out.println("Go我是非静态代码块");
	}

	public Go() {
		System.out.println("Go构造函数");
	}

	public Go(String name) {
		System.out.println("Go构造函数" + name);
	}

	public static void main(String[] args) {
		new Go();
		new Go();
	}
}
