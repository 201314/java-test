package com.gitee.linzl.sort;

import java.util.Arrays;

/**
 * 
 * 选择排序算法思想：
 * 
 * 在要排序的一组数中，选出最小的一个数与第一个位置的数交换；
 * 
 * 然后在剩下的数当中再找最小的与第二个位置的数交换，
 * 
 * 如此循环到倒数第二个数和最后一个数比较为止。
 */
public class SelectSort {
	public static int[] sort(int[] source) {
		for (int i = 0; i < source.length; i++) {
			for (int j = i + 1; j < source.length; j++) {
				if (source[i] > source[j]) {
					swap(source, i, j);
				}
			}
			System.out.println("第" + (i + 1) + "趟的结果：" + Arrays.toString(source));
		}
		return source;
	}

	// 交换两个数
	public static void swap(int[] source, int i, int j) {
		int temp = source[i];
		source[i] = source[j];
		source[j] = temp;
	}

	public static void main(String[] args) {
		int[] source = { 1, 2, 12, 13, 100, 7, 32, 91, 14, 19, 11, 15 };
		System.out.println("排序前：" + Arrays.toString(source));
		SelectSort.sort(source);
	}
}
