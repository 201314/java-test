package com.gitee.linzl.codec;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

/**
 * byte 1字节
 * 
 * char 2字节（C语言中是1字节）可以存储一个汉字
 * 
 * short 2字节
 * 
 * int 4字节
 * 
 * long 8字节
 * 
 * float 4字节
 * 
 * double 8字节
 * 
 * @description
 * @author linzl
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

	/**
	 * 
	 * 计算CRC16校验码
	 *
	 * @param data
	 *            字节数组
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
	 * 
	 * 计算CRC16校验码 (Modbus)
	 *
	 * @param data
	 *            字节数组
	 * @return 校验码
	 */
	public static String crc16ToHex(byte[] data) {
		return Long.toHexString(crc16(data)).toUpperCase();
	}

	/**
	 * 计算CRC32校验码
	 * 
	 * @param data
	 * @return
	 */
	public static long crc32(byte[] data) {
		CRC32 crc = new CRC32();
		crc.update(data);
		return crc.getValue();
	}

	/**
	 * 
	 * 计算CRC32校验码 (Modbus)
	 *
	 * @param data
	 *            字节数组
	 * @return 校验码
	 */
	public static String crc32ToHex(byte[] data) {
		return Long.toHexString(crc32(data)).toUpperCase();
	}

	public static String fullFormatHex(short value) {
		return fullFormatHex(short2Hex(value));
	}

	public static String fullFormatHex(char value) {
		return fullFormatHex(ascii2Hex(value));
	}

	/**
	 * 补全为16进制数
	 * 
	 * @param value
	 * @return
	 */
	public static String fullFormatHex(int value) {
		return fullFormatHex(int2Hex(value));
	}

	public static String fullFormatHex(long value) {
		return fullFormatHex(long2Hex(value));
	}

	public static String fullFormatHex(float value) {
		return fullFormatHex(float2Hex(value));
	}

	public static String fullFormatHex(double value) {
		return fullFormatHex(double2Hex(value));
	}

	/**
	 * 显示完整格式的16进制,占2个字节
	 * 
	 * @param data
	 *            <p>
	 *            OxFF --> Ox00FF
	 *            <p>
	 *            FF --> 00FF
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

	public static byte bin2Byte(String bin) {
		return 0;
	}

	public static char bin2Char(String bin) {
		return 0;
	}

	public static short bin2Short(String bin) {
		return 0;
	}

	public static int bin2Int(String bin) {
		return Integer.parseInt(bin, 2);
	}

	public static long bin2Long(String bin) {
		return Long.parseLong(bin, 2);
	}

	public static float bin2Float(String bin) {
		return Float.intBitsToFloat(bin2Int(bin));
	}

	public static double bin2Double(String bin) {
		return Double.longBitsToDouble(bin2Long(bin));
	}

	public static char byte2Ascii(byte[] value) {
		return (char) byte2Short(value);
	}

	public static short byte2Short(byte[] value) {
		return (short) (((value[0] & 0xFF) << 8) | value[1] & 0xFF);
	}

	public static int byte2Int(byte[] value) {
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

	public static long byte2Long(byte[] value) {
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

	public static float byte2Float(byte[] value) {
		return Float.intBitsToFloat(byte2Int(value));
	}

	public static double byte2Double(byte[] value) {
		return Double.longBitsToDouble(byte2Long(value));
	}

	/**
	 * 字节数组到double的转换.
	 */
	public static String byte2String(byte[] value) {
		return new String(value);
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String byte2Hex(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			hex = hex.length() == 1 ? 0 + hex : hex;
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	public static byte[] ascii2Byte(char value) {
		return short2Byte((short) value);
	}

	public static int ascii2Int(char value) {
		return value;
	}

	public static String ascii2Hex(char value) {
		return Integer.toHexString(value);
	}

	public static byte[] short2Byte(short value) {
		byte[] result = new byte[2];
		// 由高位到低位
		result[0] = (byte) ((value & 0xFF) >> 8);
		result[1] = (byte) (value & 0xFF);
		return result;
	}

	public static char short2Ascii(short value) {
		return (char) value;
	}

	public static String short2Hex(short value) {
		return Integer.toHexString(value);
	}

	/**
	 * 转 字节
	 * 
	 * @param value
	 *            58165-->16进制为E335
	 * @return
	 */
	public static byte[] int2Byte(int value) {
		byte[] result = new byte[4];
		// 由高位到低位
		result[0] = (byte) ((value & 0xFF) >> 24);
		result[1] = (byte) ((value & 0xFF) >> 16);
		result[2] = (byte) ((value & 0xFF) >> 8);
		result[3] = (byte) (value & 0xFF);
		return result;
	}

	public static char int2Ascii(int value) {
		return (char) value;
	}

	/**
	 * 转16进制数
	 * 
	 * @param value
	 * @return
	 */
	public static String int2Hex(int value) {
		return Integer.toHexString(value);
	}

	/**
	 * 转为2进制
	 * 
	 * @param value
	 * @return
	 */
	public static String int2Bin(int value) {
		return Integer.toBinaryString(value);
	}
	// Integer.parseInt(binaryString,2) 转10进制

	public static byte[] long2Byte(long value) {
		byte[] byteNum = new byte[8];
		for (int ix = 0; ix < 8; ++ix) {
			int offset = 64 - (ix + 1) * 8;
			byteNum[ix] = (byte) ((value & 0xFF) >> offset);
		}
		return byteNum;
	}

	public static char long2Ascii(long value) {
		return (char) value;
	}

	public static String long2Hex(long value) {
		return Long.toHexString(value);
	}

	/**
	 * 转为2进制
	 * 
	 * @param value
	 * @return
	 */
	public static String long2Bin(long value) {
		return Long.toBinaryString(value);
	}

	public static byte[] float2Byte(float value) {
		return int2Byte(Float.floatToIntBits(value));
	}

	public static char float2Ascii(float value) {
		return (char) value;
	}

	/**
	 * 将十进制浮点型转换为十六进制浮点型
	 *
	 * @return String
	 * @since 1.0
	 */
	public static String float2Hex(float value) {
		return Integer.toHexString(Float.floatToIntBits(value));
	}

	/**
	 * 转2进制
	 * 
	 * @param value
	 * @return
	 */
	public static String float2Bin(float value) {
		return int2Bin(Float.floatToIntBits(value));
	}

	public static byte[] double2Byte(double value) {
		return long2Byte(Double.doubleToLongBits(value));
	}

	public static char double2Ascii(double value) {
		return (char) value;
	}

	public static String double2Hex(double value) {
		return Long.toHexString(Double.doubleToLongBits(value));
	}

	/**
	 * 转2进制
	 * 
	 * @param value
	 * @return
	 */
	public static String double2Bin(double value) {
		return long2Bin(Double.doubleToLongBits(value));
	}

	public static byte[] string2Byte(String value) {
		return value.getBytes();
	}

	public static char[] string2Ascii(String value) {
		return value.toCharArray();
	}

	/**
	 * 字符串转16进制, 16进制表达法, 不够4位高位补0
	 * 
	 * @param str
	 * @return
	 */
	public static String string2Hex(String str) {
		return byte2Hex(str.getBytes());
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
		for (int n = 0; n < hex.length(); n += 2) {
			String item = new String(hex.getBytes(), n, 2);
			result[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return result;
	}

	public static Integer hex2Int(String hex) {
		BigInteger bigInteger = new BigInteger(hex, 16);
		return bigInteger.intValue();
	}

	public static Long hex2Long(String hex) {
		BigInteger bigInteger = new BigInteger(hex, 16);
		return bigInteger.longValue();
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

	public static double hex2Double(String hex) {
		BigInteger bigInteger = new BigInteger(hex, 16);
		return Double.longBitsToDouble(bigInteger.longValue());
	}

	/**
	 * 16进制转字符串,必须符合16进制表达法，4位为一个16进制
	 * 
	 * @param str
	 * @return
	 */
	public static String hex2String(String hex) {
		return new String(hex2Byte(hex));
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
	 * @param unicodeStr
	 *            混有 Unicode 的字符串
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
	 * @param unicode
	 *            全为 Unicode 的字符串
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
	
	public static void main(String[] args) {
		System.out.println(unicode2String("\\u670D\\u52A1\\u7A97"));
	}

}
