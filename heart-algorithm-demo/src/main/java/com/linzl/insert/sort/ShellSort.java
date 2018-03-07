package com.linzl.insert.sort;

/**
 * 1、计算出希尔排序每次的步长（增量） 2、 增量即为循环多少次直接插入排序
 */
public class ShellSort {
	public static int[] sort(int source[]) {
		int d = source.length;
		while (true) {
			d = (int) Math.ceil(d / 2);// 一趟增量为d的插入排序
			for (int k = 0; k < d; k++) {

				for (int i = k + d; i < source.length; i += d) {
					int temp = source[i];
					int j = i - d;
					for (; j >= 0 && source[j] > temp; j -= d) {
						source[j + d] = source[j];
					}
					source[j + d] = temp;
				}
			}

			if (d == 1) {
				return source;
			}
		}
	}

	public static void main(String[] args) {
		int source[] = { 2, 12, 8, 10, 7, 3, 9, 14, 19, 11, 5 };
		ShellSort.sort(source);
		for (int i : source) {
			System.out.print(i + " ");
		}
	}
}
