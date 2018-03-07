package com.linzl.array;

public class ArrayCombine {
	public static int[] combineAndRemoveRepeat(int first[], int second[]) {
		int[] temp = new int[first.length + second.length];
		int i = 0, j = 0, k = 0;
		while (j < first.length && i < second.length) {
			if (first[j] > second[i]) {
				temp[k++] = second[i++];
			} else if (first[j] < second[i]) {
				temp[k++] = first[j++];
			} else {
				temp[k++] = first[j++];
				i++;
			}
		}
		while (i < second.length)
			temp[k++] = second[i++];
		while (j < first.length)
			temp[k++] = second[j++];

		return temp;
	}

	public static void main(String[] args) {
		// 两个线性表，按顺序合并，并将重复的数据去除
		int first[] = { 1, 3, 4, 9, 11, 56, 89, 456 };
		int second[] = { 0, 3, 5, 6, 8, 10, 11, 47, 60, 498 };
		int temp[] = combineAndRemoveRepeat(first, second);
		for (int i : temp) {
			System.out.println("--" + i);
		}

	}
}
