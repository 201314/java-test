package com.gitee.linzl.ext;

import com.gitee.linzl.codec.ConvertUtil;
import com.gitee.linzl.util.StringUtil;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 主鍵生成器
 *
 * @author linzl
 */
public class KeyGeneratorUtil {
    /**
     * 主键生成器使用这个做为ID
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    public static String uuidHex() {
        return ConvertUtil.toHex(uuid());
    }

    public static String timstampId() {
        return String.valueOf(Calendar.getInstance().getTimeInMillis());
    }

    public static long randomId() {
        return ThreadLocalRandom.current().nextLong();
    }

    /**
     * 自增ID,不能含有特殊字符
     *
     * @param src
     * @return
     */
    public static String autoIncrease(String src) {
        return StringUtil.incrementAlphanumeric(src);
    }

    public static String createRuleIdPrefix() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        //今天是第多少天 0补位操作 必须满足三位
        String dayFmt = String.format("%1$03d", day);
        // 小时：0补位操作 必须满足2位
        String hourFmt = String.format("%1$02d", hour);
        String minuteFmt = String.format("%1$02d", minute);
        String secondFmt = String.format("%1$02d", second);

        StringBuffer prefix = new StringBuffer();
        prefix.append(year - 2000)
                .append(dayFmt)
                .append(hourFmt)
                .append(minuteFmt)
                .append(secondFmt);
        return prefix.toString();
    }


    public static void main(String[] args) throws Exception {
        InetAddress ip = InetAddress.getLocalHost();
        System.out.println(ip.getHostAddress());
        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
        byte[] mac = network.getHardwareAddress();
        System.out.println(ConvertUtil.toHex(mac));

    }
}