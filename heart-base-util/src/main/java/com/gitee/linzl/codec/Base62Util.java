package com.gitee.linzl.codec;

import org.apache.commons.lang3.StringUtils;

import com.gitee.linzl.cipher.message.DigestUtilsExt;

/**
 * @description Base62 编码用62个可见字符来编码信息，也就是所谓的62进制，可用于缩短地址、短url、短链接之类的
 * 
 *              例如:淘宝口令、微博短url、短信、分享等
 * 
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2019年5月15日
 */
public class Base62Util {
	public static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * 转成10进制
	 * 
	 * @param str
	 * @return
	 */
	public static long toBase10(String str) {
		// 从右边开始
		return toBase10(new StringBuilder(str).reverse().toString().toCharArray());
	}

	private static long toBase10(char[] chars) {
		long n = 0;
		int pow = 0;
		for (char item : chars) {
			n += toBase10(BASE62.indexOf(item), pow);
			pow++;
		}
		return n;
	}

	private static long toBase10(int n, int pow) {
		return n * (long) Math.pow(BASE62.length(), pow);
	}

	/**
	 * 转62进制
	 * 
	 * @param i
	 * @return
	 */
	public static String fromBase10(long i) {
		StringBuilder sb = new StringBuilder();
		if (i == 0) {
			return "a";
		}
		int length = BASE62.length();
		while (i > 0) {
			int rem = (int) (i % length);
			sb.append(BASE62.charAt(rem));
			i = i / length;
		}
		return sb.reverse().toString();
	}

	private static String KEY = "weibo"; // 可以自定义生成 MD5 加密字符传前的混合加密key

	public static String shortUrl(String url) {
		String hex = DigestUtilsExt.md5Hex(KEY + url); // 对传入网址进行 MD5 加密，key是加密字符串

		for (int i = 0; i < 4; i++) {
			// 把加密字符按照8位一组16进制与0x3FFFFFFF进行位与运算
			String sTempSubString = hex.substring(i * 8, i * 8 + 8);

			// 这里需要使用long转换，因为Inteter.parseInt()只能处理 31 位 , 首位为符号位 , 如果不用 long则会越界
			long lHexLong = Long.parseLong(sTempSubString, 16) & 0x3FFFFFFF;
			String outChars = "";
			for (int j = 0; j < 6; j++) {
				long index = 0x0000003D & lHexLong; // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
				outChars += BASE62.toCharArray()[(int) index]; // 把取得的字符相加
				lHexLong = lHexLong >> 5; // 每次循环按位右移 5 位
			}
			if (StringUtils.isNotEmpty(outChars)) {
				return outChars; // 把字符串存入对应索引的输出数组
			}
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(Base62Util.fromBase10(2576460752303423488L));
		System.out.println(Base62Util.toBase10("34kdunmzWUw"));
		String sLongUrl = "http://video.sina.com.cn/p/news/s/v/2015-09-02/105265067233.html"; // 长链接
		// 打印出结果
		// http://t.cn/R3Krh36

		// [0]:::VFvAr2
		// [1]:::iiI3a2
		// [2]:::Z3EvEv
		// [3]:::jMjU3a
		System.out.println("[" + sLongUrl + "]:::" + shortUrl(sLongUrl));
	}
}
