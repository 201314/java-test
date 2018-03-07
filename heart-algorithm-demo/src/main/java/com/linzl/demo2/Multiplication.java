package com.linzl.demo2;

/**
 * @description 99乘法口诀
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2017年11月26日
 */
public class Multiplication {
	public static void main(String[] args) {
		jiujiuchenfa();
		jiujiuchenfa(1, 1);
		jiujiuchenfa1();
	}

	/**
	 * 使用一个for循环完成
	 */
	public static void jiujiuchenfa() {
		for (int i = 1, j = 1; i <= 9; j++) {
			System.out.print(j + "*" + i + "=" + j * i + " ");
			if (i == j) {
				j = 0;
				i++;
				System.out.println();
			}
		}
	}

	/**
	 * 使用递归求99乘法口诀
	 * 
	 * @param i
	 * @param j
	 */
	public static void jiujiuchenfa(int i, int j) {
		if (i <= 9) {
			System.out.print(j + "*" + i + "=" + j * i + " ");
			if (i == j) {
				j = 0;
				i++;
				System.out.println();
			}
			jiujiuchenfa(i, ++j);
		}
	}

	/*
	 * 使用两个for循环完成
	 */
	public static void jiujiuchenfa1() {
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= i; j++) {
				System.out.print(j + "*" + i + "=" + j * i + " ");
				if (i == j) {
					System.out.println();
				}
			}
		}
	}
}
