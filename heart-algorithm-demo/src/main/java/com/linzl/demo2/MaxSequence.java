package com.linzl.demo2;

public class MaxSequence {
	/**
	 * @param args
	 *            找出最大子序列
	 */
	public static int findMax(int a[]) {
		int thisSum = 0;
		int thatSum = 0;
		int index[] = new int[2];
		for (int i = 0; i < a.length; i++) {
			thisSum += a[i];
			if (thisSum > 0) {
				if (index[0] == 0) {
					index[0] = i;
				}
				if (thisSum > thatSum) {// 不断和之前的大序列比较得出最大的
					thatSum = thisSum;
					index[1] = i;
				}
			} else {
				thisSum = 0;
				// 如果子序列为负，则不理会
				index[0] = 0;
			}
		}

		System.out.println("最大子序列距离 起始位置长度--终点位置长度：");
		for (int i : index) {
			System.out.print("--" + i + "--");
		}

		return thatSum;
	}

	public static void main(String[] args) {
		int a[] = { 12, -19, 5, 3, -6, 11, 10, -20 };
		// int b[] = { -2, 11, -4, 13, -5, -2 };

		System.out.println("\n最大子序列和：" + findMax(a));// 最大的子序列
	}
}
