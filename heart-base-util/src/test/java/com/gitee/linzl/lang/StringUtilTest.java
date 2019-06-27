package com.gitee.linzl.lang;

import org.junit.Test;

public class StringUtilTest {
	@Test
	public void replace() {
		String str = "这是标题 ------------           标题\nhello	12";
		System.out.println(str);
		System.out.println(str.replaceAll("\t|\r|\n", " "));
		System.out.println("".isEmpty());
	}

	@Test
	public void incrementAlphanumeric() {
		String a1 = "0009";
		String a2 = "000Z";
		String a3 = "000z";
		String a4 = "0099";
		String a5 = "009Z";
		String a6 = "009z";
		String a7 = "00Zz";
		String a8 = "zzzz";
		System.out.println(a1 + "==" + StringUtil.incrementAlphanumeric(a1));
		System.out.println(a2 + "==" + StringUtil.incrementAlphanumeric(a2));
		System.out.println(a3 + "==" + StringUtil.incrementAlphanumeric(a3));
		System.out.println(a4 + "==" + StringUtil.incrementAlphanumeric(a4));
		System.out.println(a5 + "==" + StringUtil.incrementAlphanumeric(a5));
		System.out.println(a6 + "==" + StringUtil.incrementAlphanumeric(a6));
		System.out.println(a7 + "==" + StringUtil.incrementAlphanumeric(a7));
		System.out.println(a8 + "==" + StringUtil.incrementAlphanumeric(a8));
	}

	@Test
	public void incrementNumeric() {
		String a1 = "0009";
		String a2 = "0008";
		String a3 = "0010";
		String a4 = "9999";
		String a5 = "0089";
		String a6 = "0019";
		String a7 = "zzzz";
		System.out.println(StringUtil.incrementNumeric(a1));
		System.out.println(StringUtil.incrementNumeric(a2));
		System.out.println(StringUtil.incrementNumeric(a3));
		System.out.println(StringUtil.incrementNumeric(a4));
		System.out.println(StringUtil.incrementNumeric(a5));
		System.out.println(StringUtil.incrementNumeric(a6));
		System.out.println(StringUtil.incrementNumeric(a7));
	}

	@Test
	public void filter() {
		String str1 = "*adCVs*34_a _09_b5*[/435^*&城池()^$$&*).{}+.|.)%%*(*.中国}34{45[]12.fd'*&999下面是中文的字符￥……{}【】。，；’“‘”？";
		str1 = "?多来米";
		System.out.println(str1);
		System.out.println(StringUtil.filterSpecial(str1));
	}

	@Test
	public void append() {
		System.out.println(StringUtil.appendSpaceBefore("hello", 10));
		System.out.println(StringUtil.appendZeroBefore("哈哈", 9));
	}
}
