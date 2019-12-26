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
import java.time.temporal.ChronoUnit;
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
     * @param month 参数必须大于0
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
     * @param month 参数必须大于0
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
     * @param week 参数必须大于0
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
     * @param week 参数必须大于0
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
     * @param month 参数必须大于0
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
     * @param week 参数必须大于0
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
     * @param week 参数必须大于0
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
     * @param year
     */
    public static LocalDateTime plusYears(int year) {
        return LocalDateTime.now().plusYears(year);
    }

    /**
     * 当前月份增加
     *
     * @param month
     */
    public static LocalDateTime plusMonths(int month) {
        return LocalDateTime.now().plusMonths(month);
    }

    /**
     * 当前天增加
     *
     * @param day
     */
    public static LocalDateTime plusDays(int day) {
        return LocalDateTime.now().plusDays(day);
    }

    /**
     * 当前小时增加
     *
     * @param hour
     */
    public static LocalDateTime plusHours(int hour) {
        return LocalDateTime.now().plusHours(hour);
    }

    /**
     * 当前时间加多少分钟
     *
     * @param minute
     */
    public static LocalDateTime plusMinutes(int minute) {
        return LocalDateTime.now().plusMinutes(minute);
    }

    /**
     * 当前时间加多少秒数
     *
     * @param second
     */
    public static LocalDateTime plusSeconds(int second) {
        return LocalDateTime.now().plusSeconds(second);
    }

    public static LocalDateTime minusYears(int year) {
        return LocalDateTime.now().minusYears(year);
    }

    public static LocalDateTime minusMonths(int month) {
        return LocalDateTime.now().minusMonths(month);
    }

    public static LocalDateTime minusDays(int day) {
        return LocalDateTime.now().minusDays(day);
    }

    public static LocalDateTime minusHours(int hour) {
        return LocalDateTime.now().minusHours(hour);
    }

    /**
     * 当前时间减多少分钟
     *
     * @param minute
     */
    public static LocalDateTime minusMinutes(int minute) {
        return LocalDateTime.now().minusMinutes(minute);
    }

    /**
     * 当前时间减多少秒数
     *
     * @param second
     */
    public static LocalDateTime minusSeconds(int second) {
        return LocalDateTime.now().minusSeconds(second);
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
        //return compare(startTime, endTime).toDays();
        //return startTime.until(endTime, ChronoUnit.DAYS);
        return ChronoUnit.DAYS.between(startTime, endTime);
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
        //return compare(startTime, endTime).toHours();
        //return startTime.until(endTime, ChronoUnit.HOURS);
        return ChronoUnit.HOURS.between(startTime, endTime);
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
        //return compare(startTime, endTime).toMinutes();
        //return startTime.until(endTime, ChronoUnit.MINUTES);
        return ChronoUnit.MINUTES.between(startTime, endTime);
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
        //return compare(startTime, endTime).getSeconds();
        //return startTime.until(endTime, ChronoUnit.SECONDS);
        return ChronoUnit.SECONDS.between(startTime, endTime);
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
        //return compare(startTime, endTime).toMillis();
        //return startTime.until(endTime, ChronoUnit.MILLIS);
        return ChronoUnit.MILLIS.between(startTime, endTime);
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

    public static Date toDate(String text, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
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

    public static LocalDate toLocalDateTime(String text, String pattern) {
        return LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime parse2LocalDateTime(String text, String pattern) {
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
    }


    public static LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    public static LocalDateTime toLocalDateTime(LocalDate date) {
        /** 0点 **/
        return date.atStartOfDay();
    }

    public static LocalDateTime minTime(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MIN);
    }

    public static Date minTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        return cal.getTime();
    }

    public static Date noonTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR, 12);
        cal.set(Calendar.MINUTE, 0);
        return cal.getTime();
    }

    public static Date maxTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999999999);
        return cal.getTime();
    }


    /**
     * @param date 2018-11-11 转换成 2018-11-11 00:00
     * @return
     */
    public static LocalDateTime toMinLocalDateTime(Date date) {
        return toMinLocalDateTime(toLocalDate(date));
    }

    /**
     * @param date 2018-11-11 转换成 2018-11-11 00:00
     * @return
     */
    public static LocalDateTime toMinLocalDateTime(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MIN);
    }

    /**
     * @param date 2018-11-11 转换成 2018-11-11 23:59:59.999999999
     * @return
     */
    public static LocalDateTime toMaxLocalDateTime(Date date) {
        return toMaxLocalDateTime(toLocalDate(date));
    }

    /**
     * @param date 2018-11-11 转换成 2018-11-11 23:59:59.999999999
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

    public static boolean before(Date before, Date after) {
        return before.before(after);
    }

    public static boolean before(LocalDateTime before, LocalDateTime after) {
        return before.isBefore(after);
    }

    public static boolean before(LocalDateTime before, Date after) {
        return before.isBefore(toLocalDateTime(after));
    }

    public static boolean before(Date before, LocalDateTime after) {
        return toLocalDateTime(before).isBefore(after);
    }

    public static boolean after(Date before, Date after) {
        return before.after(after);
    }

    public static boolean after(LocalDateTime before, LocalDateTime after) {
        return before.isAfter(after);
    }

    public static boolean after(LocalDateTime before, Date after) {
        return before.isAfter(toLocalDateTime(after));
    }

    public static boolean after(Date before, LocalDateTime after) {
        return toLocalDateTime(before).isAfter(after);
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

        Calendar todayEnd = Calendar.getInstance();
        // Calendar.HOUR 12小时制
        // HOUR_OF_DAY 24小时制
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        System.out.println("时差:" + (todayEnd.getTimeInMillis() - new Date().getTime()));

        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        System.out.println("时差:" + (compare(startTime, endTime).toMillis()));
    }
}
