package com.gitee.linzl.search.demo;

//顺序查找
public class SequnceSearch {

	public static int search(int a[], int key) {
		int temp[] = new int[a.length + 1];

		temp[0] = key;// 设置关键字为哨兵
		System.arraycopy(a, 0, temp, 1, a.length);

		int i = temp.length - 1;

		while (temp[i] != key) {
			i--;
		}

		return i;// 查询不到，返回0
	}

	public static void main(String[] args) {
		int a[] = { 3, 5, 9, 6, 4, 10, 31, 2, 14, 23, 7 };
		int key = 2;
		System.out.println(SequnceSearch.search(a, key));
	}
}
