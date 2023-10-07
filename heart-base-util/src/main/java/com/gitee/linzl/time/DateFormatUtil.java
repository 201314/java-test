package com.gitee.linzl.time;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.ocpsoft.prettytime.PrettyTime;

/**
 * 日期格式化类
 * 
 * @author linzl
 */
public class DateFormatUtil {
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	public static String format() {
		return format(YYYY_MM_DD_HH_MM_SS);
	}

	public static String format(String pattern) {
		return format(LocalDateTime.now(), pattern);
	}

	/**
	 * 默认使用yyyy-mm-dd格式转换
	 * 
	 * @param calendar
	 * @return
	 */
	public static String format(Calendar calendar) {
		return format(calendar.getTime(), YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 指定格式转换日历
	 * 
	 * @param calendar
	 *            转换日历
	 * @param pattern
	 *            转换格式
	 * @return
	 */
	public static String format(Calendar calendar, String pattern) {
		return format(calendar.getTime(), pattern);
	}

	/**
	 * 默认使用yyyy-mm-dd格式转换
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 指定格式转换日期
	 * 
	 * @param date
	 *            转换日期
	 * @param pattern
	 *            转换格式
	 * @return
	 */
	public static String format(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	public static String format(LocalDate date) {
		return format(date, YYYY_MM_DD_HH_MM_SS);
	}

	public static String format(LocalDate date, String pattern) {
		return date.format(DateTimeFormatter.ofPattern(pattern));
	}

	public static String format(LocalDateTime date) {
		return format(date, YYYY_MM_DD_HH_MM_SS);
	}

	public static String format(LocalDateTime date, String pattern) {
		return DateTimeFormatter.ofPattern(pattern).format(date);
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
}