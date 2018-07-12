package com.gitee.linzl.time;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.ocpsoft.prettytime.PrettyTime;

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
public class TimeFormatUtil {

	public static String YMDHMS = "yyyyMMddHHmmss";
	public static String LINE_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public static String DIVIDE_YMDHMS = "yyyy/MM/dd HH:mm:ss";

	public static String YMDHM = "yyyyMMddHHmm";
	public static String LINE_YMDHM = "yyyy-MM-dd HH:mm";
	public static String DIVIDE_YMDHM = "yyyy/MM/dd HH:mm";

	public static String YMDH = "yyyyMMddHH";
	public static String LINE_YMDH = "yyyy-MM-dd HH";
	public static String DIVIDE_YMDH = "yyyy/MM/dd HH";

	public static String YMD = "yyyyMMdd";
	public static String LINE_YMD = "yyyy-MM-dd";
	public static String LINE_YMD_E = "yyyy-MM-dd E"; // "yyyy-MM-dd
														// E"; E 表示星期几
	public static String DIVIDE_YMD = "yyyy/MM/dd";

	public static String YM = "yyyyMM";
	public static String LINE_YM = "yyyy-MM";
	public static String DIVIDE_YM = "yyyy/MM";

	public static String YEAR = "yyyy";

	/**
	 * 获取当前时间,默认到毫秒级
	 * 
	 * @return yyyyMMddHHmmss
	 */
	public static String getDefaultTime() {
		return format(YMDHMS);
	}

	/**
	 * 获取当前时间,格式化到分
	 * 
	 * @return yyyyMMddHHmm
	 */
	public static String getYMDHMTime() {
		return format(YMDHM);
	}

	/**
	 * 获取当前时间,格式化到小时
	 * 
	 * @return yyyyMMddHH
	 */
	public static String getYMDHTime() {
		return format(YMDH);
	}

	/**
	 * 获取当前年月日
	 * 
	 * @return yyyyMM
	 */
	public static String getYMDTime() {
		return format(YMD);
	}

	/**
	 * 获取当前年月
	 * 
	 * @return yyyyMM
	 */
	public static String getYMTime() {
		return format(YM);
	}

	/**
	 * 获取当前年
	 * 
	 * @return yyyy
	 */
	public static String getYearTime() {
		return format(YEAR);
	}

	/**
	 * 指定格式，格式化当前时间
	 * 
	 * @param formatter
	 *            格式化
	 * @return
	 */
	public static String format(String formatter) {
		return format(formatter, DateTime.now());
	}

	/**
	 * 指定格式，格式化当前时间
	 * 
	 * @param formatter
	 *            格式化
	 * @return
	 */
	public static String format(DateTime time) {
		return format(LINE_YMDHMS, time);
	}

	/**
	 * 指定格式，格式化指定时间
	 * 
	 * @param formatter
	 * @param time
	 * @return
	 */
	public static String format(String formatter, DateTime time) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(formatter);
		return time.toString(fmt);
	}

	/**
	 * 格式化时间
	 * 
	 * @param formatter
	 *            格式化
	 * @return
	 */
	public static String format(Date date) {
		return format(LINE_YMDHMS, date);
	}

	/**
	 * 指定格式，格式化指定时间
	 * 
	 * @param formatter
	 * @param date
	 * @return
	 */
	public static String format(String formatter, Date date) {
		DateTime time = new DateTime(date);
		DateTimeFormatter fmt = DateTimeFormat.forPattern(formatter);
		return time.toString(fmt);
	}

	/**
	 * 格式化时间
	 * 
	 * @param formatter
	 *            格式化
	 * @return
	 */
	public static String format(Calendar cal) {
		return format(LINE_YMDHMS, cal);
	}

	/**
	 * 指定格式，格式化指定时间
	 * 
	 * @param formatter
	 * @param cal
	 * @return
	 */
	public static String format(String formatter, Calendar cal) {
		DateTime time = new DateTime(cal.getTimeInMillis());
		DateTimeFormatter fmt = DateTimeFormat.forPattern(formatter);
		return time.toString(fmt);
	}

	/**
	 * 美化当前时间 如显示为 1小时前 2分钟前
	 *
	 * @return
	 */
	public static final String prettyFormat(Date date) {
		PrettyTime p = new PrettyTime();
		return p.format(date);
	}

	/**
	 * 美化当前毫秒时间
	 * 
	 * @param millisecond
	 *            毫秒
	 * @return
	 */
	public static final String prettyFormat(long millisecond) {
		PrettyTime p = new PrettyTime();
		return p.format(new Date(millisecond));
	}

	/**
	 * 显示秒值为**年**月**天 **时**分**秒 如1年2个月3天 10小时
	 * 
	 * @param totalSeconds
	 *            多少秒
	 * @return
	 */
	public static final String prettyFormat(int totalSeconds) {
		StringBuilder s = new StringBuilder();
		int second = totalSeconds % 60;
		if (totalSeconds > 0) {
			s.insert(0, "秒");
			s.insert(0, String.valueOf(second));
		}

		totalSeconds = totalSeconds / 60;
		int minute = totalSeconds % 60;
		if (totalSeconds > 0) {
			s.insert(0, "分");
			s.insert(0, String.valueOf(minute));
		}

		totalSeconds = totalSeconds / 60;
		int hour = totalSeconds % 24;
		if (totalSeconds > 0) {
			s.insert(0, "小时");
			s.insert(0, String.valueOf(hour));
		}

		totalSeconds = totalSeconds / 24;
		int day = totalSeconds % 31;
		if (totalSeconds > 0) {
			s.insert(0, "天");
			s.insert(0, String.valueOf(day));
		}

		totalSeconds = totalSeconds / 31;
		int month = totalSeconds % 12;
		if (totalSeconds > 0) {
			s.insert(0, "月");
			s.insert(0, String.valueOf(month));
		}

		totalSeconds = totalSeconds / 12;
		int year = totalSeconds;
		if (totalSeconds > 0) {
			s.insert(0, "年");
			s.insert(0, String.valueOf(year));
		}
		return s.toString();
	}

	public static void main(String[] args) {
		DateTime date = new DateTime();
		date = date.plusHours(1);
		System.out.println(format("yyyy-MM-dd HH:mm:ss", date));

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, 3);
		System.out.println(format(LINE_YMD_E, cal));

	}

}