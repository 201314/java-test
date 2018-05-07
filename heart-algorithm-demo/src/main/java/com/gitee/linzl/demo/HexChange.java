package com.gitee.linzl.demo;

import java.util.Stack;

/**
 * 设计一个算法，将一个十进制整数转换成二进制输出
 * 
 * 分析:对于任意整数，可以将其分成两个整数之和。第一个整数是能够被除数整除的部分，
 * 第二个整数是余数，即number=(number/divisor)*divisor+(number mod divisor);
 * 先得到的余数是低位的，需要后输出;后得到的余数是高位的，需要先输出。这正好符合了栈结构。
 * 
 * @author Administrator
 */
public class HexChange {
	public static String getBinary(int decimal) {
		Stack<Integer> stack = new Stack<Integer>(); // 创建栈对象
		while (decimal != 0) {
			stack.push(decimal % 2); // 向栈中增加余数
			decimal /= 2; // 获得新商
		}

		// 创建StringBuilder对象
		StringBuilder result = new StringBuilder();
		while (!stack.isEmpty()) {
			result.append(stack.pop()); // 保存栈中的元素到字符串中
		}

		return result.toString();
	}

	public static void main(String[] args) {
		System.out.println("64的二进制表示：" + getBinary(64));
	}
}
