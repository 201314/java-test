package com.gitee.linzl.stack;

/**
 * 两栈 共享空间
 * 
 * @author GDCC i心灵鸡汤you email:2225010489@qq.com 2013-4-20 下午10:54:31
 */
public class ShareStack {
	private static int length = 100;
	int stack[] = new int[length];
	static int firstIndex = -1; // 初始化第一个栈 为空
	static int secondIndex = length; // 初始化第二个栈为空

	// 判断第一个栈是否为空
	public boolean ifFirstEmpty() {
		return firstIndex == -1 ? true : false;
	}

	// 判断第二个栈是否为空
	public boolean ifSecondEmpty() {
		return secondIndex == length ? true : false;
	}

	// 判断 栈空间 是否已满
	public boolean ifFull() {
		return firstIndex + 1 == secondIndex ? true : false;
	}

	// 进栈
	public void push(int number, int whichOne) {
		// 先判断栈 是否已满
		if (ifFull()) {
			System.out.println("栈满");
		} else {
			if (whichOne == 1) {// 第一个栈
				++firstIndex;
				stack[firstIndex] = number;
			} else if (whichOne == 2) { // 第二个栈
				--secondIndex;
				stack[secondIndex] = number;
			}
		}
	}

	// 出栈
	public void pop(int whichOne) {
		int number = 0;// 出栈元素
		if (whichOne == 1) {
			if (ifFirstEmpty()) {
				System.out.println("栈1为空栈");
			} else {
				number = stack[firstIndex];
				firstIndex--;
			}
		} else if (whichOne == 2) {
			if (ifSecondEmpty()) {
				System.out.println("栈2为空栈");
			} else {
				number = stack[secondIndex];
				secondIndex++;
			}
		}
	}

}
