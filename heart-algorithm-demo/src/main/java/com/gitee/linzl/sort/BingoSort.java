package com.gitee.linzl.sort;

import java.util.Arrays;

/**
 * 选择排序算法思想： （1）将序列分成有序区和无序区两部分，初始时有序区为空，无序区包括全部元素。
 * （2）每次从无序区中选择最小的元素将其与无序区第一个元素进行交换。 （3）重复（2），直到无序区没有元素。
 * 
 * @author Administrator
 *
 */
public class BingoSort {
	public static int[] sort(int[] array) {
		int temp = 0; // 定义用于交换元素的临时变量
		int index = 0; // 保存元素的下标
		for (int i = 0; i < array.length; i++) {
			index = i;
			for (int j = i; j < array.length; j++) { // 查找最小元素
				if (array[j] < array[index])
					index = j;
			}
			if (index != i) { // 将最小元素进行交换
				temp = array[i];
				array[i] = array[index];
				array[index] = temp;
			}
			System.out.println("第" + (i + 1) + "趟的结果：" + Arrays.toString(array));
		}
		return array;
	}

	public static void main(String[] args) {
		int[] array = { 9, 8, 5, 3, 6, 4, 2, 10 };
		System.out.println("排序前：" + Arrays.toString(array));
		System.out.println("排序后：" + Arrays.toString(sort(array)));
	}
}
