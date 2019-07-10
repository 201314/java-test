package com.gitee.linzl.number;

import org.junit.jupiter.api.Test;

public class NumberTest {
	@Test
	public void maxDivisor() {
		// 假设输入的第一个数比第二个数小
		NumberOperation.maxDivisor(30, 100);
	}

	@Test
	public void getBinary() {
		System.out.println("64的二进制表示：" + NumberOperation.getBinary(64));
	}

	@Test
	public void count() {
		int a = 11;
		System.out.println("11的二进制有多少个1:" + NumberOperation.getCount(a));
	}

	@Test
	public void fibonacci() {
		System.out.println("菲波那契数列的通项式f(n):" + NumberOperation.fibonacci(10));
	}

	@Test
	public void maxChildSequence() {
		int a[] = { 12, -19, 5, 3, -6, 11, 10, -20 };
		// int b[] = { -2, 11, -4, 13, -5, -2 };

		System.out.println("\n最大子序列和：" + NumberOperation.findMaxSequence(a));// 最大的子序列
	}

	@Test
	public void findRepeatNum() {
		int[] a = new int[1001];
		for (int index = 0; index < 1000; index++) {
			a[index] = index + 1;
		}
		a[1000] = 99;

		System.out.println("重复数字为:" + NumberOperation.findRepeatNum(a));
		System.out.println("重复数字(第二种解法)为:" + NumberOperation.findRepeatNum2(a));
	}

	@Test
	public void printPrimeNum() {
		NumberOperation.printPrimeNum(100);
		System.out.println();
		NumberOperation.printPrimeNum2(100);
	}

}
