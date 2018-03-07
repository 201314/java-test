package com.linzl.cn.date;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期操作类
 * 
 * @author linzl 最后修改时间：2014年6月29日
 */
public class DateOperationUtil {
	public static int firstDay = Calendar.MONDAY;

	/**
	 * 根据自己需要设置一星期中的某一天作为一星期的开始 按照中国的习惯，星期一~星期日，分别对应1~7
	 * 
	 * @param firstDayParam
	 *            monday =1,tuesday =2, wednesday =3,thursday =4,firday = 5,
	 *            saturday =6,sunday = 7
	 * @return
	 */
	public static void setFirstDayOfWeek(int firstDayParam) {
		switch (firstDayParam) {
		case 2:
			firstDay = Calendar.TUESDAY;
			break;
		case 3:
			firstDay = Calendar.WEDNESDAY;
			break;
		case 4:
			firstDay = Calendar.THURSDAY;
			break;
		case 5:
			firstDay = Calendar.FRIDAY;
			break;
		case 6:
			firstDay = Calendar.SATURDAY;
			break;
		case 7:
			firstDay = Calendar.SUNDAY;
			break;
		case 1:
		default:
			firstDay = Calendar.MONDAY;
			break;
		}
	}

	/**
	 * 某一年某一个月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Calendar getFirstDay(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar;
	}

	/**
	 * 某一年某一个月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Calendar getEndDay(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, 1); // 获取设置下个月1号
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar;
	}

	/**
	 * 几个月前或后的第一天
	 * 
	 * @param month
	 * @return
	 */
	public static Calendar getFistDaySubtractMonth(int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar;
	}

	/**
	 * 几个月前或后的最后一天
	 * 
	 * @param month
	 * @return
	 */
	public static Calendar getEndDaySubtractMonth(int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, month + 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar;
	}

	/**
	 * 某个date日期,几周前或后的第一天
	 * 
	 * @param week
	 * @return
	 */
	public static Calendar getFirstDayAssignWeek(Date date, int week) {
		Calendar calendar = getSubtractDate(date, 0, 0, 7 * week);
		calendar.set(Calendar.DAY_OF_WEEK, firstDay);
		return calendar;
	}

	/**
	 * 某个date日期,几周前或后的最后一天
	 * 
	 * @param week
	 * @return
	 */
	public static Calendar getEndDayAssignWeek(Date date, int week) {
		Calendar calendar = getSubtractDate(date, 0, 0, 7 * week);
		calendar.set(Calendar.DAY_OF_WEEK, firstDay + 6);
		return calendar;
	}

	/**
	 * 本周第一天
	 * 
	 * @return
	 */
	public static Calendar getFirstDayCurrentWeek() {
		return getFirstDayAssignWeek(new Date(), 0);
	}

	/**
	 * 本周最后一天
	 * 
	 * @return
	 */
	public static Calendar getEndDayCurrentWeek() {
		return getEndDayAssignWeek(new Date(), 0);
	}

	/**
	 * 上一周第一天
	 * 
	 * @return
	 */
	public static Calendar getFirstDayLastWeek() {
		return getFirstDayAssignWeek(new Date(), -1);
	}

	/**
	 * 上一周最后一天
	 * 
	 * @return
	 */
	public static Calendar getEndDayLastWeek() {
		return getEndDayAssignWeek(new Date(), -1);
	}

	/**
	 * 下一周第一天
	 * 
	 * @return
	 */
	public static Calendar getFirstDayNextWeek() {
		return getFirstDayAssignWeek(new Date(), 1);
	}

	/**
	 * 下一周最后一天
	 * 
	 * @return
	 */
	public static Calendar getEndDayNextWeek() {
		return getEndDayAssignWeek(new Date(), 1);
	}

	/**
	 * 计算与某个日期差值为year年month月day日 的日历
	 * 
	 * @param date
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Calendar getSubtractDate(Date date, int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(firstDay);
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, year);
		calendar.add(Calendar.MONTH, month);
		calendar.add(Calendar.DATE, day);
		return calendar;
	}

	/**
	 * 与当前年相差Year年的日历
	 * 
	 * @param year
	 * @return
	 */
	public static Calendar getSubtractYear(int year) {
		return getSubtractDate(new Date(), year, 0, 0);
	}

