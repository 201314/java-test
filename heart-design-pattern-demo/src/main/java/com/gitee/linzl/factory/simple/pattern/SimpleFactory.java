package com.gitee.linzl.factory.simple.pattern;

//写一个简单的工厂类，用于各种Operation 的实例化
public class SimpleFactory {

	public static Operation getInstance(char str) {
		Operation oper = null;

		// Java中的switch只能接收byte,char,int,short 类型
		switch (str) {
		case '+':
			oper = new AddOperation();
			break;
		case '-':
			oper = new SubOperation();
			break;
		// 其他操作的实例化，继续添加case
		}
		return oper;
	}
}
