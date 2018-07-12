package com.gitee.linzl.codec;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

import org.bouncycastle.util.encoders.Hex;

/**
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年4月24日
 */
public class HexUtil {
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
	public static String hexCrc16(byte[] data) {
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
	public static String hexCrc32(byte[] data) {
		return Long.toHexString(crc32(data)).toUpperCase();
	}

	/**
	 * 显示完整的16进制格式,占2个字节
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
		} else {
			data = String.format("%0" + zeroNum + "d", 0) + data;
		}
		return data.toUpperCase();
	}

	/**
	 * 补全为16进制数
	 * 
	 * @param value
	 * @return
	 */
	public static String fullFormatHex(int value) {
		String hex = Integer.toHexString(value);
		return fullFormatHex(hex);
	}

	/**
	 * 16进制数的字符串 转 字节
	 * 
	 * @param value
	 *            "E335" "E3"
	 * @return
	 */
	public static byte[] hexToByte(String value) {
		if (value.length() == 2 || value.length() == 4) {
			return Hex.decode(value);
		}
		return null;
	}

	/**
	 * 转 字节
	 * 
	 * @param value
	 *            58165-->16进制为E335
	 * @return
	 */
	public static byte[] toByte(int value) {
		return hexToByte(Integer.toHexString(value));
	}

	/**
	 * 合并两个byte数组
	 * 
	 * @param byte_1
	 * @param byte_2
	 * @return
	 */
	public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
		byte[] byte_3 = new byte[byte_1.length + byte_2.length];
		System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
		return byte_3;
	}

	/**
	 * 有符号转无符号
	 */
	/**
	 * 无符号转有符号
	 */

	/**
	 * 移位运算
	 */
	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2Hex(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] parseHex2Byte(String hex) {
		if (hex.length() < 1) {
			return null;
		}

		byte[] result = new byte[hex.length() / 2];
		for (int i = 0; i < hex.length() / 2; i++) {
			int high = Integer.parseInt(hex.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hex.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 16进制字符串转10进制
	 */
	public int parseInt(String str) {
		int b = Integer.parseInt(str.replaceAll("^0[x|X]", ""), 16);
		System.out.println((char) b);
		return b;
	}

	public static String double2Hex(double data) {
		return Long.toHexString(Double.doubleToLongBits(data));
	}

	public static double hex2double(String hexStr) {
		BigInteger bigInteger = new BigInteger(hexStr, 16);
		return Double.longBitsToDouble(bigInteger.longValue());
	}

	/**
	 * 将十进制浮点型转换为十六进制浮点型
	 *
	 * @return String
	 * @since 1.0
	 */
	public static String float2Hex(float data) {
		return Integer.toHexString(Float.floatToIntBits(data));
	}

	/**
	 * 将16进制单精度浮点型转换为10进制浮点型
	 *
	 * @return float
	 * @since 1.0
	 */
	public static float hex2Float(String hexStr) {
		BigInteger bigInteger = new BigInteger(hexStr, 16);
		return Float.intBitsToFloat(bigInteger.intValue());
	}

	/**
	 * 字符串转16进制, 16进制表达法, 不够4位高位补0
	 * 
	 * @param str
	 * @return
	 */
	public static String string2Hex(String str) {
		StringBuffer buffer = new StringBuffer();
		for (int index = 0; index < str.length(); index++) {
			char c = str.charAt(index);
			// 这里要注意，不足4位的16进制，要补够位数
			String hex = Integer.toHexString(c);
			if (hex.length() != 4) {
				buffer.append(String.format("%0" + (4 - hex.length()) + "d", 0) + hex);
			} else {
				buffer.append(hex);
			}
		}
		return buffer.toString();
	}

	/**
	 * 16进制转字符串,必须符合16进制表达法，4位为一个16进制
	 * 
	 * @param str
	 * @return
	 */
	public static String hex2String(String hex) {
		if (hex.length() % 4 != 0) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		// 49204c6f7665204a617661 split into two characters 49, 20, 4c...
		// 要特别注意，如果是16进制表达法,必须是4位为有补0
		for (int i = 0; i < hex.length() - 1; i += 4) {
			// grab the hex in pairs
			String output = hex.substring(i, (i + 4));
			// convert hex to decimal
			int decimal = Integer.parseInt(output, 16);
			// convert the decimal to character
			sb.append((char) decimal);
		}
		return sb.toString();
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

}
