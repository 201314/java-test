package com.gitee.linzl.cron;

import java.util.Arrays;

/**
 * cron表达式生成工具类,在线检测http://cron.qqe2.com/
 *
 * Support for specifying both a day-of-week AND a day-of-month parameter is not implemented.
 * 周和日期不能同时指定，只能指定一个，另外一个为?
 *
 * @author linzl
 * @description
 * @email 2225010489@qq.com
 * @date 2018年11月1日
 */

public class CronTriggerHelper {
    private String cron = "%s %s %s %s %s %s";
    private String secondCron = "*";
    private String minuteCron = "*";
    private String hourCron = "*";
    private String dayCron = "?";
    private String monthCron = "*";
    private String weekCron = "?";
    private String yearCron = "*";

    private CronTriggerHelper() {

    }

    public static CronTriggerHelper getInstance() {
        return CronTriggerHolder.INSTANCE;
    }


    /**
     * 指定天数执行,最多指定31个数
     *
     * @param days 1~31
     * @throws Exception
     */
    public CronTriggerHelper assignDay(int[] days) throws Exception {
        if (days == null || days.length == 0) {
            return this;
        }
        if (days.length > 31) {
            throw new Exception("指定天数的数组超过31长度");
        }

        dayCron = extracted(days);
        return this;
    }

    /**
     * 指定小时执行,最多指定24个数
     *
     * @param hours 0~23
     * @throws Exception
     */
    public CronTriggerHelper assignHour(int[] hours) throws Exception {
        if (hours == null || hours.length == 0) {
            return this;
        }
        if (hours.length > 24) {
            throw new Exception("指定小时的数组超过24长度");
        }

        hourCron = extracted(hours);
        return this;
    }

    /**
     * 指定分钟执行,最多指定60个数
     *
     * @param minutes 0~59
     * @throws Exception
     */
    public CronTriggerHelper assignMinute(int[] minutes) throws Exception {
        if (minutes == null || minutes.length == 0) {
            return this;
        }
        if (minutes.length > 60) {
            throw new Exception("指定分钟的数组超过60长度");
        }

        minuteCron = extracted(minutes);
        return this;
    }

    /**
     * 指定月份数执行,最多指定12个数
     *
     * @param months 1~12
     * @throws Exception
     */
    public CronTriggerHelper assignMonth(int[] months) throws Exception {
        if (months == null || months.length == 0) {
            return this;
        }
        if (months.length > 12) {
            throw new Exception("指定月份数的数组超过12长度");
        }

        monthCron = extracted(months);
        return this;
    }

    /**
     * 指定秒数执行,最多指定60个数
     *
     * @param seconds 0~59
     * @throws Exception
     */
    public CronTriggerHelper assignSecond(int[] seconds) throws Exception {
        if (seconds == null || seconds.length == 0) {
            return this;
        }
        if (seconds.length > 60) {
            throw new Exception("指定秒数的数组超过60长度");
        }

        secondCron = extracted(seconds);
        return this;
    }

    /**
     * 指定星期几执行,最多指定7个数
     *
     * @param weekDays 1~7
     * @throws Exception
     */
    public CronTriggerHelper assignWeek(int[] weekDays) throws Exception {
        if (weekDays == null || weekDays.length == 0) {
            return this;
        }
        if (weekDays.length > 7) {
            throw new Exception("指定星期几的数组超过7长度");
        }

        weekCron = extracted(weekDays);
        return this;
    }

    /**
     * 在时间段周期执行
     *
     * @param startDay 开始小时 1~31
     * @param endDay   结束小时 1~31
     * @throws Exception
     */
    public CronTriggerHelper cycleDay(int startDay, int endDay) throws Exception {
        if (startDay < 0 || startDay > 31) {
            throw new Exception("周期执行:startDay必须在[1,31]范围");
        }
        if (endDay < 0 || endDay > 31) {
            throw new Exception("周期执行:endDay必须在[1,31]范围");
        }
        if (startDay >= endDay) {
            throw new Exception("周期执行:startDay必须小于endDay");
        }
        dayCron = startDay + "-" + endDay;
        return this;
    }


    /**
     * 在时间段周期执行
     *
     * @param startHour 开始小时 0~23
     * @param endHour   结束小时 0~23
     * @throws Exception
     */
    public CronTriggerHelper cycleHour(int startHour, int endHour) throws Exception {
        if (startHour < 0 || startHour > 23) {
            throw new Exception("周期执行:startHour必须在[0,23]范围");
        }
        if (endHour < 0 || endHour > 23) {
            throw new Exception("周期执行:endHour必须在[0,23]范围");
        }
        if (startHour >= endHour) {
            throw new Exception("周期执行:startHour必须小于endHour");
        }
        hourCron = startHour + "-" + endHour;
        return this;
    }

