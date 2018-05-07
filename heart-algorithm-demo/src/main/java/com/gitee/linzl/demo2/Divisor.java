package com.gitee.linzl.demo2;

public class Divisor {
	int first, second, temp;

	/**
	 * 最大公约数
	 * 
	 * @param first
	 * @param second
	 */
	public void maxDivisor(int first, int second) {
		if (second % first == 0) {
			System.out.println("最大公约数是" + first);
		} else {
			for (int j = 1; j <= (first / 2); j++) {
				if (first % j == 0 && second % j == 0)
					temp = j;
			}
			System.out.println("最大公约数是" + temp);
		}
	}

	public static void main(String args[]) {
		Divisor max = new Divisor();
		max.maxDivisor(30, 100);// 假设输入的第一个数比第二个数小
	}
}
