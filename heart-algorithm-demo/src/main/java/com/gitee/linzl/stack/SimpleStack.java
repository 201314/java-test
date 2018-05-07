package com.gitee.linzl.stack;

/**
 * 栈 特点：先进后出，线性顺序结构
 * 
 * @author GDCC i心灵鸡汤you email:2225010489@qq.com 2013-4-20 下午09:41:38
 */
public class SimpleStack {

	private int length = 100;
	int stack[] = new int[length];
	static int index = -1;// 初始化栈 为空

	// 判断栈是否满
	public boolean ifFull() {
		return index == (length - 1) ? true : false;
	}

	// 判断栈是否为空
	public boolean ifEmpty() {
		return index == -1 ? true : false;
	}

	// 进栈
	public void push(int number) {
		index++;
		stack[index] = number;
	}

	// 出栈
	public void pop() {
		index--;
	}

	public static void main(String[] args) {

	}
}
