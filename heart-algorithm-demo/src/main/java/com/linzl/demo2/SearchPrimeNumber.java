package com.linzl.demo2;

/**
 * @description 求出100以内的所有素数/质数
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2017年11月26日
 */
public class SearchPrimeNumber {

	public static void main(String[] args) {

	}

	public void firstMethod() {
		int i, j, k;

		for (i = 2; i <= 100; i++) {
			k = (int) Math.sqrt(i);

			for (j = 2; j <= k; j++) {
				if (i % j == 0) {
					break;
				}
			}

			if (j > k) {
				System.out.println(" " + i);
			}
		}
	}

	public void secondMethod() {
		int a[] = new int[101];
		int i;

		for (i = 1; i <= 100; i++)
			a[i] = 1;
		/**
		 * 根据筛选法求出100以内的所有素数，所谓筛选法是指从小到大筛去一个已知素数的所有倍数，
		 * 例如:根据2我们可筛去4，6，8，……98，100等数，然后根据3可筛去9，15，……，99等数
		 * （注意此时6，12等数早就被筛去了），由于4被筛去了，下一个用于筛选的素数是5……依次类推， 最后剩余的就是100以内的素数
		 */
		for (i = 2; i <= 100; i++) {
			if (a[i] != 0)
				for (int j = i + 1; j <= 100; j++) {
					if (j % i == 0)
						a[j] = 0;
				}
		}

		for (i = 2; i <= 100; i++) {
			if (a[i] != 0)
				System.out.println(" " + i);
		}
	}

}
