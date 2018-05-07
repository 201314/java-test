package com.gitee.linzl.recursion.demo;

import java.util.Scanner;

public class Fibonacci {
	private static int k;

	/**
	 * 使用递归的方法计算菲波那契数列的通项式f(n)，已知f1=1，f2=1，以后每项都是前两项的和
	 * 递归的次数也是呈现出菲波那契数列的形式地f(n)=f(n-1)+f(n-2)
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.print("输入要计算的第N项：");
		Scanner cin = new Scanner(System.in);
		long a = cin.nextLong();
		System.out.println(fibonacci(a));
		System.out.println("共递归调用了" + k + "次");
	}

	public static long fibonacci(long m) {
		if (m == 0 || m == 1) {
			k++;
			return m;
		} else
			return fibonacci(m - 1) + fibonacci(m - 2);
	}
}
