package com.gitee.linzl.string;

/**
 * 1到正无穷放在一个无限长字符串中，求第N位是多少
 * 
 * @description
 * 
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2019年7月9日
 */
public class Snippet {

	public static char getnum(String str, int n) { // str是数列，n是要求的位数
		int m = n; // 对n做个备份
		char[] strings = str.toCharArray(); // 将字符串按位分解
		/**
		 * 1-9有9个数每个数字占1位，10-99有90个数每个数字占2位，100-999有900个数每个数字占3位，以此类推
		 * 用当前位数从1-9占的位开始减，然后是10-99，100-999，从小到大以此类推，减到某一档位数小于0了，说明这位的数字属于这一档
		 * (所求位数-之前各档位占用位数和)/当前档位 = 这个数-1是这一档位中的第几个数 (java向下取整，从第0个数开始，如10是第0个数)
		 * (所求位数-之前各档位占用位数和)%当前档位 = 这一位是目标数字的第几位 (如果整除了说明这是这个数的最后一位，没整除就是下一个数的对应数)
		 */
		for (int i = 1, j = 1;; i = i * 10, j++) { // i用来存放1，10，100.....即档位，j存放对应档位的数字占几位
			if ((m - (9 * i * j)) < 0) { // 目标位数-当前档位占用的位数<0说明目标数字在这个档位中
				char[] num = ((i + m / j - 1) + "").toCharArray(); // 把求得的数字放入数组中，方便按位取
				int remainder = m % j; // 获得余数
				if (remainder == 0) {
					return num[j - 1];
				} else {
					num = ((m / j) + "").toCharArray();
					return num[remainder - 1];
				}
			} else { // 继续减下一个档位
				m = m - (9 * i * j);
			}
		}
	}
}
