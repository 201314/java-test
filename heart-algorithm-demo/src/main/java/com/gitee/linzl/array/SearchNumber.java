package com.gitee.linzl.array;

/**
 * @description 找出重复数 题目：1 ~
 *              1000放在含有1001个元素的数组中，只有唯一的一个元素值重复，其它均只出现一次。每个数组元素只能访问一次，设计一个算法，将它找出来，不用辅助存储空间，能否设计一个算法实现？
 *              姑且令该数组为int[] a
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年1月6日
 */
public class SearchNumber {
	/**
	 * 解法1：数组累和 - （1+2+3+...+.. + 999 + 1000）= 所求结果
	 */
	public int findFirst(int[] a) {
		int t = 1000 * (1000 + 1) / 2; // 1 ~ 1000的累和
		int sum = 0;
		for (int i = 0; i < a.length; i++) {
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
	public int findSecond(int[] a) {
		int t1 = 0;
		int t2 = 0;
		for (int i = 0; i < a.length; i++) {// 包含重复数
			t1 ^= a[i];
		}
		for (int i = 1; i <= 1000; i++) {
			t2 ^= i;
		}
		return (t1 ^ t2);
	}

}
