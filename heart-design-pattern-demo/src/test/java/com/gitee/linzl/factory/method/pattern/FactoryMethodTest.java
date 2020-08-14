package com.gitee.linzl.factory.method.pattern;

public class FactoryMethodTest {
	public static void main(String[] args) {
		// 每个操作 都有相对就的工厂类
		IFactory factory = new AddFactory();
		Operation oper = factory.createOperation();

		oper.setFirstNum(100);
		oper.setSecondNum(214);
		System.out.println("两数之和为：" + oper.caculateResult());

		/**
		 * 如果使用 简单工厂模式 如下，每增加一个新的运算操作， 就得修改 简单工厂类 中的switch，违反了开放-封闭原则
		 * 原则：不修改已有的类和方法，而是通过增加新的类来实现新的操作
		 */
		// 实现加法
		/*
		 * Operation add=SimpleFactory.getInstance('+'); add.setFirstNum(10.36);
		 * add.setSecondNum(42.12); System.out.println("两数之和为："+add.caculateResult());
		 */
	}
}
