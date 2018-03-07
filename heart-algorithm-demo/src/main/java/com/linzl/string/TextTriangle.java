package com.linzl.string;

/**
 * @description 每一行的相连两数之和等于第二行的第一个数
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2017年11月29日
 */
public class TextTriangle {

	public int[][] printTriangle(int a[][], int row) {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (j == 0 | j == a[i].length - 1) {
					a[i][j] = 1;
				} else {
					a[i][j] = a[i - 1][j - 1] + a[i - 1][j];
				}
			}
		}

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < a[i].length; j++) {
				System.out.print(" " + a[i][j]);
			}
			System.out.println();
		}
		return a;
	}

	public static void main(String[] args) {
		int row = 10;
		int a[][] = new int[row][];
		for (int i = 0; i < row; i++) {
			a[i] = new int[i + 1];
		}
		new TextTriangle().printTriangle(a, row);
	}
}
