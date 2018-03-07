package day02;

import java.util.Arrays;

public class Test1 {
	static int sum;

	public static void main(String[] args) {
		int a[] = { 100, 95, 100, 93, 94, 93, 96, 97, 98, 99 };
		int average = 0;
		Arrays.sort(a);
		for (int i = 0; i < a.length; i++) {
			sum += a[i];
		}
		average = (sum - a[0] - a[a.length - 1]) / (a.length - 2);
		System.out.print(average);
	}
}
