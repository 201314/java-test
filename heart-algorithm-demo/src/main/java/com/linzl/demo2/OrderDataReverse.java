package com.linzl.demo2;

import java.util.Arrays;

/**
 * 使用顺序表作为存储结构，实现线性表逆置。保存的元素类型是int。要求不能申请新的空间。
 * 
 * 对于两个整数进行交换，可以使用以下算法：
 * 
 * int a=10; int b=5; a=a+b; b=a-b; a=a-b;
 * 
 * @author Administrator
 */
public class OrderDataReverse {
	public static int[] reverse(int[] array) {
		int length = array.length;
		for (int i = 0; i < length / 2; i++) {// 遍历数组的前半部分并交换元素
			array[i] = array[i] + array[length - i - 1];
			array[length - i - 1] = array[i] - array[length - i - 1];
			array[i] = array[i] - array[length - i - 1];
		}
		return array;
	}

	public static void main(String[] args) {
		int[] array = { 2, 9, 1, 8, 7, 3 };//{ 1, 2, 5, 9, 14, 15, 36, 54, 99 };
		System.out.println("程序运行前：" + Arrays.toString(array));
		System.out.println("程序运行后：" + Arrays.toString(reverse(array)));
	}
}
