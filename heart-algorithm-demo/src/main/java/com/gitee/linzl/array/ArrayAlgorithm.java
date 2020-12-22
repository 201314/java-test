package com.gitee.linzl.array;

public class ArrayAlgorithm {
	/**
	 * 把一个数组里的所有数的组合全部列出，比如 1 2列出来为1,2,12,21，要求打印出来的不能有重复数
	 * 
	 * @param str
	 * @param prefix
	 */
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

	/**
	 * 两个线性表，按顺序合并，并将重复的数据去除
	 */
	public static int[] merge(int[] first, int[] second) {
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
		while (j < first.length) {
			temp[k++] = second[j++];
		}
		while (i < second.length) {
			temp[k++] = second[i++];
		}
		return temp;
	}

	/**
	 * 从顺序表中删除所有值为X的元素，空间复杂度为O(1)
	 * 
	 * 分析：从头开始扫描整个顺序表，如果遇到值为X的元素，使用变量记录其出现的次数。 对于不等于X的元素，将其向前移动K个位置。
	 */
	public static int[] delete(int[] array, int delete) {
		int k = 0; // 记录元素出现次数
		for (int i = 0; i < array.length; i++) {
			if (array[i] == delete) { // 如果遇到需要删除的元素则K+1
				k++;
			} else {
				array[i - k] = array[i]; // 将元素向前移动
			}
		}
		int[] newArray = new int[array.length - k]; // 创建新数组
		System.arraycopy(array, 0, newArray, 0, newArray.length);
		return newArray; // 返回删除元素后的数组
	}

	/**
	 * 使用顺序表作为存储结构，实现线性表逆置。保存的元素类型是int。要求不能申请新的空间。
	 * 
	 * 对于两个整数进行交换，可以使用以下算法： int a=10,b=5;
	 * 
	 * a=a+b;
	 * 
	 * b=a-b;
	 * 
	 * a=a-b;
	 */
	public static int[] reverse(int[] array) {
		int length = array.length;
		for (int i = 0; i < length / 2; i++) {// 遍历数组的前半部分并交换元素
			array[i] = array[i] + array[length - i - 1];
			array[length - i - 1] = array[i] - array[length - i - 1];
			array[i] = array[i] - array[length - i - 1];
		}
		return array;
	}

	/**
	 * 对于两个有序整形数组，编写算法实现交集运算。
	 * 
	 * 分析：就是将两个数组中的公共元素复制到第三个数组中。
	 * 
	 * 可以从头遍历第一个数组， 如果在第二个数组中遇到了相同的元素，将其复制到第三个数组中即可。
	 */
	public static int[] same(int[] array1, int[] array2) {
		int array1T = 0;
		int array1Length = array1.length;

		int array2T = 0;
		int array2Length = array2.length;
		// 定义新数组
		int[] array = new int[Math.max(array1Length, array2Length)];

		int size = 0;
		while (array1T < array1Length && array2T < array2Length) {
			if (array1[array1T] < array2[array2T]) {
				array1T++;
			} else if (array1[array1T] > array2[array2T]) {
				array2T++;
			} else {
				array[size++] = array1[array1T];
				array1T++;
				array2T++;
			}
		}

//		for (int i = 0; i < array1Length; i++) {
//			for (int j = 0; j < array2Length; j++) {
//				if (array1[i] == array2[j]) {
//					array[size++] = array1[i];// 如果遇到相同的元素则保存
//					break;
//				}
//			}
//		}
		int[] newArray = new int[size];
		System.arraycopy(array, 0, newArray, 0, size);// 复制元素
		return newArray;
	}

	/**
	 * 已知数组array为整形数组，将其分成左右两部分。
	 * 
	 * 编写算法使左边所有的元素为奇数，右边所有的元素为偶数。
	 * 
	 * 分析：可以从数组的左右两端不断地向中间比较，如果左端遇到偶数，右端遇到奇数，则将两者进行交接。该算法的时间复杂度为O(n)
	 */
	public static int[] swapOddEven(int[] array) {
		int i = 0;
		int j = array.length - 1;
		int temp = 0;
		while (i < j) {// 从左右两边同时遍历数组
			while (array[i] % 2 != 0) {// 如果左边遇到奇数则继续遍历
				i++;
			}

			while (array[j] % 2 == 0) {// 如果右边遇到偶数则继续遍历
				j--;
			}

			if (i < j) {// 交换数组的元素
				temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}
		return array;
	}

	/**
	 * 在array[]插入数据的位置及数值
	 * 
	 * @param index
	 * @param obj
	 * @param array
	 * @throws Exception
	 */
	public static Object[] addData(int index, Object obj, Object[] array) throws Exception {
		Object[] newArray = new Object[array.length + 1];
		if (index < 1 || index > (array.length + 1)) {
			throw new Exception("插入范围不正确，正确范围为：1~" + array.length);
		} else {
			for (int i = array.length - 1; i >= index; i--) {
				newArray[i + 1] = array[i]; // 将插入位置后的数据 后移1位
			}
		}

		newArray[index] = obj; // 将插入的数据 插入到指定位置

		// 将不需要 移动的 数据复制到新数组
		while (--index >= 0) {
			newArray[index] = array[index];
		}
		return newArray;
	}

	/**
	 * 删除array[]第几个数据
	 * 
	 * @param index
	 * @param array
	 * @return
	 * @throws Exception
	 */
	public static Object[] removeIndex(int index, Object[] array) throws Exception {
		if (index < 1 || index > (array.length + 1)) {
			throw new Exception("删除范围不正确，正确范围为：0~" + (array.length - 1));
		} else {
			for (int i = index; i < array.length - 1; i++) {
				array[i] = array[i + 1];
			}
		}

		array[array.length - 1] = 0;
		return array;
	}

	/**
	 * 删除array[]中某个数据
	 * 
	 * @param obj
	 * @param array
	 * @throws Exception
	 */
	public static Object[] removeData(Object obj, Object[] array) throws Exception {
		int j = array.length;
		for (int i = 0; i < array.length; i++) {
			if (array[i] == obj) {
				j = i;
				break;
			}
		}
		if (j == array.length) {
			throw new Exception(obj + "不存在");
		}
		return removeIndex(j, array);
	}
}
