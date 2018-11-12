package com.gitee.linzl.generator;

import org.junit.Test;

public class AutoIncrementUtilTest {
	@Test
	public void test1() {
		System.out.println(0x3E);
		String a1 = "0009";
		String a2 = "000Z";
		String a3 = "000z";
		String a4 = "0099";
		String a5 = "009Z";
		String a6 = "009z";
		String a7 = "00Zz";
		String a8 = "00zz";
		System.out.println(AutoIncrementUtil.incrementAlphanumeric(a1));
		System.out.println(AutoIncrementUtil.incrementAlphanumeric(a2));
		System.out.println(AutoIncrementUtil.incrementAlphanumeric(a3));
		System.out.println(AutoIncrementUtil.incrementAlphanumeric(a4));
		System.out.println(AutoIncrementUtil.incrementAlphanumeric(a5));
		System.out.println(AutoIncrementUtil.incrementAlphanumeric(a6));
		System.out.println(AutoIncrementUtil.incrementAlphanumeric(a7));
		System.out.println(AutoIncrementUtil.incrementAlphanumeric(a8));
	}

	@Test
	public void test2() {
		String a1 = "0009";
		String a2 = "0008";
		String a3 = "0010";
		String a4 = "9999";
		String a5 = "0089";
		String a6 = "0019";
		String a7 = "0000000";
		System.out.println(AutoIncrementUtil.incrementNumeric(a1));
		System.out.println(AutoIncrementUtil.incrementNumeric(a2));
		System.out.println(AutoIncrementUtil.incrementNumeric(a3));
		System.out.println(AutoIncrementUtil.incrementNumeric(a4));
		System.out.println(AutoIncrementUtil.incrementNumeric(a5));
		System.out.println(AutoIncrementUtil.incrementNumeric(a6));
		System.out.println(AutoIncrementUtil.incrementNumeric(a7));
	}
}
