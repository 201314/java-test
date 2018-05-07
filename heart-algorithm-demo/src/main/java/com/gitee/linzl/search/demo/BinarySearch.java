package com.gitee.linzl.search.demo;

import java.util.Arrays;

/**
 * 分析：折半查找算法要求元素有序（通常是升序）并使用顺序存储。 其核心思想是:将查找区域分成两部分，每次与中间码进行比较，如果等于则直接返回;
 * 如果大于则查找右半部分;如果小于则查找左半部分。依次重复这个过程，直到查找成功或失败。
 * 
 * @param args
 *            折半查找
 */
public class BinarySearch {
	public static int findNum(int a[], int key) {
		int low = 0;
		int high = a.length - 1;
		while (low <= high) {
			int mid = (low + high) / 2;
			if (key > a[mid])
				low = mid++; // 后半部分，下标最小为mid++
			else if (mid < a[mid])
				high = mid--;// 后半部分，下标最大为mid--
			else
				return mid;
		}
		return -1;// Not found
	}

	public static void main(String[] args) {
		int a[] = { 12, -19, 5, 3, -6, 11, 10, -20 };
		Arrays.sort(a);// 使用二分法查找，必须按升序或者降序先排序
		System.out.println(Arrays.toString(a));
		System.out.println("要查找的数所在位置：" + findNum(a, 3));// 找出给定的值所在位置
	}

}