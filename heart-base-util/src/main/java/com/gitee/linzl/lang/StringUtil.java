package com.gitee.linzl.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import io.jsonwebtoken.lang.Collections;

public class StringUtil {
	@SuppressWarnings("unchecked")
	private static List<Character> numerics = Collections
			.arrayToList(new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' });

	@SuppressWarnings("unchecked")
	private static List<Character> alphabetics = Collections.arrayToList(new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',

			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z' });

	private static List<Character> alphanumerics = new ArrayList<>();
	static {
		alphanumerics.addAll(numerics);
		alphanumerics.addAll(alphabetics);
	}

	/**
	 * 判断source是否与传入参数中的其中一个相等
	 * 
	 * @param source
	 * @param targets
	 * @return
	 */
	public static boolean orEquals(String source, String... targets) {
		boolean flag = false;
		if (Objects.isNull(targets) || targets.length <= 0 || Objects.isNull(source)) {
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
	 * @return
	 */
	public static String incrementNumeric(String source) {
		return String.valueOf(increment(source.toCharArray(), numerics));
	}

	/**
	 * 让一串字符串不断增加,,仅含字母
	 * 
	 * @param source
	 * @return
	 */
	public static String incrementAlphabetic(String source) {
		return String.valueOf(increment(source.toCharArray(), alphabetics));
	}

	/**
	 * 让一串字符串自增,含字母和数字
	 * 
	 * @param source
	 * @return
	 */
	public static String incrementAlphanumeric(String source) {
		return String.valueOf(increment(source.toCharArray(), alphanumerics));
	}

	private static char[] increment(char[] charArr, List<Character> list) {
		char start = list.get(0);
		char end = list.get(list.size() - 1);

		for (int ii = charArr.length - 1; ii >= 0; ii--) {
			char tempChar = charArr[ii];
			if (tempChar == end) {
				charArr[ii] = start;
				continue;
			}
			do {
				tempChar = ++charArr[ii];
			} while (!list.contains(tempChar));
			break;
		}

		return charArr;
	}

	/**
	 * 判断是否为空
	 * 
	 * @param param
	 * @return
	 */

	public static boolean isEmpty(String... param) {
		boolean result = true;
		if (Objects.isNull(param) || param.length == 0) {
			result = false;
		} else {
			for (String value : param) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}

	/**
	 * 替换所有空格
	 * 
	 * @param param
	 * @return
	 */
	public static String removeBlank(String param) {
		return Objects.isNull(param) ? "" : param.replaceAll("\\s*", "");
	}

	/**
	 * 将所有连接空格替换成一个空格
	 * 
	 * @param str
	 * @return
	 */
	public static String onlyOneBlank(String str) {
		return str.replaceAll(" +|\t|\r|\n", " ");
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
	 * 第一个字母小写,其余大写
	 * 
	 * @param param
	 * @return
	 */
	public static String lowFirstOtherUpper(String param) {
		return lowEndOtherUpper(param, 1);
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
	public static String subByte(String str, int length) {
		String result = "";
		int i = 0;
		int j = 0;

		StringBuffer buff = new StringBuffer(str);
		String stmp = "";
		int len = buff.length();
		for (i = 0; i < len; i++) {
			if (j >= length) {
				break;
			}

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
		if (Objects.isNull(split) || split.trim().length() <= 0) {
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
		split = (Objects.isNull(split) ? "" : split);
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
}
