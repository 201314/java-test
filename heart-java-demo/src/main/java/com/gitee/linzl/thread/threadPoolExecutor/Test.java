package com.gitee.linzl.thread.threadPoolExecutor;

public class Test {
	public static void main(String[] args) {
		SingleTon singleTon = SingleTon.getInstance();
		System.out.println(singleTon.count1 + "=?=" + singleTon.count2);
	}
}

class SingleTon {
	private static SingleTon singleTon = new SingleTon();
	public int count1;
	public int count2 = 0;

	private SingleTon() {
		count1++;
		count2++;
	}

	public static SingleTon getInstance() {
		return singleTon;
	}
}
