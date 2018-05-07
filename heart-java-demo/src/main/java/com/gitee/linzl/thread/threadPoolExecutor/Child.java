package com.gitee.linzl.thread.threadPoolExecutor;

public class Child extends Go {
	static {
		System.out.println("child只执行一次");
	}
	{
		System.out.println("child我是非静态代码块");
	}

	public Child() {
		System.out.println("child构造函数");
	}

	public Child(String name) {
		System.out.println("child构造函数" + name);
	}

	public static void main(String[] args) {
		new Child("我是孩子");
		new Go();
	}
}
