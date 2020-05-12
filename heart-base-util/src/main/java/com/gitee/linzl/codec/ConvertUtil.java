package com.gitee.linzl.codec;

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

import org.bouncycastle.util.encoders.Hex;

/**
 * byte 1字节
 * <p>
 * char 2字节（C语言中是1字节）可以存储一个汉字
 * <p>
 * short 2字节
 * <p>
 * int 4字节
 * <p>
 * long 8字节
 * <p>
 * float 4字节
 * <p>
 * double 8字节
 *
 * @author linzl
 * @description
 * @email 2225010489@qq.com
 * @date 2018年4月24日
 */
public class ConvertUtil {
    /**
     * 合并两个byte数组
     *
     * @param firstByte
     * @param secondByte
     * @return
     */
    public static byte[] mergeBytes(byte[] firstByte, byte[] secondByte) {
        byte[] mergeByte = new byte[firstByte.length + secondByte.length];
        System.arraycopy(firstByte, 0, mergeByte, 0, firstByte.length);
        System.arraycopy(secondByte, 0, mergeByte, firstByte.length, secondByte.length);
        return mergeByte;
    }

    public static byte[] mergeByte(byte[] firstByte, byte[] secondByte) {
        ByteBuffer target = ByteBuffer.allocate(firstByte.length + secondByte.length);
        target.put(firstByte);
        target.put(secondByte);
        return target.array();
    }

    /**
     * 循环冗余校验,计算CRC16校验码
     *
     * @param data 字节数组
     * @return 校验码
     */
    public static long crc16(byte[] data) {
        long CRC = 0xFFFF;
        for (int i = 0; i < data.length; i++) {
            CRC ^= (data[i] & 0x00FF);// 遇到负数，转为正数
            for (int j = 0; j < 8; j++) {
                if ((CRC & 0x0001) != 0) {
                    CRC >>= 1;
                    CRC ^= 0xA001;
                } else {
                    CRC >>= 1;
                }
            }
        }
        return CRC;
    }

    /**
     * 循环冗余校验,计算CRC16校验码 (Modbus)
     *
     * @param data 字节数组
     * @return 校验码
     */
    public static String crc16ToHex(byte[] data) {
        return Long.toHexString(crc16(data)).toUpperCase();
    }

    /**
     * 循环冗余校验,计算CRC32校验码
     *
     * @param data
     * @return
     */
    public static long crc32(byte[] data) {
        CRC32 crc = new CRC32();
        crc.update(data);
        return crc.getValue();
    }

