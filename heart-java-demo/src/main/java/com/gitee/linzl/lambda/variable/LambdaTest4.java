package com.gitee.linzl.lambda.variable;

public class LambdaTest4 {
	public void doWork1() {
		Runnable runnable = () -> {
			System.out.println(this.toString());
			System.out.println("lambda express run...");
		};
		new Thread(runnable).start();
	}

	public void doWork2() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println(this.toString());
				System.out.println("anony function run...");
			}
		};
		new Thread(runnable).start();
	}

	public static void main(String[] args) {
		new LambdaTest4().doWork1();
		new LambdaTest4().doWork2();
	}
}