package com.gitee.linzl.math;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 支持int , long , double ,String
 * 
 * @author linzl
 *
 */
public class BigDecimalUtil {
	/**
	 * 分转成元
	 * 
	 * @param points
	 *            多少分钱
	 * @return
	 */
	public static String pointsToYuan(int points) {
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(points / 100.00);
	}

	/**
	 * 元转成分
	 * 
	 * @param yuan
	 *            多少元
	 * @return
	 */
	public static BigDecimal yuanTopoints(String yuan) {
		return multiply(yuan, "100");
	}

	/**
	 * 加法
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
	 * 减法
	 * 
	 * @return
	 */
	public static BigDecimal subtract(String first, String second) {
		BigDecimal bdFirst = new BigDecimal(first);
		BigDecimal bdSecond = new BigDecimal(second);
		return bdFirst.subtract(bdSecond);
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
	 * 除法
	 * 
	 * @return
	 */
	public static BigDecimal divide(String first, String second) {
		BigDecimal bdFirst = new BigDecimal(first);
		BigDecimal bdSecond = new BigDecimal(second);
		return bdFirst.divide(bdSecond, BigDecimal.ROUND_CEILING);
	}

	public static void main(String[] args) {
		System.out.println(BigDecimalUtil.divide("-27.9", "3.6"));
		DecimalFormat df = new DecimalFormat("#0.00");
		System.out.println(df.format(201 / 100.00));

		System.out.println(divide("201", "100"));
	}
}