package com.linzl.demo;

/**
 * @description 输出某数的二进制有多少个1
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2017年11月26日
 */
public class CountNumberOfOne {
	public static int getCount(int num) {
		if (num == 1) {
			return 1;
		}
		if (num == 0) {
			return 0;
		}
		return num % 2 + getCount(num / 2);
	}

	public static void main(String[] args) {
		int a = 11;
		int num = getCount(a);
		System.out.println(num);
	}

}
