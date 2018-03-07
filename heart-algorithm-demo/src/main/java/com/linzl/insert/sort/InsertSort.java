package com.linzl.insert.sort;

import java.util.Arrays;

/**
 * 插入算法思想：
 * 
 * （1）将整个数组分成两部分：有序区和无序区。初始情况下，有序区为数组第一个元素，无序区包括剩余的其他元素。
 * （2）遍历无序区，每次向有序区增加一个元素，在增加元素后要保证有序区的有序性。
 * 
 * @param args
 */

public class InsertSort {
	public static int[] sort(int[] source) {
		for (int i = 1; i < source.length; i++) { // 从第二个元素开始遍历数组
			// 将需要插入的数临时保存
			int temp = source[i];

			int j = i - 1;
			// 与前面N个数比较，找出合适的存放位置
			for (; j >= 0 && source[j] > temp; j--) {
				// 大于temp的数后移1位
				source[j + 1] = source[j];
			}
			source[j + 1] = temp;
			System.out.println("第" + i + "趟的结果：" + Arrays.toString(source));
		}
		return source;
	}

	public static void main(String[] args) {
		int[] array = { 9, 10, 7, 5, 4, 30, 12, 1 };
		System.out.println("排序前：" + Arrays.toString(array));
		sort(array);
		// System.out.println("排序前："+Arrays.toString(sort(array)));
	}
}
