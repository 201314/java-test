package com.gitee.linzl.math;

import java.text.NumberFormat;

public class NumberUtil {
	/**
	 * 判断是否空值
	 * 
	 * @param number
	 * @param defaultVal
	 *            空值，使用默认值返回
	 * @return
	 */
	public static Long nullToDefault(Long number, Long defaultVal) {
		return number == null ? defaultVal : number;
	}

	/**
	 * 判断是否空值,空值默认0返回
	 * 
	 * @param number
	 * @return
	 */
	public static Long nullToDefault(Long number) {
		return nullToDefault(number, 0L);
	}

	/**
	 * 可以将科学记数转成原来的值
	 * 
	 * @param d
	 * @return
	 */
	public static Long double2Long(double d) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		String str = nf.format(d);
		Long value = Long.parseLong(str);
		return value;
	}
}