    /**
     * 在时间段周期执行
     *
     * @param startMinute 开始秒数 1~59
     * @param endMinute   结束秒数1~59
     * @throws Exception
     */
    public CronTriggerHelper cycleMinute(int startMinute, int endMinute) throws Exception {
        if (startMinute < 1 || startMinute > 59) {
            throw new Exception("周期执行:startMinute必须在[1,59]范围");
        }
        if (endMinute < 1 || endMinute > 59) {
            throw new Exception("周期执行:endMinute必须在[1,59]范围");
        }
        if (startMinute >= endMinute) {
            throw new Exception("周期执行:startMinute必须小于endMinute");
        }
        minuteCron = startMinute + "-" + endMinute;
        return this;
    }

    /**
     * 在时间段周期执行
     *
     * @param startMonth 开始秒数 1~12
     * @param endMonth   结束秒数1~12
     * @throws Exception
     */
    public CronTriggerHelper cycleMonth(int startMonth, int endMonth) throws Exception {
        if (startMonth < 1 || startMonth > 12) {
            throw new Exception("周期执行:startMonth必须在[1,59]范围");
        }
        if (endMonth < 1 || endMonth > 12) {
            throw new Exception("周期执行:endMonth必须在[1,59]范围");
        }
        if (startMonth >= endMonth) {
            throw new Exception("周期执行:startMonth必须小于endMonth");
        }
        monthCron = startMonth + "-" + endMonth;
        return this;
    }

    /**
     * 在时间段周期执行
     *
     * @param startSecond 开始秒数 1~59
     * @param endSecond   结束秒数1~59
     * @throws Exception
     */
    public CronTriggerHelper cycleSecond(int startSecond, int endSecond) throws Exception {
        if (startSecond < 1 || startSecond > 59) {
            throw new Exception("周期执行:startSecond必须在[1,59]范围");
        }
        if (endSecond < 1 || endSecond > 59) {
            throw new Exception("周期执行:endSecond必须在[1,59]范围");
        }
        if (startSecond >= endSecond) {
            throw new Exception("周期执行:startSecond必须小于endSecond");
        }
        secondCron = startSecond + "-" + endSecond;
        return this;
    }

    /**
     * 在时间段周期执行
     *
     * @param startWeek 开始秒数 1~7
     * @param endWeek   结束秒数1~7
     * @throws Exception
     */
    public CronTriggerHelper cycleWeek(int startWeek, int endWeek) throws Exception {
        if (startWeek < 1 || startWeek > 7) {
            throw new Exception("周期执行:startWeek必须在[1,7]范围");
        }
        if (endWeek < 1 || endWeek > 7) {
            throw new Exception("周期执行:endWeek必须在[1,7]范围");
        }
        if (startWeek >= endWeek) {
            throw new Exception("周期执行:startWeek必须小于endWeek");
        }
        weekCron = startWeek + "/" + endWeek;
        return this;
    }

    /**
     * 第week周的星期day
     *
     * @param week
     * @param day
     * @return
     */
    public CronTriggerHelper dayOfWeek(int week, int day) {
        if (week < 1 || week > 4) {
            return this;
        }
        if (day < 1 || day > 7) {
            return this;
        }
        weekCron = week + "#" + day;
        return this;
    }

    /**
     * 每月最后一天
     */
    public CronTriggerHelper endDay() {
        dayCron = "L";
        return this;
    }

    /**
     * 本月最后一个星期几
     *
     * @param weekDay
     */
    public CronTriggerHelper endWeekDayOfMonth(int weekDay) {
        if (weekDay < 1 || weekDay > 7) {
            return this;
        }
        weekCron = weekDay + "L";
        return this;
    }

