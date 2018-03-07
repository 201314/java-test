package com.linzl.demo2;

/**
 * 假设一个算术表达式中可以包括圆括号"("和")"、方括号"["和"]"，以及花括号
 * "{"和"}"，并且这3种括号可以任意鞋套使用，编写算法测试括号是否匹配 分析：先将表达式中所有
 * 的左括号"("、"["、"{"入栈。如果遇到右括号，则和栈顶元素进行 比较。如果两者能够配对，就将栈顶元素删除;如果不能配对，直接提示表达式中括号不匹配。
 * 在表达式遍历完毕后，如果栈为空，则说明匹配成功，否则匹配失败。
 * 
 * @author Administrator
 */
public class MatchBrackets {
	public static boolean match(String expression) {
		char[] array = expression.toCharArray(); // 获得表达式数组
		// 创建数组作为保存左括号的栈
		char[] stack = new char[array.length];
		int top = 0; // 使用整数记录数组中元素的位置
		for (int i = 0; i < array.length; i++) { // 遍历整个字符数组
			if ((array[i] == '(') || (array[i] == '[') || (array[i] == '{')) {
				stack[top++] = array[i]; // 如果遇到左括号就保存到新数组中
			} else {
				switch (array[i]) {
				case ')': { // 如果遇到右括号，则与栈顶元素比较
					if (stack[--top] != '(') {
						return false;// 如果不匹配直接退出循环
					}
					break;
				}
				case ']': {
					if (stack[--top] != '[') {
						return false;
					}
					break;
				}
				case '}': {
					if (stack[--top] != '{') {
						return false;
					}
					break;
				}
				}
			}
		}
		if (top != 0) { // 如果栈非空则返回false
			return false;
		} else {
			return true;
		}
	}

	public static void main(String[] args) {
		String expression = "(2+3)*{[(5-2)*6+5]*2}";
		System.out.println("表达式成功匹配：" + match(expression));
	}
}
