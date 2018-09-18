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
	public void increase() {
		String a1 = "0009";
		String a2 = "000Z";
		String a3 = "000z";
		String a4 = "0099";
		String a5 = "009Z";
		String a6 = "009z";
		String a7 = "00Zz";
		String a8 = "00zz";
		System.out.println(a1 + "==" + StringUtil.increase(a1));
		System.out.println(a2 + "==" + StringUtil.increase(a2));
		System.out.println(a3 + "==" + StringUtil.increase(a3));
		System.out.println(a4 + "==" + StringUtil.increase(a4));
		System.out.println(a5 + "==" + StringUtil.increase(a5));
		System.out.println(a6 + "==" + StringUtil.increase(a6));
		System.out.println(a7 + "==" + StringUtil.increase(a7));
		System.out.println(a8 + "==" + StringUtil.increase(a8));
	}

	public void filter() {
		String str1 = "*adCVs*34_a _09_b5*[/435^*&城池()^$$&*).{}+.|.)%%*(*.中国}34{45[]12.fd'*&999下面是中文的字符￥……{}【】。，；’“‘”？";
		str1 = "?多来米";
		System.out.println(str1);
		System.out.println(StringUtil.filterSpecial(str1));
	}
}
