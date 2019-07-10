package com.gitee.linzl.demo;

import java.util.Comparator;

public class ComparatorDemo {
	public static <T> T findMax(T[] arr, Comparator<? super T> cmp) {
		int maxIndex = 0;

		for (int i = 1; i < arr.length; i++) {
			if (cmp.compare(arr[i], arr[maxIndex]) > 0) {
				maxIndex = i;
			}
		}
		return arr[maxIndex];
	}

	public static void main(String[] args) {
		String[] arr = { "ZEBRA", "alligator", "crocodile" };
		String max = findMax(arr, (String lhs, String rhs) -> {
			return lhs.compareToIgnoreCase(rhs);
		});
		System.out.println(max);
	}
}