    public static long crc32(File file) {
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file));) {
            return crc32(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long crc32(InputStream inputStream) {
        CRC32 crc = new CRC32();
        try {
            byte[] bytes = new byte[1024];
            int cnt;
            while ((cnt = inputStream.read(bytes)) != -1) {
                // 读到最后，可能没有1024字节
                crc.update(bytes, 0, cnt);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return crc.getValue();
    }

    /**
     * 计算CRC32校验码 (Modbus)
     *
     * @param data 字节数组
     * @return 校验码
     */
    public static String crc32ToHex(byte[] data) {
        return Long.toHexString(crc32(data)).toUpperCase();
    }

    public static String crc32ToHex(File file) {
        return Long.toHexString(crc32(file)).toUpperCase();
    }

    public static String crc32ToHex(InputStream inputStream) {
        return Long.toHexString(crc32(inputStream)).toUpperCase();
    }

    public static String fullFormatHex(short value) {
        return fullFormatHex(toHex(value));
    }

    public static String fullFormatHex(char value) {
        return fullFormatHex(toHex(value));
    }

    /**
     * 补全为16进制数
     *
     * @param value
     * @return
     */
    public static String fullFormatHex(int value) {
        return fullFormatHex(toHex(value));
    }

    public static String fullFormatHex(long value) {
        return fullFormatHex(toHex(value));
    }

    public static String fullFormatHex(float value) {
        return fullFormatHex(toHex(value));
    }

    public static String fullFormatHex(double value) {
        return fullFormatHex(toHex(value));
    }

    /**
     * 显示完整格式的16进制,占2个字节
     *
     * @param data <p>
     *             OxFF --> Ox00FF
     *             <p>
     *             FF --> 00FF
     * @return
     */
    public static String fullFormatHex(String data) {
        int zeroNum = 4 - data.length();
        if (data.startsWith("0x") || data.startsWith("0X")) {
            zeroNum = 6 - data.length();
            data = data.substring(0, 2) + String.format("%0" + zeroNum + "d", 0) + data.substring(2);
        } else if (zeroNum > 0) {
            data = String.format("%0" + zeroNum + "d", 0) + data;
        }
        return data.toUpperCase();
    }

    public static byte[] char2Byte(char value) {
        return toByteExt((short) value);
    }

    public static byte[] toByte(char value) {
        return ByteBuffer.allocate(2).putChar(value).array();
    }

    public static byte[] toByteExt(short value) {
        byte[] result = new byte[2];
        // 由高位到低位
        result[0] = (byte) ((value >> 8)& 0xFF);
        result[1] = (byte) (value & 0xFF);
        return result;
    }

    public static byte[] toByte(short value) {
        return ByteBuffer.allocate(2).putShort(value).array();
    }

    /**
     * 转 字节
     *
     * @param value 58165-->16进制为E335
     * @return
     */
    public static byte[] toByteExt(int value) {
        byte[] result = new byte[4];
        // 由高位到低位
        result[0] = (byte) ((value >> 24)& 0xFF);
        result[1] = (byte) ((value >> 16)& 0xFF);
        result[2] = (byte) ((value >> 8)& 0xFF);
        result[3] = (byte) (value & 0xFF);
        return result;
    }

    public static byte[] toByte(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    public static byte[] toByteExt(long value) {
        byte[] byteNum = new byte[8];
        for (int ix = 0; ix < 8; ++ix) {
            int offset = 64 - (ix + 1) * 8;
            byteNum[ix] = (byte) ((value >> offset)& 0xFF);
        }
        return byteNum;
    }

    public static byte[] toByte(long value) {
        return ByteBuffer.allocate(8).putLong(value).array();
    }

    public static byte[] toByteExt(float value) {
        return toByteExt(Float.floatToIntBits(value));
    }

    public static byte[] toByte(float value) {
        return ByteBuffer.allocate(4).putFloat(value).array();
    }

    public static byte[] toByteExt(double value) {
        return toByteExt(Double.doubleToLongBits(value));
    }

    public static byte[] toByte(double value) {
        return ByteBuffer.allocate(8).putDouble(value).array();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hex
     * @return
     */
    public static byte[] hex2Byte(String hex) {
        if (hex.length() < 1) {
            return null;
        }
        if (hex.length() % 2 != 0) {
            // 字节数组长度不是偶数直接抛出异常不予处理
            throw new IllegalArgumentException("The byte Array's length is not even!");
        }

        byte[] result = new byte[hex.length() / 2];
        String item;
        for (int n = 0, length = hex.length(); n < length; n += 2) {
            item = new String(hex.getBytes(), n, 2);
            result[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return result;
    }

    /**
     * 二进制转字节
     *
     * @param bin
     * @return
     */
    public static byte bin2Byte(String bin) {
        return 0;
    }

    public static byte[] string2Byte(String value) {
        return value.getBytes();
    }

    public static char toCharExt(byte[] value) {
        return (char) toShortExt(value);
    }

    public static char toChar(byte[] value) {
        return ByteBuffer.wrap(value).getChar();
    }

    public static char toChar(short value) {
        return (char) value;
    }

    public static char toChar(int value) {
        return (char) value;
    }

    public static char toChar(long value) {
        return (char) value;
    }

    public static char toChar(float value) {
        return (char) value;
    }

    public static char toChar(double value) {
        return (char) value;
    }

    /**
     * 二进制转char Ascii
     *
     * @param bin
     * @return
     */
    public static char toCharExt(String bin) {
        return 0;
    }

    public static char[] toChar(String value) {
        return value.toCharArray();
    }

    public static short toShortExt(byte[] value) {
        return (short) (((value[0] & 0xFF) << 8) | value[1] & 0xFF);
    }

    public static short toShort(byte[] value) {
        return ByteBuffer.wrap(value).getShort();
    }

    public static short toShort(byte value) {
        return value;
    }

    public static short toShort(char value) {
        return (short) value;
    }

    public static short toShort(int value) {
        return (short) value;
    }

    public static short toShort(long value) {
        return (short) value;
    }

    public static short toShort(float value) {
        return (short) value;
    }

    public static short toShort(double value) {
        return (short) value;
    }

    public static short toShort(String bin) {
        return Short.parseShort(bin, 2);
    }

    public static int toIntExt(byte[] value) {
        byte[] a = new byte[4];
        int i = a.length - 1, j = value.length - 1;
        for (; i >= 0; i--, j--) {// 从b的尾部(即int值的低位)开始copy数据
            if (j >= 0) {
                a[i] = value[j];
            } else {
                a[i] = 0;// 如果b.length不足4,则将高位补0
            }
        }
        return (a[0] & 0xFF) << 24 | (a[1] & 0xFF) << 16

                | (a[2] & 0xFF) << 8 | a[3] & 0xFF;
    }

    public static int toInt(byte[] value) {
        return ByteBuffer.wrap(value).getInt();
    }

    public static int toInt(char value) {
        return value;
    }

    public static int toInt(short value) {
        return value;
    }

    public static int toInt(long value) {
        return (int) value;
    }

    public static int toInt(float value) {
        return (int) value;
    }

    public static int toInt(double value) {
        return (int) value;
    }

    /**
     * 二进制转Int
     *
     * @param bin
     * @return
     */
    public static int toInt(String bin) {
        return Integer.parseInt(bin, 2);
    }

    /**
     * 16进制转int
     *
     * @param hex
     * @return
     */
    public static int hex2Int(String hex) {
        BigInteger bigInteger = new BigInteger(hex, 16);
        return bigInteger.intValue();
    }

    public static long toLongExt(byte[] value) {
        byte[] a = new byte[8];
        int i = a.length - 1, j = value.length - 1;
        for (; i >= 0; i--, j--) {// 从b的尾部(即int值的低位)开始copy数据
            if (j >= 0) {
                a[i] = value[j];
            } else {
                a[i] = 0;// 如果b.length不足4,则将高位补0
            }
        }

        return ((((long) a[0] & 0xFF) << 56) | (((long) a[1] & 0xFF) << 48)

                | (((long) a[2] & 0xFF) << 40) | (((long) a[3] & 0xFF) << 32)

                | (((long) a[4] & 0xFF) << 24) | (((long) a[5] & 0xFF) << 16)

                | (((long) a[6] & 0xFF) << 8) | (((long) a[7] & 0xFF) << 0));
    }

    public static long toLong(byte[] value) {
        if (value.length < 8) {// 位数不够,前补0
            byte[] target = new byte[8];
            int fillZero = target.length - value.length;
            Arrays.fill(target, 0, fillZero, (byte) 0);
            System.arraycopy(value, 0, target, value.length, value.length);
            return ByteBuffer.wrap(target).getLong();
        }

        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(value);
        buffer.flip();
        return buffer.getLong();
    }

    public static long toLong(char value) {
        return value;
    }

    public static long toLong(short value) {
        return value;
    }

    public static long toLong(int value) {
        return value;
    }

    public static long toLong(float value) {
        return (long) value;
    }

    /**
     * 可以将科学记数转成原来的值
     *
     * @param value
     * @return
     */
    public static Long toLong(double value) {
        NumberFormat nf = NumberFormat.getInstance();
        // 去掉逗号
        nf.setGroupingUsed(false);
        String str = nf.format(value);
        return Long.parseLong(str);
    }

    public static Long hex2Long(String hex) {
        BigInteger bigInteger = new BigInteger(hex, 16);
        return bigInteger.longValue();
    }

    public static long bin2Long(String bin) {
        return Long.parseLong(bin, 2);
    }

    public static float toFloatExt(byte[] value) {
        return Float.intBitsToFloat(toIntExt(value));
    }

    public static float toFloat(byte[] value) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(value);
        buffer.flip();
        return buffer.getFloat();
//		return ByteBuffer.wrap(value).getFloat();
    }

    public static float toFloat(char value) {
        return value;
    }

    public static float toFloat(short value) {
        return value;
    }

    public static float toFloat(double value) {
        return (float) value;
    }

    /**
     * 将16进制单精度浮点型转换为10进制浮点型
     *
     * @return float
     * @since 1.0
     */
    public static float hex2Float(String hex) {
        BigInteger bigInteger = new BigInteger(hex, 16);
        return Float.intBitsToFloat(bigInteger.intValue());
    }

    /**
     * 二进制转浮点型
     *
     * @param bin
     * @return
     */
    public static float bin2Float(String bin) {
        return Float.intBitsToFloat(toInt(bin));
    }

    public static double toDoubleExt(byte[] value) {
        return Double.longBitsToDouble(toLongExt(value));
    }

    public static double toDouble(byte[] value) {
        if (value.length < 8) {// 位数不够,前补0
            byte[] target = new byte[8];
            int fillZero = target.length - value.length;
            Arrays.fill(target, 0, fillZero, (byte) 0);
            System.arraycopy(value, 0, target, value.length, value.length);
            return ByteBuffer.wrap(target).getDouble();
        }

        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(value, 0, value.length);
        buffer.flip();
        return buffer.getDouble();
    }

    public static double toDouble(char value) {
        return value;
    }

    public static double toDouble(short value) {
        return value;
    }

    public static double hex2Double(String hex) {
        BigInteger bigInteger = new BigInteger(hex, 16);
        return Double.longBitsToDouble(bigInteger.longValue());
    }

    public static double bin2Double(String bin) {
        return Double.longBitsToDouble(bin2Long(bin));
    }


    public static String toBin(byte value) {
        return null;
    }

    public static String toBin(char value) {
        return null;
    }

    public static String toBin(short value) {
        return Integer.toBinaryString(value);
    }

    /**
     * 转为2进制
     *
     * @param value
     * @return
     */
    public static String toBin(int value) {
        return Integer.toBinaryString(value);
        // Integer.parseInt(binaryString,2) 转10进制
    }

    /**
     * 转为2进制
     *
     * @param value
     * @return
     */
    public static String toBin(long value) {
        return Long.toBinaryString(value);
    }

    /**
     * 转2进制
     *
     * @param value
     * @return
     */
    public static String toBin(float value) {
        return toBin(Float.floatToIntBits(value));
    }

    /**
     * 转2进制
     *
     * @param value
     * @return
     */
    public static String toBin(double value) {
        return toBin(Double.doubleToLongBits(value));
    }

    public static String toString(byte[] value) {
        //return new String(value);
        return getString(ByteBuffer.wrap(value), Charset.forName("UTF-8"));
    }

    public static String toString(char value) {
        return String.valueOf(value);
    }

    public static String toString(short value) {
        return String.valueOf(value);
    }

    public static String toString(int value) {
        return String.valueOf(value);
    }

    public static String toString(long value) {
        return String.valueOf(value);
    }

    public static String toString(float value) {
        return String.valueOf(value);
    }

    public static String toString(double d) {
        NumberFormat nf = NumberFormat.getInstance();
        // 去掉逗号
        nf.setGroupingUsed(false);
        return nf.format(d);
    }

    /**
     * 16进制转字符串,必须符合16进制表达法，4位为一个16进制
     *
     * @param hex
     * @return
     */
    public static String hex2String(String hex) {
        return new String(hex2Byte(hex));
    }

    /**
     * 将字节转换成16进制
     *
     * @param buf
     * @return
     */
    public static String toHex(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            hex = hex.length() == 1 ? 0 + hex : hex;
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static String toHex(char value) {
        return Integer.toHexString(value);
    }

    public static String toHex(short value) {
        return Integer.toHexString(value);
    }

    /**
     * 转16进制数
     *
     * @param value
     * @return
     */
    public static String toHex(int value) {
        return Integer.toHexString(value);
    }

    public static String toHex(long value) {
        return Long.toHexString(value);
    }

    /**
     * 将十进制浮点型转换为十六进制浮点型
     *
     * @return String
     * @since 1.0
     */
    public static String toHex(float value) {
        return Integer.toHexString(Float.floatToIntBits(value));
    }

    public static String toHex(double value) {
        return Long.toHexString(Double.doubleToLongBits(value));
    }

    /**
     * 字符串转16进制, 16进制表达法, 不够4位高位补0
     *
     * @param value
     * @return
     */
    public static String toHex(String value) {
        return toHex(value.getBytes());
    }

    /**
     * 字符串转换unicode
     *
     * @param string
     * @return
     */
    public static String string2Unicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            String hex = Integer.toHexString(c);
            // 转换为unicode
            unicode.append("\\u");
            // if (hex.length() != 4) {
            // unicode.append(String.format("%0" + (4 - hex.length()) + "d", 0) + hex);
            // } else {
            unicode.append(hex);
            // }
        }
        return unicode.toString();
    }

    /**
     * 含有unicode 的字符串转一般字符串
     *
     * @param unicodeStr 混有 Unicode 的字符串
     * @return
     */
    public static String unicodeStr2String(String unicodeStr) {
        // int length = unicodeStr.length();
        // int count = 0;
        // 正则匹配条件，可匹配“\\u”1到4位，一般是4位可直接使用 String regex = "\\\\u[a-f0-9A-F]{4}";
        String regex = "\\\\u[a-f0-9A-F]{1,4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(unicodeStr);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String oldChar = matcher.group();// 原本的Unicode字符
            String newChar = unicode2String(oldChar);// 转换为普通字符
            // int index = unicodeStr.indexOf(oldChar);
            // sb.append(unicodeStr.substring(count, index));// 添加前面不是unicode的字符
            sb.append(newChar);// 添加转换后的字符
            // count = index + oldChar.length();// 统计下标移动的位置
        }
        // sb.append(unicodeStr.substring(count, length));// 添加末尾不是Unicode的字符
        return sb.toString();
    }

    /**
     * unicode 转字符串
     *
     * @param unicode 全为 Unicode 的字符串
     * @return
     */
    public static String unicode2String(String unicode) {
        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            string.append((char) data);
        }
        return string.toString();
    }

    /**
     * 字符串转ByteBuffer
     *
     * @param str
     * @return
     */
    public static ByteBuffer getByteBuffer(String str) {
        return ByteBuffer.wrap(str.getBytes());
    }

    /**
     * ByteBuffer转字符串
     *
     * @param buffer
     * @param charset
     * @return
     */
    public static String getString(ByteBuffer buffer, Charset charset) {
        CharsetDecoder decoder = null;
        CharBuffer charBuffer = null;
        try {
            decoder = charset.newDecoder();
            // charBuffer = decoder.decode(buffer);//用这个的话，只能输出来一次结果，第二次显示为空
            charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static byte[] getLimitByte(ByteBuffer buffer) {
        int len = buffer.limit() - buffer.position();
        byte[] bytes = new byte[len];
        buffer.get(bytes);
        return bytes;
    }

    static Map<Long, String> map = new HashMap<>(12);
    static Map<Long, String> unitMap = new HashMap<>();
    static Map<Long, String> sectionMap = new HashMap<>(3);

    static {
        map.put(0L, "零");
        map.put(1L, "壹");
        map.put(2L, "贰");
        map.put(3L, "叁");
        map.put(4L, "肆");
        map.put(5L, "伍");
        map.put(6L, "陆");
        map.put(7L, "柒");
        map.put(8L, "捌");
        map.put(9L, "玖");

        unitMap.put(1L, "拾");
        unitMap.put(2L, "佰");
        unitMap.put(3L, "仟");

        sectionMap.put(1L, "元");
        // 第2位开始用万
        sectionMap.put(2L, "万");
        // 第3位开始用亿
        sectionMap.put(3L, "亿");

    }

//分、角、元、拾、佰、仟、万、亿、兆

    /**
     * 金额转中文大写
     *
     * @param number
     * @return
     */
    public static String numberConvertCapital(long number) {
        String str = String.valueOf(number);
        int numLength = str.length();
        int section = (numLength + (4 - 1)) / 4;

        StringBuffer sb = new StringBuffer();

        int count = 1;
        while (count <= section) {
            int startIdx = numLength - 4 * count;
            startIdx = startIdx > 0 ? startIdx : 0;

            int ednInx = numLength - 4 * (count - 1);

            String subStr = str.substring(startIdx, ednInx);
            String result = capital2(Long.parseLong(subStr), count);
            sb.insert(0, result);
            count++;
        }
        return sb.toString();
    }

    /**
     * 每4位转大写，所有数字和单位都显示出来，完整版
     *
     * @param number
     * @param section 从右数起第几个4位
     * @return
     */
    public static String capital(long number, long section) {
        StringBuffer sb = new StringBuffer(sectionMap.get(section));
        long remainder = number % 10;
        sb.insert(0, map.get(remainder));
        number = number / 10;

        long tIdx = 0;
        while (number > 0) {
            tIdx++;
            remainder = number % 10;
            sb.insert(0, map.get(remainder) + unitMap.get(tIdx));
            number = number / 10;
        }
        return sb.toString();
    }

    /**
     * 每4位转大写,可简写
     *
     * @param number
     * @param section 从右数起第几个4位
     * @return
     */
    public static String capital2(long number, long section) {
        StringBuffer sb = new StringBuffer(sectionMap.get(section));
        long remainder = number % 10;
        sb.insert(0, map.get(remainder));
        number = number / 10;

        long tIdx = 0;
        while (number > 0) {
            tIdx++;
            remainder = number % 10;
            if (remainder == 0L) {
                sb.insert(0, map.get(remainder));
            } else {
                sb.insert(0, map.get(remainder) + unitMap.get(tIdx));
            }
            number = number / 10;
        }

        String str = sb.toString();
        String[] arr = str.split("");
        int lianxuZero = 0;
        StringBuffer rSb = new StringBuffer();
        for (String string : arr) {
            if ("零".endsWith(string)) {
                lianxuZero++;
            } else {
                lianxuZero = 0;
            }
            if (lianxuZero <= 1) {
                rSb.append(string);
            }
        }

        return rSb.toString();
    }

    public static void main(String[] args) {
    	String zi = "林";
    	System.out.println("zi.getBytes()==>"+zi.getBytes().length);
    	byte cc[]= ByteBuffer.allocate(4).put(zi.getBytes()).array();
    	for (byte b : cc) {
    		System.out.println("b==>"+b);
		}
    	System.out.println(Hex.toHexString(cc));
    	System.out.println("=="+new String(cc)+"==");
        int number = 45897;
        byte[] dd = ByteBuffer.allocate(4).putInt(number).array();
    	for (byte b : dd) {
    		System.out.println("number==>"+b);
		}
    	System.out.println(Hex.toHexString(dd));

    	
//        long number = 6895;
//        System.out.println("number6895:" + capital(number, 1));
//        number = 235_6895;
//        System.out.println("number1235_6895:" + numberConvertCapital(number));
//        System.out.println("107,000:" + numberConvertCapital(107000));
//        byte bb[] = ByteBuffer.allocate(4).putInt(1).array();
//        System.out.println("bb[]:" + Base64.getEncoder().encodeToString(bb));
//        System.out.println("\"字\".getBytes()==>" + "字".getBytes().length);
//        System.out.println(Base64.getEncoder().encodeToString("字".getBytes()));
//        byte[] c = toByteExt(9998);
//        for (byte c2 : c) {
//            System.out.println(c2);
//        }
    }


}
