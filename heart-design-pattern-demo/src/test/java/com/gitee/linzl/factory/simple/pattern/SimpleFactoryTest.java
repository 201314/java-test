package com.gitee.linzl.factory.simple.pattern;

/**
 * 
 * 将各种运算分开成为一个类，是为了实现易扩展，便于修改
 * 
 * @author GDCC i心灵鸡汤you 2225010489@qq.com
 */
public class SimpleFactoryTest {
	public static void main(String[] args) {

		// 实现加法
		Operation add = SimpleFactory.getInstance('+');
		add.setFirstNum(10.36);
		add.setSecondNum(42.12);
		System.out.println("两数之和为：" + add.caculateResult());

		// 实现减法
		Operation sub = SimpleFactory.getInstance('-');
		sub.setFirstNum(10);
		sub.setSecondNum(2);
		System.out.println("两数之差为：" + sub.caculateResult());

		// 实现乘法、除法、开方、乘法……
		/**
		 * 步骤：1、写一个乘法类实现抽象类Operation 步骤：2、在SimpleFactory switch中添加case操作
		 */

	}
}
