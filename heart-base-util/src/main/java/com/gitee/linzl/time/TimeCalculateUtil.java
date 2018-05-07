package com.gitee.linzl.time;

import org.joda.time.DateTime;

/**
 * 加2个小时
 * 
 * DateTime date = new DateTime();
 * 
 * DateTime future = date.plusSeconds(7200);
 * 
 * DateTimeFormatter formate = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
 * System.out.println(future.toString(formate));
 * 
 * 
 * joda 计算时间差
 * 
 * 主鍵生成器
 * 
 * @author linzl
 */
public class TimeCalculateUtil {

	public static DateTime add(int years, int months, int days, int hours, int minutes, int seconds) {
		DateTime date = DateTime.now();
		return add(date, years, months, days, hours, minutes, seconds);
	}

	public static DateTime minus(int years, int months, int days, int hours, int minutes, int seconds) {
		DateTime date = DateTime.now();
		return minus(date, years, months, days, hours, minutes, seconds);
	}

	public static DateTime add(DateTime date, int years, int months, int days, int hours, int minutes, int seconds) {
		DateTime addYears = date.plusYears(years);
		DateTime addMonths = addYears.plusMonths(months);
		DateTime addDays = addMonths.plusDays(days);
		DateTime addHours = addDays.plusHours(hours);
		DateTime addMinutes = addHours.plusMinutes(minutes);
		DateTime addSeconds = addMinutes.plusSeconds(seconds);
		return addSeconds;
	}

	public static DateTime minus(DateTime date, int years, int months, int days, int hours, int minutes, int seconds) {
		DateTime minusYears = date.minusYears(years);
		DateTime minusMonths = minusYears.minusMonths(months);
		DateTime minusDays = minusMonths.minusDays(days);
		DateTime minusHours = minusDays.minusHours(hours);
		DateTime minusMinutes = minusHours.minusMinutes(minutes);
		DateTime minusSeconds = minusMinutes.minusSeconds(seconds);
		return minusSeconds;
	}

	public static DateTime add(int years, int months, int days) {
		return add(years, months, days, 0, 0, 0);
	}

	public static DateTime minus(int years, int months, int days) {
		return minus(years, months, days, 0, 0, 0);
	}

	/**
	 * 当前年份增加
	 * 
	 * @param years
	 */
	public static DateTime addYears(int years) {
		return add(years, 0, 0, 0, 0, 0);
	}

	public static DateTime minusYears(int years) {
		return minus(years, 0, 0, 0, 0, 0);
	}

	/**
	 * 当前月份增加
	 * 
	 * @param months
	 */
	public static DateTime addMonths(int months) {
		return add(0, months, 0, 0, 0, 0);
	}

	public static DateTime minusMonths(int months) {
		return minus(0, months, 0, 0, 0, 0);
	}

	/**
	 * 当前天增加
	 * 
	 * @param days
	 */
	public static DateTime addDays(int days) {
		return add(0, 0, days, 0, 0, 0);
	}

	public static DateTime minusDays(int days) {
		return minus(0, 0, days, 0, 0, 0);
	}

	/**
	 * 当前小时增加
	 * 
	 * @param hours
	 */
	public static DateTime addHours(int hours) {
		return add(0, 0, 0, hours, 0, 0);
	}

	public static DateTime minusHours(int hours) {
		return minus(0, 0, 0, hours, 0, 0);
	}

	/**
	 * 当前分钟增加
	 * 
	 * @param minutes
	 */
	public static DateTime addMinutes(int minutes) {
		return add(0, 0, 0, 0, minutes, 0);
	}

	public static DateTime minusMinutes(int minutes) {
		return minus(0, 0, 0, 0, minutes, 0);
	}

	/**
	 * 当前秒数增加
	 * 
	 * @param seconds
	 */
	public static DateTime addSeconds(int seconds) {
		return add(0, 0, 0, 0, 0, seconds);
	}

	public static DateTime minusSeconds(int seconds) {
		return minus(0, 0, 0, 0, 0, seconds);
	}

	public static void main(String[] args) {
		DateTime today = new DateTime(2018, 11, 2, 13, 34);
		DateTime date = TimeCalculateUtil.add(today, 1, 1, 2, 3, 5, 0);
		System.out.println(TimeFormatUtil.format("yyyy-MM-dd HH:mm:ss", date));
	}

}