package com.gitee.linzl.math;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 支持int , long , double ,String
 * <p>
 * 涉及金钱，要转换为"分"来计算存储
 *
 * @author linzl
 */
public class BigDecimalUtil {
    /**
     * 分转成元
     *
     * @param points 多少分钱
     * @return
     */
    public static String pointsToYuan(int points) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(points / 100.00);
    }

    /**
     * 元转成分
     *
     * @param yuan 多少元
     * @return
     */
    public static BigDecimal yuanTopoints(String yuan) {
        return multiply(yuan, "100");
    }

    /**
     * 按百分比显示数据
     *
     * @param obj
     * @return
     */
    public static String percent(Object obj) {
        DecimalFormat format = new DecimalFormat("0.00%");
        return format.format(obj);
    }

    public static String format(long number, int length) {// 或者是double
        DecimalFormat df = new DecimalFormat("00000000");
        // 补够8位数
        return df.format(number);
    }

    /**
     * 加法
     *
     * @param first
     * @param second
     * @return
     */
    public static BigDecimal add(String first, String second) {
        BigDecimal bdFirst = new BigDecimal(first);
        BigDecimal bdSecond = new BigDecimal(second);
        return bdFirst.add(bdSecond);
    }

    public static BigDecimal add(Double first, Double second) {
        BigDecimal bdFirst = BigDecimal.valueOf(first);
        BigDecimal bdSecond = BigDecimal.valueOf(second);
        return bdFirst.add(bdSecond);
    }

    public static BigDecimal add(String first, String second, int scale) {
        return add(first, second).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal add(Double first, Double second, int scale) {
        return add(first, second).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 减法
     *
     * @return
     */
    public static BigDecimal subtract(String first, String second) {
        BigDecimal bdFirst = new BigDecimal(first);
        BigDecimal bdSecond = new BigDecimal(second);
        return bdFirst.subtract(bdSecond);
    }

    public static BigDecimal subtract(Double first, Double second) {
        BigDecimal bdFirst = BigDecimal.valueOf(first);
        BigDecimal bdSecond = BigDecimal.valueOf(second);
        return bdFirst.subtract(bdSecond);
    }

    /**
     * 减法
     *
     * @param first
     * @param second
     * @param scale  保存几位小数
     * @return
     */
    public static BigDecimal subtract(String first, String second, int scale) {
        return subtract(first, second).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal subtract(Double first, Double second, int scale) {
        return subtract(first, second).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 乘法
     *
     * @return
     */
    public static BigDecimal multiply(String first, String second) {
        BigDecimal bdFirst = new BigDecimal(first);
        BigDecimal bdSecond = new BigDecimal(second);
        return bdFirst.multiply(bdSecond);
    }

    public static BigDecimal multiply(Double first, Double second) {
        BigDecimal bdFirst = BigDecimal.valueOf(first);
        BigDecimal bdSecond = BigDecimal.valueOf(second);
        return bdFirst.multiply(bdSecond);
    }

    public static BigDecimal multiply(String first, String second, int scale) {
        return multiply(first, second).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal multiply(Double first, Double second, int scale) {
        return multiply(first, second).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 除法
     *
     * @return
     */
    public static BigDecimal divide(String first, String second) {
        BigDecimal bdFirst = new BigDecimal(first);
        BigDecimal bdSecond = new BigDecimal(second);
        return bdFirst.divide(bdSecond);
    }

    public static BigDecimal divide(Double first, Double second) {
        BigDecimal bdFirst = BigDecimal.valueOf(first);
        BigDecimal bdSecond = BigDecimal.valueOf(second);
        return bdFirst.divide(bdSecond);
    }

    public static BigDecimal divide(String first, String second, int scale) {
        return multiply(first, second).setScale(scale, BigDecimal.ROUND_CEILING);
    }

    public static BigDecimal divide(Double first, Double second, int scale) {
        return multiply(first, second).setScale(scale, BigDecimal.ROUND_CEILING);
    }


    public static void main(String[] args) {
        System.out.println(pointsToYuan(1101));
        System.out.println(percent(1.092));

        System.out.println(BigDecimalUtil.divide("-27.9", "3.6"));
        DecimalFormat df = new DecimalFormat("#0.00");
        System.out.println(df.format(201 / 100.00));
        System.out.println(divide("201", "100"));
        System.out.println(multiply("201", "100"));
    }
}