package com.gitee.linzl.time;

import java.util.Date;

import org.joda.time.DateTime;

/**
 * 判断两个日期间有多少个工作日，有多少个周末 判断是否为工作日 判断是否为周末
 * 
 * 日期操作类
 * 
 * @author linzl 最后修改时间：2014年6月29日
 */
public class DateOperationUtil {
	// 一周的第一天为星期一
	public static final int firstDayOfChinaWeek = 1;
	// 一周的第一天为星期日
	public static final int firstDayOfEnglishWeek = 7;

	public static int firstDayOfWeek = firstDayOfChinaWeek;

	/**
	 * 某一年某一个月的第一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static DateTime getFirstDay(int year, int month) {
		DateTime time = DateTime.now();
		time = time.withYear(year);
		time = time.withMonthOfYear(month);
		time = time.withDayOfMonth(1);
		return time;
	}

	/**
	 * 某一年某一个月的最后一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static DateTime getEndDay(int year, int month) {
		DateTime time = DateTime.now();
		time = time.withYear(year);
		time = time.withMonthOfYear(month);
		time = time.withDayOfMonth(1);
		time = time.minusDays(1);
		return time;
	}

	/**
	 * 几个月前的第一天
	 *
	 * @param month
	 *            参数必须大于0
	 * @return
	 */
	public static DateTime getFistDayBeforeMonth(int month) {
		DateTime time = DateTime.now();
		time = time.minusMonths(month);
		time = time.withDayOfMonth(1);
		return time;
	}

	/**
	 * 几个月后的第一天
	 *
	 * @param month
	 *            参数必须大于0
	 * @return
	 */
	public static DateTime getFistDayAfterMonth(int month) {
		DateTime time = DateTime.now();
		time = time.plusMonths(month);
		time = time.withDayOfMonth(1);
		return time;
	}

	/**
	 * 几个月前的最后一天
	 *
	 * @param month
	 *            参数必须大于0
	 * @return
	 */
	public static DateTime getEndDayBeforeMonth(int month) {
		DateTime time = DateTime.now();
		time = time.minusMonths(month - 1);
		time = time.withDayOfMonth(1);
		time = time.minusDays(1);
		return time;
	}

	/**
	 * 几个月后的最后一天
	 *
	 * @param month
	 *            参数必须大于0
	 * @return
	 */
	public static DateTime getEndDayAfterMonth(int month) {
		DateTime time = DateTime.now();
		time = time.plusMonths(month + 1);
		time = time.withDayOfMonth(1);
		time = time.minusDays(1);
		return time;
	}

	/**
	 * 几周前的第一天
	 *
	 * @param week
	 *            参数必须大于0
	 * @return
	 */
	public static DateTime getFirstDayBeforeWeek(int week) {
		DateTime time = DateTime.now();
		time = time.minusWeeks(week);
		time = time.withDayOfWeek(firstDayOfWeek);
		return time;
	}

	/**
	 * 几周后的第一天
	 *
	 * @param week
	 *            参数必须大于0
	 * @return
	 */
	public static DateTime getFirstDayAfterWeek(int week) {
		DateTime time = DateTime.now();
		time = time.plusWeeks(week);
		time = time.withDayOfWeek(firstDayOfWeek);
		return time;
	}

	/**
	 * 几周前的最后一天
	 *
	 * @param week
	 *            参数必须大于0
	 * @return
	 */
	public static DateTime getEndDayBeforeWeek(int week) {
		DateTime time = DateTime.now();
		time = time.minusWeeks(week);
		time = time.withDayOfWeek(firstDayOfWeek);
		time = time.plusDays(6);
		return time;
	}

	/**
	 * 几周后的最后一天
	 *
	 * @param week
	 *            参数必须大于0
	 * @return
	 */
	public static DateTime getEndDayAfterWeek(int week) {
		DateTime time = DateTime.now();
		time = time.plusWeeks(week);
		time = time.withDayOfWeek(firstDayOfWeek);
		time = time.plusDays(6);
		return time;
	}

	/**
	 * 本周第一天
	 *
	 * @return
	 */
	public static DateTime getFirstDayCurrentWeek() {
		return getFirstDayBeforeWeek(0);
	}

	/**
	 * 本周最后一天
	 *
	 * @return
	 */
	public static DateTime getEndDayCurrentWeek() {
		return getEndDayBeforeWeek(0);
	}

	/**
	 * 上一周第一天
	 *
	 * @return
	 */
	public static DateTime getFirstDayLastWeek() {
		return getFirstDayBeforeWeek(1);
	}

	/**
	 * 上一周最后一天
	 *
	 * @return
	 */
	public static DateTime getEndDayLastWeek() {
		return getEndDayBeforeWeek(1);
	}

	/**
	 * 下一周第一天
	 *
	 * @return
	 */
	public static DateTime getFirstDayNextWeek() {
		return getFirstDayAfterWeek(1);
	}

	/**
	 * 下一周最后一天
	 *
	 * @return
	 */
	public static DateTime getEndDayNextWeek() {
		return getEndDayAfterWeek(1);
	}

	public static void main(String[] args) {
		// 设置星期一为一周第一天
		DateOperationUtil.firstDayOfWeek = DateOperationUtil.firstDayOfEnglishWeek;

		Date date = getFirstDayLastWeek().toDate();
		System.out.println("上周第一天:" + TimeFormatUtil.format(TimeFormatUtil.LINE_YMD_E, date));
		date = getEndDayLastWeek().toDate();
		System.out.println("上周最后一天:" + TimeFormatUtil.format(TimeFormatUtil.LINE_YMD_E, date));
		date = getFirstDayCurrentWeek().toDate();
		System.out.println("本周第一天:" + TimeFormatUtil.format(TimeFormatUtil.LINE_YMD_E, date));
		date = getEndDayCurrentWeek().toDate();
		System.out.println("本周最后一天:" + TimeFormatUtil.format(TimeFormatUtil.LINE_YMD_E, date));
		date = getFirstDayNextWeek().toDate();
		System.out.println("下周第一天:" + TimeFormatUtil.format(TimeFormatUtil.LINE_YMD_E, date));
		date = getEndDayNextWeek().toDate();
		System.out.println("下周最后一天:" + TimeFormatUtil.format(TimeFormatUtil.LINE_YMD_E, date));

		date = getFistDayBeforeMonth(2).toDate();
		System.out.println("2个月前的第一天：" + TimeFormatUtil.format(date));
		date = getFistDayAfterMonth(2).toDate();
		System.out.println("2个月后的第一天：" + TimeFormatUtil.format(date));

		date = getEndDayBeforeMonth(2).toDate();
		System.out.println("2个月前的最后一天：" + TimeFormatUtil.format(date));
		date = getEndDayAfterMonth(2).toDate();
		System.out.println("2个月后的最后一天：" + TimeFormatUtil.format(date));

		date = getFirstDay(2013, 3).toDate();
		System.out.println("2013年3月的第一天:" + TimeFormatUtil.format(date));
		date = getEndDay(2013, 3).toDate();
		System.out.println("2013年3月的最后一天:" + TimeFormatUtil.format(date));

	}
}