	/**
	 * 与当前月份相差month月的日历
	 * 
	 * @param month
	 * @return
	 */
	public static Calendar getSubtractMonth(int month) {
		return getSubtractDate(new Date(), 0, month, 0);
	}

	/**
	 * 与当前天相差day天的日历
	 * 
	 * @param day
	 * @return
	 */
	public static Calendar getSubtractDay(int day) {
		return getSubtractDate(new Date(), 0, 0, day);
	}

	/**
	 * 计算两个日期时间差（毫秒） （差值转化为指定格式的差值 ，如 转化为多少秒，转化为多少时 , 多少月，多少年，多少周） 比较常用
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static long subtractDate(Date first, Date second) {
		long firstTime = first.getTime();
		long secondTime = second.getTime();
		long subTime = firstTime - secondTime;
		return subTime > 0 ? subTime : -subTime;
	}

	/**
	 * 计算两个日期时间差（天）
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static long subtractDay(Date first, Date second) {
		long subtract = subtractDate(first, second);
		return subtract / (24 * 60 * 60 * 1000);
	}

	/**
	 * 计算两个日期时间差（小时）
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static long subtractHour(Date first, Date second) {
		long subtract = subtractDate(first, second);
		return subtract / (1 * 60 * 60 * 1000);
	}

	/**
	 * 计算两个日期时间差（分钟）
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static long subtractMin(Date first, Date second) {
		long subtract = subtractDate(first, second);
		return subtract / (1 * 60 * 1000);
	}

	/**
	 * 计算两个日期时间差（秒）
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static long subtractSecond(Date first, Date second) {
		long subtract = subtractDate(first, second);
		return subtract / 1000;
	}

	public static void main(String[] args) {
		// 中国地区， 设置星期一为一周第一天
		DateOperationUtil.setFirstDayOfWeek(1);
		Date date = DateOperationUtil.getFirstDayLastWeek().getTime();
		System.out.println("上周第一天:" + DateFormatUtil.format(date));
		date = DateOperationUtil.getEndDayLastWeek().getTime();
		System.out.println("上周最后一天:" + DateFormatUtil.format(date));

		date = DateOperationUtil.getFirstDayCurrentWeek().getTime();
		System.out.println("本周第一天:" + DateFormatUtil.format(date));
		Date second = DateOperationUtil.getEndDayCurrentWeek().getTime();
		System.out.println("本周最后一天:" + DateFormatUtil.format(date));
		System.out.println("--->" + DateOperationUtil.subtractHour(date, second));

		date = DateOperationUtil.getFirstDayNextWeek().getTime();
		System.out.println("下周第一天:" + DateFormatUtil.format(date));
		date = DateOperationUtil.getEndDayNextWeek().getTime();
		System.out.println("下周最后一天:" + DateFormatUtil.format(date));

		date = DateOperationUtil.getFistDaySubtractMonth(-2).getTime();
		System.out.println("-2个月前或后的第一天：" + DateFormatUtil.format(date));
		date = DateOperationUtil.getEndDaySubtractMonth(-2).getTime();
		System.out.println("-2个月前的最后一天：" + DateFormatUtil.format(date));

		date = DateOperationUtil.getFirstDay(2013, 3).getTime();
		System.out.println("2013年3月的第一天:" + DateFormatUtil.format(date));
		date = DateOperationUtil.getEndDay(2013, 3).getTime();
		System.out.println("2013年3月的最后一天:" + DateFormatUtil.format(date));

		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.YEAR, 2013);
		cl.set(Calendar.MONTH, 4);
		cl.set(Calendar.DAY_OF_MONTH, 1);

		date = DateOperationUtil.getFirstDayAssignWeek(cl.getTime(), -5).getTime();
		System.out.println("2013年5月1号前5个星期:" + DateFormatUtil.format(date));
	}
}