package com.linzl.swap.sort;

import java.util.Arrays;

/**
 * 冒泡排序思想：
 * 
 * (1)比较数组中相邻位置的两个元素，如果是反序则进行交换。
 * 
 * (2)重复步骤(1)直到所有元素变成有序的
 * 
 * 说明：冒泡排序算法每循环一次就将待排序数组中最大的元素移动到最右侧。
 * 
 * @author Administrator
 *
 */
public class BubbleSort {
	public static int[] sort(int[] array) {
		int length = array.length;
		int temp = 0;
		for (int i = 0; i < length - 1; i++) { // 因为最后一趟时只剩下一个数时，不需要再进行比较，所以减1
			for (int j = 0; j < length - 1 - i; j++) {
				if (array[j] > array[j + 1]) {
					temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
				}
			}
			System.out.println("第" + (i + 1) + "趟的结果：" + Arrays.toString(array));
		}
		return array;
	}

	public static void main(String[] args) {
		int[] array = { 9, 18, 7, 16, 5, 40, 3, 2 };
		System.out.println("排序前：" + Arrays.toString(array));
		sort(array);
	}

}
