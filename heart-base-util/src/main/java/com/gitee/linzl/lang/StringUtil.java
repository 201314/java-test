package com.gitee.linzl.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import io.jsonwebtoken.lang.Collections;

public class StringUtil {
	private static String[] chars = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",

			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z",

			"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
			"v", "w", "x", "y", "z" };

	@SuppressWarnings("unchecked")
	private static List<String> includeList = Collections.arrayToList(chars);

	private static String[] justNumbers = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

	@SuppressWarnings("unchecked")
	private static List<String> includeNumbers = Collections.arrayToList(justNumbers);

	/**
	 * 判断source是否与传入参数中的其中一个相等
	 * 
	 * @param source
	 * @param targets
	 * @return
	 */
	public static boolean orEquals(String source, String... targets) {
		boolean flag = false;
		if (targets == null || targets.length <= 0 || source == null) {
			return flag;
		}
		for (String string : targets) {
			if (source.equals(string)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 让一串字符串不断增加,只包含0~9数字
	 * 
	 * @param source
	 * @param step   增长的步长
	 * @return
	 */
	public static String incrementNumeric(String source) {
		char startChar = '0';
		char endChar = '9';
		char[] sourceArray = source.toCharArray();

		for (int ii = sourceArray.length - 1; ii >= 0; ii--) {
			if (!increment(sourceArray, ii, startChar, endChar, includeNumbers)) {
				break;
			}
		}

		String str = String.valueOf(sourceArray);
		// if (0 == Long.valueOf(str)) {
		// // 位数增加
		// str = 1 + str;
		// }
		return str;
	}

	/**
	 * 让一串字符串自增   
	 * 
	 * @param source
	 * @return
	 */
	public static String incrementAlphanumeric(String source) {
		char startChar = '0';
		char endChar = 'z';
		char[] sourceArray = source.toCharArray();

		for (int ii = sourceArray.length - 1; ii >= 0; ii--) {
			if (!increment(sourceArray, ii, startChar, endChar, includeList)) {
				break;
			}
		}
		return String.valueOf(sourceArray);
	}

	private static boolean increment(char[] inputCharArray, int ii, char start, char end, List<String> includeList) {
		char tempChar = inputCharArray[ii];
		if (tempChar == end) {
			inputCharArray[ii] = start;
			return true;
		}
		do {
			tempChar = ++inputCharArray[ii];
		} while (!includeList.contains(String.valueOf(tempChar)));
		return false;
	}

	/**
	 * 判断是否为空
	 * 
	 * @param param
	 * @return
	 */

	public static boolean isEmpty(String param) {
		return param == null ? true : (param.trim().length() > 0 ? false : true);
	}

	/**
	 * 替换所有空格
	 * 
	 * @param param
	 * @return
	 */
	public static String removeBlank(String param) {
		return param == null ? "" : param.replaceAll("\\s*", "");
	}

	/**
	 * 将所有连接空格替换成一个空格
	 * 
	 * @param str
	 * @return
	 */
	public static String onlyOneBlank(String str) {
		str = str.replaceAll(" +|\t|\r|\n", " ");
		return str;
	}

	/**
	 * 0~index的字符串小写，其他不变
	 * 
	 * @param param
	 * @param index
	 * @return
	 */
	public static String lowEnd(String param, int index) {
		return param.substring(0, index).toLowerCase() + param.substring(index);
	}

	/**
	 * 0~index的字符串小写，其他大写
	 * 
	 * @param param
	 * @param index
	 * @return
	 */
	public static String lowEndOtherUpper(String param, int index) {
		param = param.toUpperCase();
		return lowEnd(param, index);
	}

	/**
	 * 0~index的字符串大写，其他不变
	 * 
	 * @param param
	 * @param index
	 * @return
	 */
	public static String upperEnd(String param, int index) {
		return param.substring(0, index).toUpperCase() + param.substring(index);
	}

	/**
	 * 0~index的字符串大写，其他小写
	 * 
	 * @param param
	 * @param index
	 * @return
	 */
	public static String upperEndOtherLow(String param, int index) {
		param = param.toLowerCase();
		return upperEnd(param, index);
	}

	/**
	 * 第一个字母小写
	 * 
	 * @param param
	 * @return
	 */
	public static String lowFirst(String param) {
		return lowEnd(param, 1);
	}

	/**
	 * 第一个字母大写
	 * 
	 * @param param
	 * @return
	 */
	public static String upperFirst(String param) {
		return upperEnd(param, 1);
	}

	/**
	 * 第一个字母小写,其余大写
	 * 
	 * @param param
	 * @return
	 */
	public static String lowFirstOtherUpper(String param) {
		return lowEndOtherUpper(param, 1);
	}

	/**
	 * 第一个字母大写,其余小写
	 * 
	 * @param param
	 * @return
	 */
	public static String upperFirstOtherLow(String param) {
		return upperEndOtherLow(param, 1);
	}

	/**
	 * 按字节截取字符串
	 * 
	 * @param str    要截取的字符串 如输入：测试testing嘻嘻
	 * @param length 截取的字符串位数 :12 结果：测试testing...
	 * @return
	 */
	public static String subStringByBytes(String str, int length) {
		String result = "";
		int i = 0;
		int j = 0;

		StringBuffer buff = new StringBuffer(str);
		String stmp = "";
		int len = buff.length();
		for (i = 0; i < len; i++) {
			if (j < length) {
				stmp = buff.substring(i, i + 1);
				try {
					stmp = new String(stmp.getBytes("utf-8"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (stmp.getBytes().length > 1) {
					j += 2;
				} else {
					j += 1;
				}
				result += stmp;
			} else {
				break;
			}
		}
		if (j > length) {
			result = result.substring(0, result.length() - 1);
			result += "...";
		}

		return result;
	}

	/**
	 * 使用StringTokenizer将字符串source以split分割成数组
	 * 
	 * @param source 字符串
	 * @param split  以某个字符串做为分割规则
	 * @return
	 */
	public static String[] stringToArray(String source, String split) {
		List<String> tempList = new ArrayList<String>();
		StringTokenizer stk = null;
		if (split == null || split.trim().length() <= 0) {
			stk = new StringTokenizer(source);
		} else {
			stk = new StringTokenizer(source, split);
		}

		while (stk.hasMoreTokens()) {
			tempList.add(stk.nextToken());
		}
		return (String[]) tempList.toArray(new String[tempList.size()]);
	}

	/**
	 * 将字符串source以split分割成int数组
	 * 
	 * @param source
	 * @param split
	 * @return
	 */
	public static int[] stringToIntArray(String source, String split) {
		String[] strArray = source.split(split);
		int[] intArray = new int[strArray.length];
		for (int i = 0; i < intArray.length; i++) {
			intArray[i] = Integer.parseInt(strArray[i]);
		}
		return intArray;
	}

	/**
	 * 使用String.split将字符串source以split分割成List集合
	 * 
	 * @param source 字符串
	 * @param split  以某个字符串做为分割规则
	 * @return
	 */
	public static List<String> splitToList(String source, String split) {
		String[] str = source.split(split);
		List<String> tempList = new ArrayList<String>();
		for (int i = 0; i < str.length; i++) {
			tempList.add(str[i]);
		}
		return tempList;
	}

	/**
	 * 将数组obj分割成一个以split区分的字符串，
	 * 
	 * @param obj
	 * @param split
	 * @return
	 */
	public static String arrayToString(Object[] obj, String split) {
		split = (split == null ? "" : split);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < obj.length; i++) {
			sb.append(obj[i]).append(split);
		}
		return sb.toString();
	}

	/**
	 * 在source前补fillLength个0
	 * 
	 * @param source
	 * @param fillLength
	 * @return
	 */
	public static String appendZeroBefore(String source, int fillLength) {
		return String.format("%0" + fillLength + "d", 0) + source;
	}

	/**
	 * 在source后补fillLength个0
	 * 
	 * @param source
	 * @param fillLength
	 * @return
	 */
	public static String appendZeroAfter(String source, int fillLength) {
		return source + String.format("%0" + fillLength + "d", 0);
	}

	/**
	 * 将source前补fillLength长度空格
	 * 
	 * @param source
	 * @param fullLength
	 * @return
	 */
	public static String appendSpaceBefore(String source, int fillLength) {
		return String.format("%" + fillLength + "s", "") + source;
	}

	/**
	 * 将source后补fillLength长度空格
	 * 
	 * @param source
	 * @param fillLength
	 * @return
	 */
	public static String appendSpaceAfter(String source, int fillLength) {
		return source + String.format("%-" + fillLength + "s", "");
	}

	/**
	 * 左填充
	 * 
	 * @param source     源数据
	 * @param fillLength 需要填充的长度
	 * @param pad        填充内容
	 * @return
	 */
	public static String appendBefore(String source, int fillLength, String pad) {
		StringBuilder sb = new StringBuilder();
		for (int start = 0; start < fillLength; start++) {
			sb.append(pad);
		}
		return sb.toString() + source;
	}

	/**
	 * 右填充
	 * 
	 * @param source     源数据
	 * @param fillLength 需要填充的长度
	 * @param pad        填充内容
	 * @return
	 */
	public static String appendAfter(String source, int fillLength, String pad) {
		StringBuilder sb = new StringBuilder();
		for (int start = 0; start < fillLength; start++) {
			sb.append(pad);
		}
		return source + sb.toString();
	}

	/**
	 * 过滤特殊字符
	 * 
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String filterSpecial(String str) throws PatternSyntaxException {
		// 只允许字母和数字 // String regEx ="[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	public static void main(String[] args) {
		System.out.println(appendSpaceBefore("hello", 10));
		System.out.println(appendZeroBefore("哈哈", 9));
	}
}
