package com.gitee.linzl.array;

import java.util.Arrays;

import org.junit.Test;

public class ArrayTest {
	@Test
	public void listAll() {
		String str = "12324";
		ArrayAlgorithm.listAll(str, "");
	}

	@Test
	public void combine() {
		// 两个线性表，按顺序合并，并将重复的数据去除
		int first[] = { 1, 3, 4, 9, 11, 56, 89, 456 };
		int second[] = { 0, 3, 5, 6, 8, 10, 11, 47, 60, 498 };
		int temp[] = ArrayAlgorithm.merge(first, second);
		System.out.println(Arrays.toString(temp));
	}

	@Test
	public void delete() {
		int[] array = { 1, 2, 3, 4, 5, 6, 2, 5, 2, 9, 5, 4 };
		System.out.println("程序运行前：" + Arrays.toString(array));
		System.out.println("程序运行后：" + Arrays.toString(ArrayAlgorithm.delete(array, 2)));
	}

	@Test
	public void reverse() {
		int[] array = { 2, 9, 1, 8, 7, 3 };// { 1, 2, 5, 9, 14, 15, 36, 54, 99 };
		System.out.println("程序运行前：" + Arrays.toString(array));
		System.out.println("程序运行后：" + Arrays.toString(ArrayAlgorithm.reverse(array)));
	}

	@Test
	public void same() {
		int[] array1 = { 1, 2, 4, 9, 12 };
		int[] array2 = { 2, 6, 9, 11, 12, 16 };
		System.out.println("第一个数组：" + Arrays.toString(array1));
		System.out.println("第二个数组：" + Arrays.toString(array2));
		System.out.println("两个数组的交集：" + Arrays.toString(ArrayAlgorithm.same(array1, array2)));
	}

	@Test
	public void swapOddEven() {
		int[] array = { 1, 3, 6, 8, 9, 10, 14, 15, 18, 60, 85, 99 };
		System.out.println("程序运行前：" + Arrays.toString(array));
		System.out.println("程序运行后：" + Arrays.toString(ArrayAlgorithm.swapOddEven(array)));
	}

	@Test
	public void arrayCURD() throws Exception {
		Object array[] = { 1, 2, 6, 7, 9, 11, 54, 235 };
		System.out.println("在序号5添加数字10:" + Arrays.toString(ArrayAlgorithm.addData(5, 10, array)));
		System.out.println("删除序号5的数字:" + Arrays.toString(ArrayAlgorithm.removeIndex(5, array)));
		System.out.println("删除数字9:" + Arrays.toString(ArrayAlgorithm.removeData(9, array)));
	}
}
