package com.gitee.linzl.number;

import java.util.Stack;

public class NumberOperation {
	/**
	 * 最大公约数
	 * 
	 * @param first
	 * @param second
	 */
	public static int maxDivisor(int first, int second) {
		if (second % first == 0) {
			System.out.println("最大公约数是" + first);
			return first;
		}

		int temp = 0;
		for (int j = 1, tmp = (first / 2); j <= tmp; j++) {
			if (first % j == 0 && second % j == 0) {
				temp = j;
			}
		}
		System.out.println("最大公约数是" + temp);
		return temp;
	}

	/**
	 * 设计一个算法，将一个十进制整数转换成二进制输出
	 * 
	 * 分析:对于任意整数，可以将其分成两个整数之和。第一个整数是能够被除数整除的部分，
	 * 第二个整数是余数，即number=(number/divisor)*divisor+(number mod divisor);
	 * 先得到的余数是低位的，需要后输出;后得到的余数是高位的，需要先输出。这正好符合了栈结构。
	 */
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

	/**
	 * 输出某数的二进制有多少个1
	 * 
	 * @param num
	 * @return
	 */
	public static int getCount(int num) {
		if (num == 1) {
			return 1;
		}
		if (num == 0) {
			return 0;
		}
		return num % 2 + getCount(num / 2);
	}

	/**
	 * 使用递归的方法计算菲波那契数列的通项式f(n)，已知f1=1，f2=1，以后每项都是前两项的和
	 * 递归的次数也是呈现出菲波那契数列的形式地f(n)=f(n-1)+f(n-2)
	 * 
	 * @param args
	 */
	public static long fibonacci(long m) {
		if (m == 0 || m == 1) {
			return m;
		}
		return fibonacci(m - 1) + fibonacci(m - 2);
	}

	/**
	 * 找出最大子序列,不考虑全是负数的情况,
	 * 
	 * int a[] = { 12, -19, 5, 3, -6, 11, 10, -20 };
	 * 
	 * 如果都为负数时呢？？？
	 */
	public static int findMaxSequence(int a[]) {
		boolean startPositive = true;
		int curSum = 0;
		int positiveIndex = -1;

		int preSum = 0;
		int start = -1;// 记录当前最大子序列的起始位置和终点位置
		int end = -1;

		for (int i = 0, length = a.length; i < length; i++) {
			if (startPositive && a[i] > 0) {// 记录第一个不为负数的位置,只有一开始不为负数，后面的数相加才会尽可能大
				positiveIndex = i;
				startPositive = false;
			}

			curSum += a[i];
			if (curSum > preSum) {// 和上次之和比较，大则赋值位置信息
				preSum = curSum;
				start = positiveIndex;
				end = i;
			} else if (curSum <= 0) {// 和为负数不考虑，reset重置
				curSum = 0;
				startPositive = true;
			}
		}
		System.out.println("最大子序列距离 起始位置start:" + start + "终点位置长度end：" + end);
		return preSum;

	}

	/**
	 * 找出重复数
	 * 
	 * 1~1000放在含有1001个元素的数组中，只有唯一的一个元素值重复，其它均只出现一次。
	 * 
	 * 每个数组元素只能访问一次，设计一个算法，将它找出来，不用辅助存储空间，能否设计一个算法实现
	 * 
	 * @param a
	 * @return
	 */
	/**
	 * 解法1：数组累和 - (1+2+3+...+.. + 999 + 1000)= 所求结果
	 */
	public static int findRepeatNum(int[] a) {
		int t = 1000 * (1000 + 1) / 2; // 1 ~ 1000的累和
		int sum = 0;
		for (int i = 0, length = a.length; i < length; i++) {
			sum += a[i];
		}
		return (sum - t);
	}

	/**
	 * 1^2^...^1000（序列中不包含n）的结果为T
	 * 
	 * 则1^2^...^1000（序列中包含n）的结果就是T^n。 T^(T^n)=n。
	 * 
	 * @param a
	 * @return
	 */
	public static int findRepeatNum2(int[] a) {
		int t1 = 0;
		int t2 = 0;
		for (int i = 0, length = a.length; i < length; i++) {// 包含重复数
			t1 ^= a[i];
		}
		for (int i = 1; i <= 1000; i++) {
			t2 ^= i;
		}
		return (t1 ^ t2);
	}

	/**
	 * 求出100以内的所有素数/质数
	 */
	public static void printPrimeNum(int num) {
		int j = 0, k = 0;

		for (int i = 2; i <= num; i++) {
			k = (int) Math.sqrt(i);// 开方

			for (j = 2; j <= k; j++) {
				if (i % j == 0) {
					break;
				}
			}

			if (j > k) {
				System.out.print(i + " ");
			}
		}
	}

	/**
	 * 求出100以内的所有素数/质数
	 */
	public static void printPrimeNum2(int num) {
		int a[] = new int[num + 1];

		for (int i = 1; i <= num; i++) {
			a[i] = 1;
		}
		/**
		 * 根据筛选法求出100以内的所有素数，所谓筛选法是指从小到大筛去一个已知素数的所有倍数，
		 * 例如:根据2我们可筛去4，6，8，……98，100等数，然后根据3可筛去9，15，……，99等数
		 * （注意此时6，12等数早就被筛去了），由于4被筛去了，下一个用于筛选的素数是5……依次类推， 最后剩余的就是100以内的素数
		 */
		for (int i = 2; i <= num; i++) {
			if (a[i] != 0) {
				continue;
			}
			for (int j = i + 1; j <= num; j++) {
				if (j % i == 0) {
					a[j] = 0;
				}
			}
		}

		for (int i = 2; i <= num; i++) {
			if (a[i] != 0) {
				System.out.print(i + " ");
			}
		}
	}
}
