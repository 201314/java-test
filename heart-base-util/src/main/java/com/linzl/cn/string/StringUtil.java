package com.linzl.cn.string;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StringUtil {

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
	 * @param str
	 *            要截取的字符串 如输入：测试testing嘻嘻
	 * @param length
	 *            截取的字符串位数 :12 结果：测试testing...
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
	 * @param source
	 *            字符串
	 * @param split
	 *            以某个字符串做为分割规则
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
	 * @param source
	 *            字符串
	 * @param split
	 *            以某个字符串做为分割规则
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

	public static void main(String[] args) {
		String str = "这是标题 ------------           标题\nhello	12";
		System.out.println(str);
		System.out.println(str.replaceAll("\t|\r|\n", " "));
		System.out.println("".isEmpty());
	}
}
