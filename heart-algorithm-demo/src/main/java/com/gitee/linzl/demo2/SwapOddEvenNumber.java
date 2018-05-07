package com.gitee.linzl.demo2;

import java.util.Arrays;

/**
 * 已知数组array为整形数组，将其分成左右两部分。编写算法使左边所有的元素为奇数，右边所有的元素为偶数。
 * 
 * 分析：可以从数组的左右两端不断地向中间比较，如果左端遇到偶数，右端遇到奇数，则将两者进行交接。该算法的时间复杂度为O(n)
 * 
 * @author Administrator
 */
public class SwapOddEvenNumber {
	public static int[] adjust(int[] array) {
		int i = 0;
		int j = array.length - 1;
		int temp = 0;
		while (i < j) {// 从左右两边同时遍历数组
			while (array[i] % 2 != 0) {// 如果左边遇到奇数则继续遍历
				i++;
			}

			while (array[j] % 2 == 0) {// 如果右边遇到偶数则继续遍历
				j--;
			}

			if (i < j) {// 交换数组的元素
				temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}
		return array;
	}

	public static void main(String[] args) {
		int[] array = { 1, 3, 6, 8, 9, 10, 14, 15, 18, 60, 85, 99 };
		System.out.println("程序运行前：" + Arrays.toString(array));
		System.out.println("程序运行后：" + Arrays.toString(adjust(array)));
	}
}
