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
        /**
         * 浮点型的计算，不建议直接使用BigDecimal(double val)
         * 因为如果传入0.1,打印的结果会是0.1000000000000000055511151231257827021181583404541015625
         */
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
        System.out.println(BigDecimalUtil.divide("-27.9", "3.6"));
        DecimalFormat df = new DecimalFormat("#0.00");
        System.out.println(df.format(201 / 100.00));
        System.out.println(divide("201", "100"));
        System.out.println(multiply("201", "100"));
    }
}