    private String extracted(int[] time) {
        Arrays.sort(time);
        StringBuilder sb = new StringBuilder();
        for (int assignIdx = 0, total = time.length; assignIdx < total; assignIdx++) {
            sb.append(time[assignIdx]);
            if (assignIdx < total - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public String generateCron() {
        return String.format(cron, secondCron, minuteCron, hourCron, dayCron, monthCron,
                weekCron, yearCron);
    }

    /**
     * 指定几号后最近的工作日(包含当日)
     *
     * @param day
     */
    public CronTriggerHelper latelyWeekDay(int day) {
        if (day < 1 || day > 31) {
            return this;
        }
        dayCron = day + "W";
        return this;
    }

    public CronTriggerHelper repeatExecuteDay(int perDay) throws Exception {
        repeatExecuteDay(1, perDay);
        return this;
    }

    /**
     * 从 startDay 秒开始,每 perHour 秒执行一次
     *
     * @param startDay 1~31
     * @param perHour  1~31
     */
    public CronTriggerHelper repeatExecuteDay(int startDay, int perDay) throws Exception {
        if (startDay < 1 || startDay > 31) {
            throw new Exception("重复执行:startDay必须在[1,31]范围");
        }

        if (perDay < 1 || perDay > 31) {
            throw new Exception("重复执行:startDay必须在[1,31]范围");
        }

        if (startDay > perDay) {
            throw new Exception("周期执行:startDay必须小于perDay");
        }

        int totalDay = startDay + perDay;
        if (totalDay < 2 || totalDay > 31) {
            throw new Exception("重复执行:startDay+perDay之和必须在[2,31]范围才有意义");
        }
        dayCron = startDay + "/" + perDay;
        return this;
    }

    public CronTriggerHelper repeatExecuteHour(int perHour) throws Exception {
        repeatExecuteHour(0, perHour);
        return this;
    }

    /**
     * 从 startHour 秒开始,每 perHour 秒执行一次
     *
     * @param startHour 0~23
     * @param perHour   1~23
     */
    public CronTriggerHelper repeatExecuteHour(int startHour, int perHour) throws Exception {
        if (startHour < 0 || startHour > 23) {
            throw new Exception("重复执行:startHour必须在[0,23]范围");
        }

        if (perHour < 1 || perHour > 23) {
            throw new Exception("重复执行:perHour必须在[1,23]范围");
        }

        if (startHour > perHour) {
            throw new Exception("周期执行:startHour必须小于perHour");
        }

        int totalHour = startHour + perHour;
        if (totalHour >= 24) {
            throw new Exception("重复执行:startHour+perHour之和必须在[1,24]范围才有意义");
        }
        hourCron = startHour + "/" + perHour;
        return this;
    }


    public CronTriggerHelper repeatExecuteMinute(int perMinute) throws Exception {
        repeatExecuteMinute(0, perMinute);
        return this;
    }

    /**
     * 从 startMinute 秒开始,每 perMinute 秒执行一次
     *
     * @param startMinute 0~59
     * @param perSecond   1~59
     */
    public CronTriggerHelper repeatExecuteMinute(int startMinute, int perMinute) throws Exception {
        if (startMinute < 0 || startMinute > 59) {
            throw new Exception("重复执行:startMinute必须在[0,59]范围");
        }

        if (perMinute < 1 || perMinute > 59) {
            throw new Exception("重复执行:perMinute必须在[1,59]范围");
        }

        if (startMinute > perMinute) {
            throw new Exception("周期执行:startMinute必须小于perMinute");
        }

        int totalMinute = startMinute + perMinute;
        if (totalMinute >= 60) {
            throw new Exception("重复执行:startMinute+perMinute之和必须在[1,60]范围才有意义");
        }

        minuteCron = startMinute + "/" + perMinute;
        return this;
    }

    public CronTriggerHelper repeatExecuteMonth(int perMonth) throws Exception {
        repeatExecuteMonth(1, perMonth);
        return this;
    }

    /**
     * 从 startMonth 秒开始,每 perMonth 秒执行一次
     *
     * @param startMonth 1~12
     * @param perMonth   1~12
     */
    public CronTriggerHelper repeatExecuteMonth(int startMonth, int perMonth) throws Exception {
        if (startMonth < 1 || startMonth > 12) {
            throw new Exception("重复执行:startMonth必须在[1,12]范围");
        }

        if (perMonth < 1 || perMonth > 12) {
            throw new Exception("重复执行:perMonth必须在[1,12]范围");
        }

        if (startMonth > perMonth) {
            throw new Exception("周期执行:startMonth必须小于perMonth");
        }

        int totalMonth = startMonth + perMonth;
        if (totalMonth > 12) {
            throw new Exception("重复执行:startMonth+perMonth之和必须在[2,12]范围才有意义");
        }
        monthCron = startMonth + "/" + perMonth;
        return this;
    }


    public CronTriggerHelper repeatExecuteSecond(int perSecond) throws Exception {
        repeatExecuteSecond(0, perSecond);
        return this;
    }

    /**
     * 从 startSecond 秒开始,每 perSecond 秒执行一次
     *
     * @param startSecond 0~59
     * @param perSecond   1~59
     */
    public CronTriggerHelper repeatExecuteSecond(int startSecond, int perSecond) throws Exception {
        if (startSecond < 0 || startSecond > 59) {
            throw new Exception("重复执行:startSecond必须在[0,59]范围");
        }

        if (perSecond < 1 || perSecond > 59) {
            throw new Exception("重复执行:perSecond必须在[1,59]范围");
        }

        if (startSecond > perSecond) {
            throw new Exception("周期执行:startSecond必须小于perSecond");
        }

        int totalSecond = startSecond + perSecond;
        if (totalSecond >= 60) {
            throw new Exception("重复执行:startSecond+perSecond之和必须在[1,60]范围才有意义");
        }
        secondCron = startSecond + "/" + perSecond;
        return this;
    }


    /**
     * 1、加载一个类时，其内部类不会同时被加载
     * <p>
     * 2、一个类加载，当且仅当其某个静态成员（静态域，构造器，静态方法等）被调用时发生
     */
    static class CronTriggerHolder {
        // 由于对象实例化是在内部类加载的时候去创建的，因此是线程安全的。
        // 因为在方法中创建对象，才存在并发问题，静态内部类随着方法调用而被加载，只加载一次，并不存在并发问题，所以是线程安全的。
        private static final CronTriggerHelper INSTANCE = new CronTriggerHelper();
    }
}
