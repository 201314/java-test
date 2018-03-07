package com.linzl.demo;

import java.util.*;

/**
 * @description 得到输入值的各种排列组合
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2017年11月26日
 */
public class Arrange {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String str = scan.nextLine();

		List<String> list = new ArrayList<String>();
		for (int i = 0; i < str.length(); i++) {
			list.add(i, str.substring(i, i + 1));
		}
		perm(list, 0, list.size() - 1);

	}

	public static void perm(List<String> list, int k, int m) {
		if (k == m) {
			for (int i = 0; i <= m; i++) {
				System.out.print(list.get(i));
			}
			System.out.println();
		} else {
			for (int i = k; i <= m; i++) {
				Collections.swap(list, k, i);
				perm(list, k + 1, m);
				Collections.swap(list, k, i);
			}
		}
	}
}