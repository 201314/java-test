package com.gitee.linzl.math;

import java.util.Date;
import java.util.Objects;

public class ObjectUtil {
    /**
     * 判断是否空值,空值默认0返回
     *
     * @param number
     * @return
     */
    public static Long defaultIfNull(Long number) {
        return defaultIfNull(number, 0L);
    }

    public static Integer defaultIfNull(Integer number) {
        return defaultIfNull(number, 0);
    }

    public static Date defaultIfNull(Date date) {
        return defaultIfNull(date, new Date());
    }


    /**
     * 判断是否空值
     *
     * @param number
     * @param defaultVal 空值，使用默认值返回
     * @return
     */
    public static Integer defaultIfNull(Integer number, Integer defaultVal) {
        return Objects.nonNull(number) ? number : defaultVal;
    }

    public static Long defaultIfNull(Long number, Long defaultVal) {
        return Objects.nonNull(number) ? number : defaultVal;
    }

    public static Date defaultIfNull(Date date, Date defaultVal) {
        return Objects.nonNull(date) ? date : defaultVal;
    }

}
