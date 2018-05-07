package com.gitee.linzl.demo2;

import java.util.Arrays;

/**
 * 从顺序表中删除所有值为X的元素，空间复杂度为O(1)
 * 
 * 分析：从头开始扫描整个顺序表，如果遇到值为X的元素，使用变量记录其出现的次数。 对于不等于X的元素，将其向前移动K个位置。
 * 
 * @author Administrator
 */
public class DeleteAssignData {
	public static int[] delete(int[] array, int delete) {
		int k = 0; // 记录元素出现次数
		for (int i = 0; i < array.length; i++) {
			if (array[i] == delete) { // 如果遇到需要删除的元素则K+1
				k++;
			} else {
				array[i - k] = array[i]; // 将元素向前移动
			}
		}
		int[] newArray = new int[array.length - k]; // 创建新数组
		System.arraycopy(array, 0, newArray, 0, newArray.length);
		return newArray; // 返回删除元素后的数组
	}

	public static void main(String[] args) {
		int[] array = { 1, 2, 3, 4, 5, 6 };
		System.out.println("程序运行前：" + Arrays.toString(array));
		System.out.println("程序运行后：" + Arrays.toString(delete(array, 2)));
	}
}
