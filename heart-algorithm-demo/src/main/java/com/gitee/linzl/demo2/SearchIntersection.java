package com.gitee.linzl.demo2;

import java.util.Arrays;

/**
 * 对于两个整形数组，编写算法实现交集运算。 分析：就是将两个数组中的公共元素复制到第三个数组中。可以从头遍历第一个数组，
 * 如果在第二个数组中遇到了相同的元素，将其复制到第三个数组中即可。
 * 
 * @author Administrator
 */
public class SearchIntersection {
	public static int[] join(int[] array1, int[] array2) {
		int array1Length = array1.length;
		int array2Length = array2.length;
		// 定义新数组
		int[] array = new int[Math.max(array1Length, array2Length)];
		int size = 0;
		for (int i = 0; i < array1Length; i++) {
			for (int j = 0; j < array2Length; j++) {
				if (array1[i] == array2[j]) {
					array[size++] = array1[i];// 如果遇到相同的元素则保存
					break;
				}
			}
		}
		int[] newArray = new int[size];
		System.arraycopy(array, 0, newArray, 0, size);// 复制元素
		return newArray;
	}

	public static void main(String[] args) {
		int[] array1 = { 1, 2, 4, 5, 9, 12 };
		int[] array2 = { 2, 6, 9, 11, 16, 12 };
		System.out.println("第一个数组：" + Arrays.toString(array1));
		System.out.println("第二个数组：" + Arrays.toString(array2));
		System.out.println("两个数组的交集：" + Arrays.toString(join(array1, array2)));
	}
}
