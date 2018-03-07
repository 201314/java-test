package com.linzl.cn.codec;

import org.junit.Test;

public class HexUtilTest {

	@Test
	public void testGetCRC() {

	}

	@Test
	public void testGetHexCRC() {

	}

	@Test
	public void testfullFormatStrHex() {
		String dataHex = "63";
		System.out.println(HexUtil.fullFormatHex(dataHex));
	}

	@Test
	public void testfullFormatIntHex() {
		int data = 99;
		String dataHex = "0063";
		System.out.println(dataHex.equalsIgnoreCase(HexUtil.fullFormatHex(data)));
	}

	@Test
	public void testparseInt() {

	}

	@Test
	public void parseHex2Double() {
		String doubleHex = "40c41a9df3b645a2";
		double f = 10293.234;
		System.out.println(f == HexUtil.hex2double(doubleHex));
	}

	@Test
	public void parseDouble2Hex() {
		double f = 10293.234;
		String doubleHex = "40c41a9df3b645a2";
		System.out.println(doubleHex.equalsIgnoreCase(HexUtil.double2Hex(f)));
	}

	@Test
	public void parseFloat2Hex() {
		String floatHex = "42c9cccd";
		float f = 100.9f;
		System.out.println(f == HexUtil.hex2Float(floatHex));
	}

	@Test
	public void parseHex2Float() {
		float f = 100.9f;
		String floatHex = "42c9cccd";
		System.out.println(floatHex.equalsIgnoreCase(HexUtil.float2Hex(f)));
	}

	@Test
	public void convertStringToHex() {
		String str = "POS88884";
		String strHex = "504F533838383834";
		String toHex = HexUtil.string2Hex(str);
		System.out.println(strHex.equalsIgnoreCase(toHex));
	}

	@Test
	public void convertHexToString() {
		String strHex = "504F533838383834";
		String str = "POS88884";
		String toStr = HexUtil.hex2String(strHex);
		System.out.println(toStr);
		System.out.println(str.equals(toStr));
	}
}
