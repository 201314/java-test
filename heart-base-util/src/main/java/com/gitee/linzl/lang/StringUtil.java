package com.gitee.linzl.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtil {
	// 开始字符，比如字符0
	private static char startChar = '0';
	// 结束字符，比如字符Z
	private static char endChar = 'z';

	private static char[] exclusionFirList = null;
	private static char[] exclusionSecList = null;
	static {
		// 除外列表，比如字符9和字符A之间的[:;<=>?]
		exclusionFirList = new char['A' - '9' - 1];
		exclusionSecList = new char['a' - 'Z' - 1];
		for (char cc = '9' + 1; cc < 'A'; cc++) {
			exclusionFirList[cc - '9' - 1] = cc;
		}
		for (char cc = 'Z' + 1; cc < 'a'; cc++) {
			exclusionSecList[cc - 'Z' - 1] = cc;
		}
	}

	public static boolean exclusionArray(char index) {
		// 刚好在特殊字符范围内
		if ((exclusionFirList[0] <= index && index <= exclusionFirList[exclusionFirList.length - 1])
				|| (exclusionSecList[0] <= index && index <= exclusionSecList[exclusionSecList.length - 1])) {
			return true;
		}
		return false;
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
	 * 递增字符串，特殊字符除外
	 * 
	 * @param source
	 * @return
	 */
	public static String autoIncrease(String source) {
		return increaseByStep(source, 1);
	}

	/**
	 * 按步长递增字符串，特殊字符除外
	 * 
	 * @param source
	 * @param step
	 *            增长步长
	 * @return
	 */
	public static String increaseByStep(String source, int step) {
		char[] inputCharArray = source.toCharArray();
		boolean carryFlag = false;
		int length = inputCharArray.length - 1;
		for (int ii = length; ii >= 0; ii--) {
			char tempChar = inputCharArray[ii];
			// 处理第一位
			if (ii == length) {
				if (tempChar == endChar) {// 刚好+1要进一位
					inputCharArray[ii] = startChar;
					carryFlag = true;
				} else {
					do {
						inputCharArray[ii] += step;
					} while (exclusionArray(inputCharArray[ii]));
				}
			} else {
				if (carryFlag) {
					do {
						inputCharArray[ii] += step;
					} while (exclusionArray(inputCharArray[ii]));

					tempChar = inputCharArray[ii];
					if (tempChar > endChar) {
						inputCharArray[ii] = startChar;
						carryFlag = true;
					} else {
						carryFlag = false;
					}
				}
			}
		}
		return String.valueOf(inputCharArray);
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

	/**
	 * 在source前补zeroNum个0
	 * 
	 * @param source
	 * @param zeroNum
	 * @return
	 */
	public static String appendZeroBefore(String source, int zeroNum) {
		return String.format("%0" + zeroNum + "d", 0) + source;
	}

	/**
	 * 在source后补zeroNum个0
	 * 
	 * @param source
	 * @param zeroNum
	 * @return
	 */
	public static String appendZeroAfter(String source, int zeroNum) {
		return source + String.format("%0" + zeroNum + "d", 0);
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
