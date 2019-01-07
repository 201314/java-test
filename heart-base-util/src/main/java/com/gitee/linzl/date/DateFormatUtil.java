package com.gitee.linzl.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 日期格式化类
 * 
 * @author linzl
 * 
 */
public class DateFormatUtil {
	private static final String YYYY_MM_DD = "yyyy-MM-dd";

	// 格式化SimpleDateFormat实例化 池
	private static Map<String, SimpleDateFormat> map = new ConcurrentHashMap<>();

	/**
	 * 获取SimpleDateFormat
	 * 
	 * @param pattern
	 *            日期格式
	 * @return SimpleDateFormat对象
	 */
	private static SimpleDateFormat getDateFormat(String pattern) {
		if (pattern != null && pattern.trim().length() > 0) {
			if (map.get(pattern) == null) {
				map.putIfAbsent(pattern, new SimpleDateFormat(pattern));
			}
		} else {
			try {
				throw new Exception("日期格式不正确" + pattern);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return (SimpleDateFormat) map.get(pattern);
	}

	/**
	 * 默认使用yyyy-mm-dd格式转换
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, YYYY_MM_DD);
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
		return getDateFormat(pattern).format(date);
	}

	/**
	 * 默认使用yyyy-mm-dd格式转换
	 * 
	 * @param calendar
	 * @return
	 */
	public static String format(Calendar calendar) {
		return format(calendar.getTime(), YYYY_MM_DD);
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
		return getDateFormat(pattern).format(calendar.getTime());
	}

	/**
	 * 字符串转换为日期
	 * 
	 * @param str
	 *            转换的字符串
	 * @param pattern
	 *            转换格式
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String str, String pattern) throws ParseException {
		return getDateFormat(pattern).parse(str);
	}

	/**
	 * 字符串转换为日期,默认yyyy-MM-dd格式
	 * 
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String str) throws ParseException {
		return stringToDate(str, YYYY_MM_DD);
	}

	/**
	 * 字符串转为Timestamp
	 * 
	 * @param str
	 *            格式必须为yyyy-mm-dd hh:mm:ss[.fffffffff],f为可选项
	 * @return
	 */
	public static Timestamp stringToTimestamp(String str) {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		return ts.valueOf(str);
	}

	/**
	 * Timestamp转为字符串
	 * 
	 * @param ts
	 * @return
	 */
	public static String timeStampToString(Timestamp ts, String pattern) {
		return getDateFormat(pattern).format(ts);
	}

	/**
	 * Timestamp转为字符串，默认yyyy-MM-dd格式
	 * 
	 * @param ts
	 * @return
	 */
	public static String timeStampToString(Timestamp ts) {
		return timeStampToString(ts, YYYY_MM_DD);
	}

	/**
	 * Date转Timestamp
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp dateToTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 格式中一些比较特别的,只要你能想象到的格式，就能格式化出来
	 */
	private static void Special() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 E");
		String format = "P" + sdf.format(new Date());// date-->String
		System.out.println("1---->" + format);
		sdf = new SimpleDateFormat("'中国时间:'yyyyMMddHHmmss");
		format = sdf.format(new Date());
		System.out.println("2---->" + format);
	}

	public static void main(String[] args) {
		Special();
		String str = "2012-12-25 12:10:00";
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		ts = ts.valueOf(str);
		System.out.println(DateFormatUtil.timeStampToString(ts));
	}
}