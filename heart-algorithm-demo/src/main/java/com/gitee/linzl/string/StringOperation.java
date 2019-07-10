package com.gitee.linzl.string;

import java.util.HashMap;
import java.util.Map;

public class StringOperation {
	/**
	 * T 为非空串。若主串S中第pos个字符之后存在与T相等的子串， 则返回第一个这样的子串在S中的位置，否则返回0
	 * 
	 * @return
	 */
	public static int index(String source, String target, int pos) {
		int slength = source.length();
		int tlength = target.length();
		if (pos >= 0) {
			int index = pos;
			while ((index + tlength - 1) < slength) {
				String temp = source.substring(index, index + tlength);
				if (target.equals(temp)) {
					return index;
				}
				index++;
			}
		}
		return 0;
	}

	/**
	 * 不能使用String的相关方法，实现字符串截取功能
	 * 
	 * @param source
	 * @param num
	 */
	public static void split(String source, int num) {
		int k = 0;
		String temp = "";
		for (int i = 0; i < source.length(); i++) {
			byte[] b = (source.charAt(i) + "").getBytes();
			k += b.length;
			if (k > num) {
				break;
			}
			temp = temp + source.charAt(i);
		}
		System.out.println(temp);
	}

	/**
	 * 统计各字符串出现的次数
	 */
	public void countOfString() {
		String str = "hehemygodshewuhemyaretwareyou";
		int count = 1;
		String tempStr = null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < str.length(); i++) {
			tempStr = String.valueOf(str.charAt(i));
			if (map.get(tempStr) != null) {
				map.put(tempStr, map.get(tempStr) + 1);
			} else {
				map.put(tempStr, count);
			}
		}
		map.forEach((key, value) -> System.out.println(key + "-->" + value));
	}

	public static void main(String[] args) {
		String source = "123456789";
		String target = "789";
		int pos = 5;
		System.out.println("所在位置:" + index(source, target, pos));
	}
}
