package com.gitee.linzl.math;

import java.math.BigDecimal;

/**
 * 支持int , long , double ,String
 * 
 * @author linzl
 *
 */
public class BigDecimalUtil {
	BigDecimal bdFirst = null;
	BigDecimal bdSecond = null;

	public static BigDecimal add(int first, int second) {
		return add(String.valueOf(first), String.valueOf(second));
	}

	public static BigDecimal add(long first, long second) {
		return add(String.valueOf(first), String.valueOf(second));
	}

	/**
	 * 
	 * @param first
	 * @param second
	 * @return
	 */

	public static BigDecimal add(double first, double second) {
		return add(String.valueOf(first), String.valueOf(second));
	}

	/**
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static BigDecimal add(String first, String second) {
		BigDecimal bdFirst = new BigDecimal(first);
		BigDecimal bdSecond = new BigDecimal(second);
		return bdFirst.add(bdSecond);
	}

	/**
	 * 除法
	 * 
	 * @return
	 */
	public static BigDecimal divide(String first, String second) {
		BigDecimal bdFirst = new BigDecimal(first);
		BigDecimal bdSecond = new BigDecimal(second);
		return bdFirst.divide(bdSecond, 2);// RoundingMode
	}

	/**
	 * 乘法
	 * 
	 * @return
	 */
	public static BigDecimal multiply(String first, String second) {
		BigDecimal bdFirst = new BigDecimal(first);
		BigDecimal bdSecond = new BigDecimal(second);
		return bdFirst.multiply(bdSecond);
	}

	/**
	 * 减法
	 * 
	 * @return
	 */
	public static BigDecimal subtract(String first, String second) {
		BigDecimal bdFirst = new BigDecimal(first);
		BigDecimal bdSecond = new BigDecimal(second);
		return bdFirst.subtract(bdSecond);
	}

	public static void main(String[] args) {
		System.out.println(BigDecimalUtil.divide("-27.9", "3.6"));
	}

}