package com.gitee.linzl.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
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
		// localDate = localDate.withDayOfMonth(1);
		localDate = localDate.with(TemporalAdjusters.firstDayOfMonth());
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
		// localDate = localDate.withDayOfMonth(1);
		localDate = localDate.with(TemporalAdjusters.firstDayOfMonth());
		return localDate;
	}

	/**
	 * 几个月后的第一天
	 *
	 * @param month
	 *            参数必须大于0
	 * @return
	 */
	public static LocalDateTime getFirstDayAfterMonth(int month) {
		LocalDateTime localDate = LocalDateTime.now();
		localDate = localDate.plusMonths(month);
		// localDate = localDate.withDayOfMonth(1);
		localDate = localDate.with(TemporalAdjusters.firstDayOfMonth());
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
		return getFirstDayBeforeWeek(LocalDateTime.now(), week);
	}

	/**
	 * 某个date日期,几周前的第一天
	 * 
	 * @param week
	 * @return
	 */
	public static LocalDateTime getFirstDayBeforeWeek(Date date, int week) {
		LocalDateTime localDate = toLocalDateTime(date);
		return getFirstDayBeforeWeek(localDate, week);
	}

	public static LocalDateTime getFirstDayBeforeWeek(LocalDateTime date, int week) {
		date = date.minusWeeks(week);
		date = date.with(DayOfWeek.MONDAY);
		return date;
	}

	/**
	 * 几周后的第一天
	 *
	 * @param week
	 *            参数必须大于0
	 * @return
	 */
	public static LocalDateTime getFirstDayAfterWeek(int week) {
		return getFirstDayAfterWeek(LocalDateTime.now(), week);
	}

	/**
	 * 某个date日期,几周后的第一天
	 * 
	 * @param week
	 * @return
	 */
	public static LocalDateTime getFirstDayAfterWeek(Date date, int week) {
		LocalDateTime localDate = toLocalDateTime(date);
		return getFirstDayAfterWeek(localDate, week);
	}

	public static LocalDateTime getFirstDayAfterWeek(LocalDateTime date, int week) {
		date = date.plusWeeks(week);
		date = date.with(DayOfWeek.MONDAY);
		return date;
	}

	/**
	 * 上一周第一天
	 * 
	 * @return
	 */
	public static LocalDateTime getFirstDayLastWeek() {
		return getFirstDayBeforeWeek(LocalDateTime.now(), 1);
	}

	/**
	 * 本周第一天
	 * 
	 * @return
	 */
	public static LocalDateTime getFirstDayCurrentWeek() {
		return getFirstDayAfterWeek(LocalDateTime.now(), 0);
	}

	/**
	 * 下一周第一天
	 * 
	 * @return
	 */
	public static LocalDateTime getFirstDayNextWeek() {
		return getFirstDayAfterWeek(LocalDateTime.now(), 1);
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
		localDate = localDate.with(TemporalAdjusters.lastDayOfMonth());
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
		localDate = localDate.minusMonths(month);
		localDate = localDate.with(TemporalAdjusters.lastDayOfMonth());
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
		localDate = localDate.with(TemporalAdjusters.lastDayOfMonth());
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
		return getEndDayBeforeWeek(LocalDateTime.now(), week);
	}

	/**
	 * 某个date日期,几周前或后的最后一天
	 * 
	 * @param week
	 * @return
	 */
	public static LocalDateTime getEndDayBeforeWeek(Date date, int week) {
		LocalDateTime localDate = toLocalDateTime(date);
		return getEndDayBeforeWeek(localDate, week);
	}

	public static LocalDateTime getEndDayBeforeWeek(LocalDateTime date, int week) {
		date = date.minusWeeks(week);
		date = date.with(DayOfWeek.SUNDAY);
		return date;
	}

	/**
	 * 几周后的最后一天
	 *
	 * @param week
	 *            参数必须大于0
	 * @return
	 */
	public static LocalDateTime getEndDayAfterWeek(int week) {
		return getEndDayAfterWeek(LocalDateTime.now(), week);
	}

	/**
	 * 某个date日期,几周前或后的最后一天
	 * 
	 * @param week
	 * @return
	 */
	public static LocalDateTime getEndDayAfterWeek(Date date, int week) {
		LocalDateTime localDate = toLocalDateTime(date);
		return getEndDayAfterWeek(localDate, week);
	}

	public static LocalDateTime getEndDayAfterWeek(LocalDateTime date, int week) {
		date = date.plusWeeks(week);
		date = date.with(DayOfWeek.SUNDAY);
		return date;
	}

	/**
	 * 上一周最后一天
	 * 
	 * @return
	 */
	public static LocalDateTime getEndDayLastWeek() {
		return getEndDayBeforeWeek(LocalDateTime.now(), 1);
	}

	/**
	 * 本周最后一天
	 * 
	 * @return
	 */
	public static LocalDateTime getEndDayCurrentWeek() {
		return getEndDayAfterWeek(LocalDateTime.now(), 0);
	}

	/**
	 * 下一周最后一天
	 * 
	 * @return
	 */
	public static LocalDateTime getEndDayNextWeek() {
		return getEndDayAfterWeek(LocalDateTime.now(), 1);
	}

	/**
	 * 当前年份增加
	 * 
	 * @param years
	 */
	public static LocalDateTime plusYears(int years) {
		return LocalDateTime.now().plusYears(years);
	}

	/**
	 * 当前月份增加
	 * 
	 * @param months
	 */
	public static LocalDateTime plusMonths(int month) {
		return LocalDateTime.now().plusMonths(month);
	}

	/**
	 * 当前天增加
	 * 
	 * @param days
	 */
	public static LocalDateTime plusDays(int days) {
		return LocalDateTime.now().plusDays(days);
	}

	/**
	 * 当前小时增加
	 * 
	 * @param hours
	 */
	public static LocalDateTime plusHours(int hours) {
		return LocalDateTime.now().plusHours(hours);
	}

	/**
	 * 当前时间加多少分钟
	 * 
	 * @param minutes
	 */
	public static LocalDateTime plusMinutes(int minutes) {
		return LocalDateTime.now().plusMinutes(minutes);
	}

	/**
	 * 当前时间加多少秒数
	 * 
	 * @param seconds
	 */
	public static LocalDateTime plusSeconds(int seconds) {
		return LocalDateTime.now().plusSeconds(seconds);
	}

	public static LocalDateTime minusYears(int years) {
		return LocalDateTime.now().minusYears(years);
	}

	public static LocalDateTime minusMonths(int month) {
		return LocalDateTime.now().minusMonths(month);
	}

	public static LocalDateTime minusDays(int days) {
		return LocalDateTime.now().minusDays(days);
	}

	public static LocalDateTime minusHours(int hours) {
		return LocalDateTime.now().minusHours(hours);
	}

	/**
	 * 当前时间减多少分钟
	 * 
	 * @param minutes
	 */
	public static LocalDateTime minusMinutes(int minutes) {
		return LocalDateTime.now().minusMinutes(minutes);
	}

	/**
	 * 当前时间减多少秒数
	 * 
	 * @param seconds
	 */
	public static LocalDateTime minusSeconds(int seconds) {
		return LocalDateTime.now().minusSeconds(seconds);
	}

	/**
	 * 计算两个日期时间差（天）
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static long days(Date first, Date second) {
		long subtract = milliSeconds(first, second);
		return subtract / (24 * 60 * 60 * 1000);
	}

	/**
	 * 计算时差天数
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long days(LocalDateTime startTime, LocalDateTime endTime) {
		return compare(startTime, endTime).toDays();
	}

	/**
	 * 计算两个日期时间差（小时）
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static long hours(Date first, Date second) {
		long subtract = milliSeconds(first, second);
		return subtract / (1 * 60 * 60 * 1000);
	}

	public static long hours(LocalDateTime startTime, LocalDateTime endTime) {
		return compare(startTime, endTime).toHours();
	}

	/**
	 * 计算两个日期时间差（分钟）
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static long minutes(Date first, Date second) {
		long subtract = milliSeconds(first, second);
		return subtract / (1 * 60 * 1000);
	}

	public static long minutes(LocalDateTime startTime, LocalDateTime endTime) {
		return compare(startTime, endTime).toMinutes();
	}

	/**
	 * 计算两个日期时间差（秒）
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static long seconds(Date first, Date second) {
		long subtract = milliSeconds(first, second);
		return subtract / 1000;
	}

	/**
	 * 计算时差秒数
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long seconds(LocalDateTime startTime, LocalDateTime endTime) {
		return compare(startTime, endTime).getSeconds();
	}

	/**
	 * 计算两个日期时间差（毫秒） （差值转化为指定格式的差值 ，如 转化为多少秒，转化为多少时 , 多少月，多少年，多少周） 比较常用
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static long milliSeconds(Date first, Date second) {
		long firstTime = first.getTime();
		long secondTime = second.getTime();
		long subTime = firstTime - secondTime;
		return subTime > 0 ? subTime : -subTime;
	}

	public static long milliSeconds(LocalDateTime startTime, LocalDateTime endTime) {
		return compare(startTime, endTime).toMillis();
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
	public static LocalDateTime subtract(Date date, int year, int month, int dayOfMonth) {
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime localDate = instant.atZone(zoneId).toLocalDateTime();
		localDate = localDate.plusYears(year);
		localDate = localDate.plusMonths(month);
		localDate = localDate.plusDays(dayOfMonth);
		return localDate;
	}

	/**
	 * 计算时差
	 */
	public static Duration compare(LocalDateTime startTime, LocalDateTime endTime) {
		return Duration.between(startTime, endTime);
	}

	public static Date parse2Date(String text, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(text);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static LocalDate parse2LocalDate(String text, String pattern) {
		return LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern));
	}

	public static LocalDateTime parse2LocalDateTime(String text, String pattern) {
		return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
	}

	public static Date toDate(LocalDateTime localDateTime) {
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zdt = localDateTime.atZone(zoneId);
		return Date.from(zdt.toInstant());
	}

	public static Date toDate(LocalDate localDate) {
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
		return Date.from(zdt.toInstant());
	}

	public static LocalDateTime toLocalDateTime(Date date) {
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		return instant.atZone(zoneId).toLocalDateTime();
	}

	public static LocalDateTime toLocalDateTime(LocalDate date) {
		return date.atStartOfDay();
	}

	/**
	 * 
	 * @param date
	 *            2018-11-11 转换成 2018-11-11 00:00
	 * @return
	 */
	public static LocalDateTime toMinLocalDateTime(Date date) {
		return toMinLocalDateTime(toLocalDate(date));
	}

	/**
	 * 
	 * @param date
	 *            2018-11-11 转换成 2018-11-11 00:00
	 * @return
	 */
	public static LocalDateTime toMinLocalDateTime(LocalDate date) {
		return LocalDateTime.of(date, LocalTime.MIN);
	}

	/**
	 * 
	 * @param date
	 *            2018-11-11 转换成 2018-11-11 23:59:59.999999999
	 * @return
	 */
	public static LocalDateTime toMaxLocalDateTime(Date date) {
		return toMaxLocalDateTime(toLocalDate(date));
	}

	/**
	 * 
	 * @param date
	 *            2018-11-11 转换成 2018-11-11 23:59:59.999999999
	 * @return
	 */
	public static LocalDateTime toMaxLocalDateTime(LocalDate date) {
		return LocalDateTime.of(date, LocalTime.MAX);
	}

	public static LocalDate toLocalDate(Date date) {
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		return instant.atZone(zoneId).toLocalDate();
	}

	public static LocalTime toLocalTime(Date date) {
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		return instant.atZone(zoneId).toLocalTime();
	}

	public static void main(String[] args) {
		System.out.println("上周第一天:" + getFirstDayLastWeek());
		System.out.println("上周最后一天:" + getEndDayLastWeek());

		System.out.println("本周第一天:" + getFirstDayCurrentWeek());
		System.out.println("本周最后一天:" + getEndDayCurrentWeek());

		System.out.println("下周第一天:" + getFirstDayNextWeek());
		System.out.println("下周最后一天:" + getEndDayNextWeek());

		System.out.println("2个月前的第一天：" + getFirstDayBeforeMonth(2));
		System.out.println("2个月前的最后一天：" + getEndDayBeforeMonth(2));

		System.out.println("2013年2月的第一天:" + getFirstDay(2013, 2));
		System.out.println("2013年2月的最后一天:" + getEndDay(2013, 2));

		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.YEAR, 2019);
		cl.set(Calendar.MONTH, 0);
		cl.set(Calendar.DAY_OF_MONTH, 1);
		System.out.println(cl.getTime());
		System.out.println("2019年1月1号5周后的第一天:" + getFirstDayAfterWeek(cl.getTime(), 5));
	}
}
