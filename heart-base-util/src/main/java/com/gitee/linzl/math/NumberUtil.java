package com.gitee.linzl.math;

import java.util.Objects;

public class NumberUtil {
    /**
     * 判断是否空值
     *
     * @param number
     * @param defaultVal 空值，使用默认值返回
     * @return
     */
    public static Long nullToDefault(Long number, Long defaultVal) {
        return Objects.nonNull(number) ? number : defaultVal;
    }

    /**
     * 判断是否空值,空值默认0返回
     *
     * @param number
     * @return
     */
    public static Long nullToZero(Long number) {
        return nullToDefault(number, 0L);
    }

}
