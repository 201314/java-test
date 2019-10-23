package com.gitee.linzl.codec;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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

	public static byte[] mergeByte(byte[] firstByte, byte[] secondByte) {
		ByteBuffer target = ByteBuffer.allocate(firstByte.length + secondByte.length);
		target.put(firstByte);
		target.put(secondByte);
		return target.array();
	}

	/**
	 * 
	 * 计算CRC16校验码
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
	 * 
	 * 计算CRC16校验码 (Modbus)
	 *
	 * @param data 字节数组
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
	 * 
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
		return fullFormatHex(short2Hex(value));
	}

	public static String fullFormatHex(char value) {
		return fullFormatHex(char2Hex(value));
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
	 *             <p>
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

	public static byte bin2Byte(String bin) {
		return 0;
	}

	public static char bin2Char(String bin) {
		return 0;
	}

	public static short bin2Short(String bin) {
		return Short.parseShort(bin, 2);
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

	public static short toShort(byte[] value) {
		return ByteBuffer.wrap(value).getShort();
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

	public static int toInt(byte[] value) {
		return ByteBuffer.wrap(value).getInt();
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

	public static float byte2Float(byte[] value) {
		return Float.intBitsToFloat(byte2Int(value));
	}

	public static float toFloat(byte[] value) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.put(value);
		buffer.flip();
		return buffer.getFloat();
//		return ByteBuffer.wrap(value).getFloat();
	}

	public static double byte2Double(byte[] value) {
		return Double.longBitsToDouble(byte2Long(value));
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

	public static String byte2String(byte[] value) {
		return new String(value);
	}

	public static String toString(byte[] value) {
		return getString(ByteBuffer.wrap(value), Charset.forName("UTF-8"));
	}

	/**
	 * 将字节转换成16进制
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

	public static byte[] char2Byte(char value) {
		return short2Byte((short) value);
	}

	public static byte[] toByte(char value) {
		return ByteBuffer.allocate(2).putChar(value).array();
	}

	public static int char2Int(char value) {
		return value;
	}

	public static String char2Hex(char value) {
		return Integer.toHexString(value);
	}

	public static byte[] short2Byte(short value) {
		byte[] result = new byte[2];
		// 由高位到低位
		result[0] = (byte) ((value & 0xFF) >> 8);
		result[1] = (byte) (value & 0xFF);
		return result;
	}

	public static byte[] toByte(short value) {
		return ByteBuffer.allocate(2).putShort(value).array();
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
	 * @param value 58165-->16进制为E335
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

	public static byte[] toByte(int value) {
		return ByteBuffer.allocate(4).putInt(value).array();
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

	public static byte[] toByte(long value) {
		return ByteBuffer.allocate(8).putLong(value).array();
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

	public static byte[] toByte(float value) {
		return ByteBuffer.allocate(4).putFloat(value).array();
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

	public static byte[] toByte(double value) {
		return ByteBuffer.allocate(8).putDouble(value).array();
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
		String item = null;
		for (int n = 0, length = hex.length(); n < length; n += 2) {
			item = new String(hex.getBytes(), n, 2);
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
		long number = 6895;
		System.out.println("number6895:" + capital(number, 1));
		number = 235_6895;
		System.out.println("number1235_6895:" + numberConvertCapital(number));
		System.out.println("107,000:" + numberConvertCapital(107000));
	}
}
