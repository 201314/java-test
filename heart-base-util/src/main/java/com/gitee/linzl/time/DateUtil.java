package com.gitee.linzl.time;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	/**
	 * 某一年某一个月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static LocalDateTime getFirstDay(int year, int month) {
		LocalDateTime localDate = LocalDateTime.now();
		localDate = localDate.withYear(year);
		localDate = localDate.withMonth(month);
		localDate = localDate.withDayOfMonth(1);
		return localDate;
	}

	/**
	 * 某一年某一个月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static LocalDateTime getEndDay(int year, int month) {
		LocalDateTime localDate = getFirstDay(year, month);
		localDate = localDate.plusMonths(1);
		localDate = localDate.minusDays(1);
		return localDate;
	}

	/**
	 * 几个月前的第一天
	 * 
	 * @param month
	 *            参数必须大于0
	 * @return
	 */
	public static LocalDateTime getFirstDayBeforeMonth(int month) {
		LocalDateTime localDate = LocalDateTime.now();
		localDate = localDate.minusMonths(month);
		localDate = localDate.withDayOfMonth(1);
		return localDate;
	}

	/**
	 * 几个月后的第一天
	 *
	 * @param month
	 *            参数必须大于0
	 * @return
	 */
	public static LocalDateTime getFistDayAfterMonth(int month) {
		LocalDateTime localDate = LocalDateTime.now();
		localDate = localDate.plusMonths(month);
		localDate = localDate.withDayOfMonth(1);
		return localDate;
	}

	/**
	 * 几个月前最后一天
	 * 
	 * @param month
	 * @return
	 */
	public static LocalDateTime getEndDayBeforeMonth(int month) {
		LocalDateTime localDate = LocalDateTime.now();
		localDate = localDate.minusMonths(month - 1);
		localDate = localDate.withDayOfMonth(1);
		localDate = localDate.minusDays(1);
		return localDate;
	}

	/**
	 * 几个月后的最后一天
	 *
	 * @param month
	 *            参数必须大于0
	 * @return
	 */
	public static LocalDateTime getEndDayAfterMonth(int month) {
		LocalDateTime localDate = LocalDateTime.now();
		localDate = localDate.plusMonths(month + 1);
		localDate = localDate.withDayOfMonth(1);
		localDate = localDate.minusDays(1);
		return localDate;
	}

	/**
	 * 几周前的第一天
	 *
	 * @param week
	 *            参数必须大于0
	 * @return
	 */
	public static LocalDateTime getFirstDayBeforeWeek(int week) {
		LocalDateTime localDate = LocalDateTime.now();
		localDate = localDate.minusWeeks(week);
		localDate = localDate.with(DayOfWeek.MONDAY);
		return localDate;
	}

	/**
	 * 几周后的第一天
	 *
	 * @param week
	 *            参数必须大于0
	 * @return
	 */
	public static LocalDateTime getFirstDayAfterWeek(int week) {
		LocalDateTime localDate = LocalDateTime.now();
		localDate = localDate.plusWeeks(week);
		localDate = localDate.with(DayOfWeek.MONDAY);
		return localDate;
	}

	/**
	 * 几周前的最后一天
	 *
	 * @param week
	 *            参数必须大于0
	 * @return
	 */
	public static LocalDateTime getEndDayBeforeWeek(int week) {
		LocalDateTime localDate = getFirstDayBeforeWeek(week);
		localDate = localDate.plusDays(6);
		return localDate;
	}

	/**
	 * 几周后的最后一天
	 *
	 * @param week
	 *            参数必须大于0
	 * @return
	 */
	public static LocalDateTime getEndDayAfterWeek(int week) {
		LocalDateTime localDate = getEndDayBeforeWeek(week);
		localDate = localDate.plusDays(6);
		return localDate;
	}

	/**
	 * 某个date日期,几周前或后的第一天
	 * 
	 * @param week
	 * @return
	 */
	public static LocalDateTime getFirstDayAssignWeek(Date date, int week) {
		LocalDateTime localDate = getSubtractDate(date, 0, 0, 7 * week);
		localDate = localDate.with(DayOfWeek.MONDAY);
		return localDate;
	}

	/**
	 * 某个date日期,几周前或后的最后一天
	 * 
	 * @param week
	 * @return
	 */
	public static LocalDateTime getEndDayAssignWeek(Date date, int week) {
		LocalDateTime localDate = getFirstDayAssignWeek(date, week);
		localDate = localDate.plusDays(6);
		return localDate;
	}

	/**
	 * 本周第一天
	 * 
	 * @return
	 */
	public static LocalDateTime getFirstDayCurrentWeek() {
		return getFirstDayAssignWeek(new Date(), 0);
	}

	/**
	 * 本周最后一天
	 * 
	 * @return
	 */
	public static LocalDateTime getEndDayCurrentWeek() {
		return getEndDayAssignWeek(new Date(), 0);
	}

	/**
	 * 上一周第一天
	 * 
	 * @return
	 */
	public static LocalDateTime getFirstDayLastWeek() {
		return getFirstDayAssignWeek(new Date(), -1);
	}

	/**
	 * 上一周最后一天
	 * 
	 * @return
	 */
	public static LocalDateTime getEndDayLastWeek() {
		return getEndDayAssignWeek(new Date(), -1);
	}

	/**
	 * 下一周第一天
	 * 
	 * @return
	 */
	public static LocalDateTime getFirstDayNextWeek() {
		return getFirstDayAssignWeek(new Date(), 1);
	}

	/**
	 * 下一周最后一天
	 * 
	 * @return
	 */
	public static LocalDateTime getEndDayNextWeek() {
		return getEndDayAssignWeek(new Date(), 1);
	}

	/**
	 * 与当前年相差Year年的日历
	 * 
	 * @param year
	 * @return
	 */
	public static LocalDateTime getSubtractYear(int year) {
		return getSubtractDate(new Date(), year, 0, 0);
	}

	/**
	 * 与当前月份相差month月的日历
	 * 
	 * @param month
	 * @return
	 */
	public static LocalDateTime getSubtractMonth(int month) {
		return getSubtractDate(new Date(), 0, month, 0);
	}

	/**
	 * 与当前天相差day天的日历
	 * 
	 * @param day
	 * @return
	 */
	public static LocalDateTime getSubtractDay(int day) {
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

	/**
	 * 计算与某个日期差值为year年month月day日 的日历
	 * 
	 * @param date
	 * @param year
	 * @param month
	 * @param dayOfMonth
	 * @return
	 */
	public static LocalDateTime getSubtractDate(Date date, int year, int month, int dayOfMonth) {
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime localDate = instant.atZone(zoneId).toLocalDateTime();
		localDate = localDate.plusYears(year);
		localDate = localDate.plusMonths(month);
		localDate = localDate.plusDays(dayOfMonth);
		return localDate;
	}

	/**
	 * 当前年份增加
	 * 
	 * @param years
	 */
	public static LocalDateTime plusYears(int years) {
		LocalDateTime now = LocalDateTime.now();
		now = now.plusYears(years);
		return now;
	}

	public static LocalDateTime minusYears(int years) {
		LocalDateTime now = LocalDateTime.now();
		now = now.minusYears(years);
		return now;
	}

	/**
	 * 当前月份增加
	 * 
	 * @param months
	 */
	public static LocalDateTime plusMonths(int month) {
		LocalDateTime now = LocalDateTime.now();
		now = now.plusMonths(month);
		return now;
	}

	public static LocalDateTime minusMonths(int month) {
		LocalDateTime now = LocalDateTime.now();
		now = now.minusMonths(month);
		return now;
	}

	/**
	 * 当前天增加
	 * 
	 * @param days
	 */
	public static LocalDateTime plusDays(int days) {
		LocalDateTime now = LocalDateTime.now();
		now = now.plusDays(days);
		return now;
	}

	public static LocalDateTime minusDays(int days) {
		LocalDateTime now = LocalDateTime.now();
		now = now.minusDays(days);
		return now;
	}

	/**
	 * 当前小时增加
	 * 
	 * @param hours
	 */
	public static LocalDateTime plusHours(int hours) {
		LocalDateTime now = LocalDateTime.now();
		now = now.plusHours(hours);
		return now;
	}

	public static LocalDateTime minusHours(int hours) {
		LocalDateTime now = LocalDateTime.now();
		now = now.minusHours(hours);
		return now;
	}

	/**
	 * 当前时间加多少分钟
	 * 
	 * @param minutes
	 */
	public static LocalDateTime plusMinutes(int minutes) {
		LocalDateTime now = LocalDateTime.now();
		now = now.plusMinutes(minutes);
		return now;
	}

	/**
	 * 当前时间减多少分钟
	 * 
	 * @param minutes
	 */
	public static LocalDateTime minusMinutes(int minutes) {
		LocalDateTime now = LocalDateTime.now();
		now = now.minusMinutes(minutes);
		return now;
	}

	/**
	 * 当前时间加多少秒数
	 * 
	 * @param seconds
	 */
	public static LocalDateTime plusSeconds(int seconds) {
		LocalDateTime now = LocalDateTime.now();
		now = now.plusSeconds(seconds);
		return now;
	}

	/**
	 * 当前时间减多少秒数
	 * 
	 * @param seconds
	 */
	public static LocalDateTime minusSeconds(int seconds) {
		LocalDateTime now = LocalDateTime.now();
		now = now.minusSeconds(seconds);
		return now;
	}

	public static void main(String[] args) {
		System.out.println("上周第一天:" + getFirstDayLastWeek());
		System.out.println("上周最后一天:" + getEndDayLastWeek());

		System.out.println("本周第一天:" + getFirstDayCurrentWeek());
		System.out.println("本周最后一天:" + getEndDayCurrentWeek());

		System.out.println("下周第一天:" + getFirstDayNextWeek());
		System.out.println("下周最后一天:" + getEndDayNextWeek());

		System.out.println("-2个月前或后的第一天：" + getFirstDayBeforeMonth(-2));
		System.out.println("-2个月前的最后一天：" + getEndDayBeforeMonth(-2));

		System.out.println("2013年3月的第一天:" + getFirstDay(2013, 2));
		System.out.println("2013年3月的最后一天:" + getEndDay(2013, 2));

		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.YEAR, 2019);
		cl.set(Calendar.MONTH, 0);
		cl.set(Calendar.DAY_OF_MONTH, 1);
		System.out.println(cl.getTime());
		System.out.println("2019年1月1号5周后:" + getFirstDayAssignWeek(cl.getTime(), 1));
	}
}
