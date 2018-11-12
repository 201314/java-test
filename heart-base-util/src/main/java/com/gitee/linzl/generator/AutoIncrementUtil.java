package com.gitee.linzl.generator;

import java.util.List;

import io.jsonwebtoken.lang.Collections;

public class AutoIncrementUtil {
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
	 * 让一串字符串自增,只包含0~9数字
	 * 
	 * @param source
	 * @return
	 */
	public static String incrementNumeric(String source) {
		char startChar = '0';
		char endChar = '9';
		char[] sourceArray = source.toCharArray();

		for (int ii = sourceArray.length - 1; ii >= 0; ii--) {
			if (!increaseCommon(sourceArray, ii, startChar, endChar, includeNumbers)) {
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
			if (!increaseCommon(sourceArray, ii, startChar, endChar, includeList)) {
				break;
			}
		}
		return String.valueOf(sourceArray);
	}

	private static boolean increaseCommon(char[] inputCharArray, int ii, char start, char end,
			List<String> includeList) {
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

}
