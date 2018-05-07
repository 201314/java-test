package com.gitee.linzl.recursion.demo;

public class ArrayCombination {
	/**
	 * 把一个数组里的所有数的组合全部列出，比如 1 2列出来为1，2，12，21，要求打印出来的不能有重复数
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "12324";
		listAll(str, "");
	}

	public static void listAll(String str, String prefix) {
		System.out.println(prefix);
		if (str.length() >= 1) {
			int[] index = new int[str.length()];
			for (int i = 0; i < str.length(); i++) {
				index[i] = str.indexOf(str.charAt(i));// 该循环将所有字符第一次出现的位置记录在数组中
			}

			for (int i = 0; i < str.length(); i++) {
				if (i == index[i]) {
					System.out.println("长度为：" + str.length());
					listAll(str.substring(1), prefix + str.substring(0, 1));
				}
				str = str.substring(1) + str.substring(0, 1);
			}
		}
	}
}
