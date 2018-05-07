package com.gitee.linzl.demo2;

/**
 * 9*9乘法表
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年4月26日
 */
public class MultiplicationTable {
	public void test() {
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= i; j++) {
				System.out.print(i + "*" + j + "=" + i * j + "\t");
				if (j == i)
					System.out.println();
			}
		}
	}

	public static void main(String args[]) {
		MultiplicationTable hello = new MultiplicationTable();
		hello.test();
	}
}
