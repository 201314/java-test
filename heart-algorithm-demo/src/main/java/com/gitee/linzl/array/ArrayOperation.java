package com.gitee.linzl.array;

public class ArrayOperation {
	/**
	 * 在array[]插入数据的位置及数值
	 * 
	 * @param index
	 * @param obj
	 * @param array
	 * @throws Exception
	 */
	public static void addData(int index, Object obj, Object[] array) throws Exception {
		Object newArray[] = new Object[array.length + 1];
		if (index < 1 || index > (array.length + 1)) {
			throw new Exception("插入范围不正确，正确范围为：1~" + array.length);
		} else {
			for (int i = array.length - 1; i >= index; i--)
				newArray[i + 1] = array[i]; // 将插入位置后的数据 后移1位
		}

		newArray[index] = obj; // 将插入的数据 插入到指定位置

		// 将不需要 移动的 数据复制到新数组
		while (--index >= 0)
			newArray[index] = array[index];

		for (Object object : newArray)
			System.out.println("--" + object);
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
			for (int i = index; i < array.length - 1; i++)
				array[i] = array[i + 1]; // 将插入位置后的数据 后移1位
		}

		array[array.length - 1] = 0;
		System.out.println("----------------");
		for (Object object : array)
			System.out.println("--" + object);

		return array;
	}

	/**
	 * 删除array[]中某个数据
	 * 
	 * @param obj
	 * @param array
	 * @throws Exception
	 */
	public static void removeData(Object obj, Object array[]) throws Exception {
		int j = array.length;
		for (int i = 0; i < array.length; i++) {
			if (array[i] == obj)
				j = i;
		}
		if (j == array.length) {
			throw new Exception(obj + "不存在");
		}
		removeIndex(j, array);
	}

	public static void main(String[] args) throws Exception {
		Object obj = 10;
		Object array[] = { 1, 2, 6, 7, 9, 11, 54, 235 };
		// addData(5, obj, array);
		// removeIndex(5, array);
		removeData(9, array);
	}
}
