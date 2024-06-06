package com.gitee.linzl.time;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static java.time.temporal.ChronoField.*;
import static java.time.temporal.ChronoUnit.*;

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
        localDate = localDate.with(TemporalAdjusters.firstDayOfMonth());
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
        LocalDateTime localDate = LocalDateTime.now();
        localDate = localDate.withYear(year);
        localDate = localDate.withMonth(month);
        localDate = localDate.with(TemporalAdjusters.lastDayOfMonth());
        return localDate;
    }


    public static LocalDate getFirstDayOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDate getEndDayOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 获取指定日期所在月第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int first = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, first);
        //Instant instant = date.toInstant();
        //instant = instant.truncatedTo(DAYS); 截取到天
        //return Date.from(instant);
        return cal.getTime();
    }

    public static LocalDate getFirstDayOfMonth(LocalDateTime date) {
        return date.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate();
    }

    public static LocalDate getFirstDayOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }


    /**
     * 几个月前或几个月后的第一天
     *
     * @param month 负数表示几个月前，正数表示几个月后
     * @return
     */
    public static LocalDateTime getFirstDayOfMonth(Date date, int month) {
        return getFirstDayOfMonth(toLocalDateTime(date), month);
    }

    public static LocalDateTime getFirstDayOfMonth(LocalDate date, int month) {
        return getFirstDayOfMonth(date.atStartOfDay(), month);
    }

    public static LocalDateTime getFirstDayOfMonth(LocalDateTime date, int month) {
        if (month > 0) {
            return date.with(temporal -> temporal.with(DAY_OF_MONTH, 1).plus(month, MONTHS));
        }
        return date.with(temporal -> temporal.with(DAY_OF_MONTH, 1).minus(-month, MONTHS));
    }

    /**
     * 获取指定日期所在月最后一天
     *
     * @param date
     * @return
     */
    public static Date getEndDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        return cal.getTime();
    }

    /**
     * 几个月前或几个月后的第一天
     *
     * @param month 负数表示几个月前，正数表示几个月后
     * @return
     */
    public static LocalDateTime getEndDayOfMonth(Date date, int month) {
        return getEndDayOfMonth(toLocalDate(date), month);
    }

    public static LocalDateTime getEndDayOfMonth(LocalDate date, int month) {
        return getEndDayOfMonth(date.atStartOfDay(), month);
    }

    public static LocalDateTime getEndDayOfMonth(LocalDateTime date, int month) {
        if (month > 0) {
            return date.with(TemporalAdjusters.lastDayOfMonth()).plusMonths(month);
        }
        return date.with(TemporalAdjusters.lastDayOfMonth()).minusMonths(-month);
    }

    /**
     * 几周前的第一天
     *
     * @param week 参数必须大于0
     * @return
     */
    public static LocalDateTime getFirstDayOfWeek(int week) {
        return getFirstDayOfWeek(LocalDateTime.now(), week);
    }

    /**
     * 某个date日期,几周前的第一天
     *
     * @param week
     * @return
     */
    public static LocalDateTime getFirstDayOfWeek(Date date, int week) {
        return getFirstDayOfWeek(toLocalDateTime(date), week);
    }

    public static LocalDateTime getFirstDayOfWeek(LocalDate date, int week) {
        return getFirstDayOfWeek(date.atStartOfDay(), week);
    }

    public static LocalDateTime getFirstDayOfWeek(LocalDateTime date, int week) {
        if (week > 0) {
            return date.with(TemporalAdjusters.firstDayOfMonth()).plusWeeks(week);
        }
        return date.with(DayOfWeek.MONDAY).minusWeeks(-week);
    }

    /**
     * 几周前的最后一天
     *
     * @param week 参数必须大于0
     * @return
     */
    public static LocalDateTime getEndDayOfWeek(int week) {
        return getEndDayOfWeek(LocalDateTime.now(), week);
    }

    /**
     * 某个date日期,几周前或后的最后一天
     *
     * @param week
     * @return
     */
    public static LocalDateTime getEndDayOfWeek(Date date, int week) {
        return getEndDayOfWeek(toLocalDateTime(date), week);
    }

    public static LocalDateTime getEndDayOfWeek(LocalDate date, int week) {
        return getEndDayOfWeek(date.atStartOfDay(), week);
    }

    public static LocalDateTime getEndDayOfWeek(LocalDateTime date, int week) {
        if (week > 0) {
            return date.with(TemporalAdjusters.firstDayOfMonth()).plusWeeks(week);
        }
        return date.with(DayOfWeek.SUNDAY).minusWeeks(-week);
    }

    /**
     * 指定周几的为一周第1天
     *
     * @param date
     * @param dayOfWeek
     * @return
     */
    public static Date assignFirstDayOfWeek(Date date, DayOfWeek dayOfWeek) {
        LocalDate localDate = toLocalDate(date);
        return Date.from(assignFirstDayOfWeek(localDate, dayOfWeek).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate assignFirstDayOfWeek(LocalDate date, DayOfWeek dayOfWeek) {
        WeekFields weekFields = WeekFields.of(dayOfWeek, 1);
        // 获取当前日期所在周的第一天
        return date.with(weekFields.dayOfWeek(), 1);
    }

    public static LocalDateTime assignFirstDayOfWeek(LocalDateTime date, DayOfWeek dayOfWeek) {
        WeekFields weekFields = WeekFields.of(dayOfWeek, 1);
        // 获取当前日期所在周的第一天
        return date.with(weekFields.dayOfWeek(), 1);
    }


    public static Date assignEndDayOfWeek(Date date, DayOfWeek dayOfWeek) {
        LocalDate localDate = toLocalDate(date);
        return Date.from(assignEndDayOfWeek(localDate, dayOfWeek).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate assignEndDayOfWeek(LocalDate date, DayOfWeek dayOfWeek) {
        WeekFields weekFields = WeekFields.of(dayOfWeek, 1);
        // 获取当前日期所在周的最后一天
        return date.with(weekFields.dayOfWeek(), 7);
    }

    public static LocalDateTime assignEndDayOfWeek(LocalDateTime date, DayOfWeek dayOfWeek) {
        WeekFields weekFields = WeekFields.of(dayOfWeek, 1);
        // 获取当前日期所在周的最后一天
        return date.with(weekFields.dayOfWeek(), 7);
    }

    public static String getWeekYear(Date date) {
        return getWeekYear(toLocalDate(date));
    }

    public static String getWeekYear(LocalDate date) {
        int year = date.getYear();

        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekOfYear = date.get(weekFields.weekOfYear());
        String weekYear = StringUtils.join(String.valueOf(year),
                StringUtils.leftPad(String.valueOf(weekOfYear), 2, '0'));
        return weekYear;
    }

    public static Date minTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    public static Date maxTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }


    public static LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    public static LocalDate toLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }


    /**
     * 当前年份增加
     *
     * @param year
     */
    public static LocalDateTime plusYears(int year) {
        //LocalDate.now().plusYears(week);
        //LocalDate.now().plus(1,ChronoUnit.YEARS);
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
        //LocalDate.now().plusDays(day);
        //LocalDate.now().plus(1,ChronoUnit.DAYS);
        return LocalDateTime.now().plusDays(day);
    }

    public static LocalDateTime plusWeeks(int week) {
        //LocalDate.now().plusWeeks(week);
        //LocalDate.now().plus(1,ChronoUnit.WEEKS);
        return LocalDateTime.now().plusWeeks(week);
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
        return ChronoUnit.MILLIS.between(startTime, endTime);
    }

    /**
     * 计算时差
     */
    public static Duration compare(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime);
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

    public static Date toDate(String text, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date toDate(final LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(final LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Calendar toCalendar(final LocalDateTime localDateTime) {
        return GregorianCalendar.from(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()));
    }

    public static Calendar toCalendar(final LocalDate localDate) {
        return GregorianCalendar.from(ZonedDateTime.of(localDate, LocalTime.MIDNIGHT, ZoneId.systemDefault()));
    }

    public static LocalDate toLocalDateTime(String text, String pattern) {
        return LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime parse2LocalDateTime(String text, String pattern) {
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    public static long getTime(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static long getTime(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDate toLocalDate(final long milliseconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(final long milliseconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault());
    }

    public static LocalDateTime toLocalDateTime(LocalDate date) {
        /** 0点 **/
        return date.atStartOfDay();
    }

    public static LocalDateTime noonTime(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.NOON);
    }

    public static Date noonTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * @param date 2018-11-11 转换成 2018-11-11 00:00
     * @return
     */
    public static LocalDateTime toMinLocalDateTime(Date date) {
        return minTime(toLocalDate(date));
    }

    /**
     * @param date 2018-11-11 转换成 2018-11-11 00:00
     * @return
     */
    public static LocalDateTime minTime(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MIN);
    }

    /**
     * @param date 2018-11-11 转换成 2018-11-11 23:59:59.999999999
     * @return
     */
    public static LocalDateTime toMaxLocalDateTime(Date date) {
        // LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return maxTime(toLocalDate(date));
    }

    /**
     * @param date 2018-11-11 转换成 2018-11-11 23:59:59.999999999
     * @return
     */
    public static LocalDateTime maxTime(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MAX);
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

    public static boolean checkToday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        LocalDate now = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        return checkToday(now);
    }

    public static boolean checkToday(LocalDate date) {
        return LocalDate.now().equals(date);
    }

    public static boolean checkToday(LocalDateTime date) {
        return checkToday(date.toLocalDate());
    }

    public static boolean checkCurrentMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        YearMonth yearMonth = YearMonth.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
        YearMonth now = YearMonth.now();
        return now.equals(yearMonth);
    }

    public static boolean checkCurrentMonth(LocalDateTime date) {
        return checkCurrentMonth(date.toLocalDate());
    }

    public static boolean checkCurrentMonth(LocalDate date) {
        YearMonth yearMonth = YearMonth.from(date);
        YearMonth now = YearMonth.now();
        return now.equals(yearMonth);
    }

    /**
     * 例：生日检查
     *
     * @param birthday
     */
    public static boolean checkBirth(LocalDate birthday) {
        LocalDate now = LocalDate.now();
        MonthDay currentMonthDay = MonthDay.of(now.getMonth(), now.getDayOfMonth());
        MonthDay birth = MonthDay.from(birthday);
        return currentMonthDay.equals(birth);
    }

    public static void main(String[] args) {
        System.out.println(minTime(new Date()));
        /*System.out.println("上周第一天:" + getFirstDayLastWeek());
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
        System.out.println("时差:" + (todayEnd.getTimeInMillis() - System.currentTimeMillis()));

        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        System.out.println("时差:" + (compare(startTime, endTime).toMillis()));

        System.out.println(getEndDayNextMonth());
        System.out.println(checkCurrentMonth(LocalDateTime.of(2019, 12, 1, 0, 0)));*/

        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        // 获取当前一周的第一天
        DayOfWeek firstDayOfWeek = weekFields.getFirstDayOfWeek();

        // 打印当前一周的第一天
        System.out.println("当前一周的第一天是: " + firstDayOfWeek);

        // 设置一周的第一天为星期一
        WeekFields modifiedWeekFields = weekFields.of(DayOfWeek.FRIDAY, 7);
        // 打印当前一周的第一天
        System.out.println("当前一周的第一天11是: " + modifiedWeekFields.getFirstDayOfWeek());

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2024);
        cal.set(Calendar.MONTH, Calendar.APRIL);
        cal.set(Calendar.DAY_OF_MONTH, 26);
        System.out.println("年龄:" + getAge(cal.getTime()));

        // 如果输入日期为周一到周四,返回上周五的日期
        // 如果输入日期为周一到周四,返回本周四的日期
        Date date = cal.getTime();
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();

        // 设置一周的第一天为星期一
        weekFields = weekFields.of(DayOfWeek.FRIDAY, 7);

        // 获取当前日期所在周的第一天
        LocalDate firstDayOfWeekDate = localDate.with(weekFields.dayOfWeek(), firstDayOfWeek.getValue());
        System.out.println(firstDayOfWeekDate);

       /* LocalDate pre5 = null;
        if (1<=val && val<=4){
            pre5 = localDate.minusWeeks(1).with(DayOfWeek.FRIDAY);
            System.out.println(pre5);
        } else {
            pre5 = localDate.with(DayOfWeek.FRIDAY);
            System.out.println(pre5);
        }

        LocalDate pre4 = null;
        if (1<=val && val<=4){
            pre4 = localDate.minusWeeks(1).with(DayOfWeek.FRIDAY);
            System.out.println(pre4);
        } else {
            pre4 = localDate.with(DayOfWeek.FRIDAY);
            System.out.println(pre4);
        }

        localDate = localDate.with(DayOfWeek.THURSDAY);
        System.out.println(localDate);*/

        System.out.println(getFirstDayOfMonth(new Date()));
    }

    /**
     * 获取年龄，周岁
     *
     * @param birthday
     * @return
     */
    public static int getAge(Date birthday) {
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthday);

        int birthYear = birth.get(Calendar.YEAR);
        int birthMonth = birth.get(Calendar.MONTH) + 1;
        int birthDay = birth.get(Calendar.DAY_OF_MONTH);

        Calendar now = Calendar.getInstance();
        int nowYear = now.get(Calendar.YEAR);
        int nowMonth = now.get(Calendar.MONTH) + 1;
        int nowDay = now.get(Calendar.DAY_OF_MONTH);

        int age = nowYear - birthYear;
        int month = nowMonth - birthMonth;
        int day = nowDay - birthDay;
        /**
         * 还未到生日当月减1岁 || 生日当月,还未到生日+1天 减1岁
         */
        if (month < 0 || (month == 0 && day <= 0)) {
            age = age - 1;
        }
        return age <= 0 ? 0 : age;
    }

    /**
     * 失效检查，如信用卡过期
     *
     * @return
     */
    public boolean expireCheck() {
        YearMonth current = YearMonth.now();
        YearMonth credit = YearMonth.of(2019, Month.FEBRUARY);
        return current.equals(credit);
    }

    public void test() {
        // UTC 时钟
        Clock.systemUTC();
        // 系统时钟
        Clock.systemDefaultZone();

        ZoneId america = ZoneId.of("America/New_York");
        ZonedDateTime dateAndTimeInNewYork = ZonedDateTime.of(LocalDateTime.now(), america);
        //将当时时间转为纽约时间
        System.out.println(dateAndTimeInNewYork);
        //闰年检查
        LocalDate.now().isLeapYear();
        //当前时间戳
        Instant.now();

        //时间差
        LocalDate java8Release = LocalDate.of(2018, 12, 14);
        //Period 类表示一段时间的年、月、日和周
        Period period = Period.between(LocalDate.now(), java8Release);
    }
}
