package com.gitee.linzl.sort;

/**
 * 基本思想：选择一个基准元素,通常选择第一个元素或者最后一个元素,通过一趟扫描，将待排序列分成两部分,
 * 一部分比基准元素小,一部分大于等于基准元素,此时基准元素在其排好序后的正确位置,然后再用同样的方法递归地排序划分的两部分。
 */
public class QuickSort {

	public static int getMiddle(int[] source, int low, int high) {
		int temp = source[low];// 数组的第一个作为中轴

		while (low < high) {

			// 一定要从高端开始比较
			while (low < high && source[high] >= temp) {
				high--;
			}
			source[low] = source[high];// 比中轴小的记录移到低端

			while (low < high && source[low + 1] <= temp) {
				low++;
			}
			source[high] = source[low];// 比中轴大的记录移到高端

		}

		source[low] = temp; // 中轴记录到适当位置
		return low;// 返回中轴的位置
	}

	public static void apart(int[] source, int low, int high) {
		if (low < high) {
			int middle = getMiddle(source, low, high); // 将source数组进行一分为二
			apart(source, low, middle - 1); // 对低字表进行递归排序
			apart(source, middle + 1, high);// 对高字表进行递归排序
		}
	}

	public static void main(String[] args) {
		int[] source = { 2, 12, 8, 10, 7, 3, 9, 14, 19, 11, 5 };
		QuickSort.apart(source, 0, source.length - 1);
		for (int i : source) {
			System.out.print(i + " ");
		}
	}
}
