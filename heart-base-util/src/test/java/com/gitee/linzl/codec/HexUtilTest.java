package com.gitee.linzl.codec;

import org.junit.Test;

import com.gitee.linzl.codec.HexUtil;

public class HexUtilTest {

	@Test
	public void testGetCRC() {
		System.out.println("crc-32:" + HexUtil.hexCrc32("0CB2B7A8DCB4".getBytes()));
		System.out.println("crc-16:" + HexUtil.hexCrc16("0CB2B7A8DCB4".getBytes()));
	}

	@Test
	public void testUnicode2String() {
		System.out.println(HexUtil.string2Unicode("我是logback输出\\u的消息，快消费掉吧"));
		System.out.println(HexUtil.unicodeStr2String(
				"\\u6211\\u662f\\u6c\\u6f\\u67\\u62\\u61\\u63\\u6b\\u8f93\\u51fa\\u5c\\u75\\u7684\\u6d88\\u606f\\uff0c\\u5feb\\u6d88\\u8d39\\u6389\\u5427"));
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
