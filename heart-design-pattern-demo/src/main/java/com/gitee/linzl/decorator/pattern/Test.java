package com.gitee.linzl.decorator.pattern;

public class Test {
	public static void main(String[] args) {
		Person monkey = new Person();

		// 装饰过程
		Decoration dress = new DressDecoration();
		dress.decorate(monkey); // 打扮

		Decoration head = new HeadDecoration();
		head.decorate(dress); // 打扮

		head.dressUp();
	}
}
