package com.gitee.linzl.math;

import com.gitee.linzl.codec.ConvertUtil;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 金额工具
 *
 * @author linzhenlie
 * @date 2019/9/17
 */
public class MoneyUtil {
    static Map<Integer, String> map = new HashMap<>(10);
    /**
     * 整数部分
     */
    static Map<Integer, String> integralPart = new HashMap<>(4);
    /**
     * 小数部分
     */
    static Map<Integer, String> fractionalPart = new HashMap<>(2);

    static {
        map.put(0, "零");
        map.put(1, "壹");
        map.put(2, "贰");
        map.put(3, "叁");
        map.put(4, "肆");
        map.put(5, "伍");
        map.put(6, "陆");
        map.put(7, "柒");
        map.put(8, "捌");
        map.put(9, "玖");

        integralPart.put(0, "元");
        integralPart.put(1, "拾");
        integralPart.put(2, "佰");
        integralPart.put(3, "仟");
        integralPart.put(4, "万");
        integralPart.put(5, "拾");
        integralPart.put(6, "佰");
        integralPart.put(7, "仟");
        integralPart.put(8, "亿");
        integralPart.put(9, "拾");
        integralPart.put(10, "佰");
        integralPart.put(11, "仟");
        integralPart.put(12, "兆");
        integralPart.put(13, "拾");
        integralPart.put(14, "佰");
        integralPart.put(15, "仟");

        fractionalPart.put(0, "角");
        fractionalPart.put(1, "分");
        fractionalPart.put(2, "厘");
    }

//分、角、元、拾、佰、仟、万、亿、兆

    /**
     * 分转成元
     *
     * @param points 多少分钱
     * @return
     */
    public static String toYuan(int points) {
        DecimalFormat df = new DecimalFormat("#0.00元");
        return df.format(points / 100.00);
    }

    /**
     * 元转成分
     *
     * @param yuan 多少元
     * @return
     */
    public static int toPoints(String yuan) {
        return BigDecimalUtil.multiply(yuan, "100").intValue();
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

    /**
     * 将number在高位补足到length长度
     *
     * @param number
     * @param length
     * @return
     */
    public static String repairLength(Long number, int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = length; i > 0; i--) {
            sb.append(0);
        }

        DecimalFormat df = new DecimalFormat(sb.toString());
        return df.format(number);
    }

    /**
     * 超过maxLength时，采用简写方式
     * 如1155555,超过6位，采用W表达==>115.56W(四舍五入，小数点保留2位)
     *
     * @param number
     * @param maxLength
     * @return
     */
    public static String abbreviation(Double number, int maxLength) {
        String str = ConvertUtil.double2String(number);
        if (str.length() <= maxLength) {
            return str;
        }
        //超过maxLength,采用简写
        DecimalFormat df = new DecimalFormat("#0.00W");
        return df.format(number / 10000);
    }

    /**
     * 金额转中文大写
     *
     * @param number
     * @return
     */
    public static String numberConvertCapital(double number) {
        String str = ConvertUtil.double2String(number);

        int dotIdx = str.indexOf(".");
        String integral = dotIdx > -1 ? str.substring(0, dotIdx) : str;

        StringBuffer sb = new StringBuffer();

        //整数部分
        int numLength = integral.length();
        // 从个位起，每4个数划分一部分
        int section = (numLength + (4 - 1)) / 4;
        // 每4个数划为一部分，标记序号
        int sectionIdx = 0;
        while (sectionIdx < section) {
            int startIdx = numLength - 4 * (sectionIdx + 1);
            startIdx = startIdx > 0 ? startIdx : 0;

            int endInx = numLength - 4 * sectionIdx;

            String subStr = str.substring(startIdx, endInx);
            String result = integralCapital(Integer.parseInt(subStr), sectionIdx, true, section);

            sb.insert(0, result);
            sectionIdx++;
        }

        //小数部分 0.1,0.02
        if (dotIdx > -1) {
            sb.append(fractionalCapital(str.substring(dotIdx + 1)));
        } else {
            sb.append("整");
        }
        return sb.toString();
    }

    /**
     * 转换小数部分,最多2位小数
     *
     * @return
     */
    private static String fractionalCapital(String fractionalStr) {
        if (Objects.isNull(fractionalStr) || (Integer.parseInt(fractionalStr)) == 0) {
            return "";
        }

        StringBuffer sb = new StringBuffer();

        String[] arr = fractionalStr.split("");
        String jiao = arr[0];
        if (Objects.nonNull(jiao)) {
            Integer num = Integer.parseInt(jiao);
            sb.append(map.get(num));
            if (num != 0) {
                sb.append(fractionalPart.get(0));
            }
        }

        String fen = arr[1];
        if (Objects.nonNull(fen)) {
            Integer num = Integer.parseInt(fen);
            sb.append(map.get(num));
            if (num != 0) {
                sb.append(fractionalPart.get(1));
            }
        }
        return sb.toString();
    }

    /**
     * 每4位转大写，所有数字和单位都显示出来
     *
     * @param number
     * @param sectionIdx   从右数起第几个4位
     * @param rmSerialZero 是否将连续的零置成一个零
     * @return
     */
    private static String integralCapital(int number, int sectionIdx, boolean rmSerialZero, int section) {
        if (number == 0L) {
            //个十百千为零时的判断
            if (sectionIdx == 0 && section > 0) {
                // 0元
                if (section == 1) {
                    return map.get(number) + integralPart.get(sectionIdx);
                } else {
                    // ***0000元
                    return integralPart.get(sectionIdx);
                }
            }
            // 1亿元 中间的0
            return "";
        }

        String result = integralCapital(number, sectionIdx, rmSerialZero);
        // 不足4位前补零,每4位划分为一部分，总共不超过1，不用补零
        if (String.valueOf(number).length() < 4 && sectionIdx < (section - 1)) {
            return "零" + result;
        }
        return result;
    }

    private static String integralCapital(int number, int sectionIdx, boolean rmSerialZero) {
        StringBuffer sb = new StringBuffer(integralPart.get(sectionIdx * 4));
        int rest;
        int uIdx = 0;
        boolean curZero;
        boolean preZero = false;
        while (number > 0) {
            rest = number % 10;

            //连续几个0
            curZero = rmSerialZero && rest == 0;

            //当超过1个零时，显示一个0
            //不允许出现这样 ***零元   ***零万
            if (!curZero && preZero && !integralPart.values().contains(String.valueOf(sb.charAt(0)))) {
                sb.insert(0, "零");
            }

            //插入此部分的单位
            if (rest > 0 || !rmSerialZero) {
                sb.insert(0, integralPart.getOrDefault(uIdx + sectionIdx * 4, ""));
                sb.insert(0, map.get(rest));
            }

            preZero = curZero;

            uIdx++;
            number = number / 10;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        double number = 1000.00;
        number = 1000.10;
        number = 1000.09;
        //number = 1000_0000_0000d;
        //number = 1020_3060_0040d;
        number = 20_0060;
        // number = 100_0300;
        //number = 8000_0000;
        //number = 700_0000;
        // number = 60_0000;
        // number = 5_0000;
        // number = 4000;
        // number = 300;
        //number = 20;
        //number = 0;
        // number = 1002;
        //number = 1200;
        //number = 2356789.9845;
        System.out.println(number + ":" + numberConvertCapital(number));
        System.out.println("1155555:" + abbreviation(1155555d, 6));
    }
}
