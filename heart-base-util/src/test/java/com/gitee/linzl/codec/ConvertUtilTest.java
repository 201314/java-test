package com.gitee.linzl.codec;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

public class ConvertUtilTest {

	@Test
	public void crc() {
		System.out.println("crc-16 long1:" + ConvertUtil.crc16("0CB2B7A8DCB4".getBytes()));
		System.out.println("crc-16 hex2:" + ConvertUtil.crc16ToHex("0CB2B7A8DCB4".getBytes()));
		System.out.println("crc-16 hex3:" + ConvertUtil.crc16ToHex(ConvertUtil.hex2Byte("0CB2B7A8DCB4")));
		System.out.println("crc-32 long1:" + ConvertUtil.crc32("0CB2B7A8DCB4".getBytes()));
		System.out.println("crc-32 hex2:" + ConvertUtil.crc32ToHex("0CB2B7A8DCB4".getBytes()));
		System.out.println("crc-32 hex2:" + ConvertUtil.crc32ToHex(ConvertUtil.hex2Byte("0CB2B7A8DCB4")));
	}

	@Test
	public void fullFormatHex() {
		String dataHex = "63";
		System.out.println(ConvertUtil.fullFormatHex(dataHex));

		int data = 99;
		String dataHex2 = "0063";
		System.out.println(dataHex2.equalsIgnoreCase(ConvertUtil.fullFormatHex(data)));
	}

	@Test
	public void byteTest() {
		String str = "POS88884";
		String strHex = "504F533838383834";
		System.out.println(new String(ConvertUtil.hex2Byte(strHex)));
		byte value[] = { 0x41, (byte) 0xc8, 0, 0 };
		System.out.println("byte 2 ascii:" + ConvertUtil.toChar(value));
		System.out.println("byte 2 short:" + ConvertUtil.toShortExt(value));
		System.out.println("byte 2 int:" + ConvertUtil.toIntExt(value));
		System.out.println("to Int:" + ConvertUtil.toInt(value));
		System.out.println("byte 2 long:" + ConvertUtil.toLongExt(value));

		byte dd[] = { 0x41, (byte) 0xc8, 0, 0 };
		System.out.println("to long:" + ConvertUtil.toLong(dd));
		System.out.println("byte 2 float:" + ConvertUtil.toFloatExt(value));
		System.out.println("to float:" + ConvertUtil.toFloat(value));
		System.out.println("byte 2 double:" + ConvertUtil.toDoubleExt(value));
		System.out.println("to double:" + ConvertUtil.toDouble(dd));
	}

	@Test
	public void shortTest() {
		short value = 110;
		byte[] bytes = ByteBuffer.allocate(2).putShort(value).array();
		System.out.println("short2Byte:" + ConvertUtil.toByte(value));

		byte[] b = ConvertUtil.toByteExt(value);
		System.out.println(Arrays.toString(b));
		System.out.println("byte2Short:" + ConvertUtil.toShortExt(b));
		System.out.println("toShort:" + ConvertUtil.toShort(b));
		System.out.println("Ascii:" + ConvertUtil.toChar(value));
		System.out.println("to hex:" + Hex.toHexString(bytes));
		System.out.println("Hex:" + ConvertUtil.toHex(value));
	}

	@Test
	public void charTest() {
		char value = '3';
		System.out.println("char to int:" + ConvertUtil.toInt(value));
		System.out.println("char to byte:" + Arrays.toString(ConvertUtil.char2Byte(value)));
		System.out.println("toByte:" + Arrays.toString(ConvertUtil.toByte(value)));
		System.out.println("char to hex:" + ConvertUtil.toHex(value));
		System.out.println("char to bin:" + Integer.toBinaryString(value));
	}

	@Test
	public void intTest() {
		int value = 9998;
		byte[] bytes = ByteBuffer.allocate(4).putInt(value).array();
		System.out.println(Arrays.toString(bytes));

		byte[] b = ConvertUtil.toByteExt(value);
		System.out.println(Arrays.toString(b));
		System.out.println("byte2Int:" + ConvertUtil.toIntExt(b));
		System.out.println("int to Ascii:" + ConvertUtil.toChar(value));
		System.out.println("int to hex:" + Hex.toHexString(bytes));
		System.out.println("full hex:" + ConvertUtil.fullFormatHex(value));
		System.out.println("int to Hex:" + ConvertUtil.toHex(value));
		System.out.println("hex to int:" + ConvertUtil.hex2Int(ConvertUtil.toHex(value)));
		System.out.println("int to bin:" + ConvertUtil.toBin(value));
		System.out.println("bin to int:" + ConvertUtil.toInt(ConvertUtil.toBin(value)));
	}

	@Test
	public void longTest() {
		long value = 216;
		byte[] bytes = ByteBuffer.allocate(8).putLong(value).array();
		System.out.println(Arrays.toString(bytes));

		byte[] b = ConvertUtil.toByteExt(value);
		System.out.println(Arrays.toString(b));
		System.out.println("byte2Long:" + ConvertUtil.toLongExt(b));
		System.out.println("long to Ascii:" + ConvertUtil.toChar(value));
		System.out.println("long to  hex:" + Hex.toHexString(bytes));
		System.out.println("full hex:" + ConvertUtil.fullFormatHex(value));
		System.out.println("long to Hex:" + ConvertUtil.toHex(value));
		System.out.println("hex to long:" + ConvertUtil.hex2Long(ConvertUtil.toHex(value)));
		System.out.println("long to bin:" + ConvertUtil.toBin(value));
		System.out.println("bin to long:" + ConvertUtil.bin2Long(ConvertUtil.toBin(value)));
	}

	@Test
	public void floatTest() {
		float value = 216;
		byte[] bytes = ByteBuffer.allocate(4).putFloat(value).array();
		System.out.println(Arrays.toString(bytes));

		byte[] b = ConvertUtil.toByteExt(value);
		System.out.println(Arrays.toString(b));
		System.out.println("byte2Float:" + ConvertUtil.toFloatExt(b));
		System.out.println("float to Ascii:" + ConvertUtil.toChar(value));
		System.out.println("float to  hex:" + Hex.toHexString(bytes));
		System.out.println("full hex:" + ConvertUtil.fullFormatHex(value));
		System.out.println("float to Hex:" + ConvertUtil.toHex(value));
		System.out.println("hex to float:" + ConvertUtil.hex2Float(ConvertUtil.toHex(value)));
		System.out.println("float to bin:" + ConvertUtil.toBin(value));
		System.out.println("bin to float:" + ConvertUtil.bin2Float(ConvertUtil.toBin(value)));

		float f = 100.9f;
		System.out.println(ConvertUtil.toHex(f));
		String floatHex = "42c9cccd";
		System.out.println(ConvertUtil.hex2Float(floatHex));

	}

	@Test
	public void doubleTest() {
		double value = 216;
		byte[] bytes = ByteBuffer.allocate(8).putDouble(value).array();
		System.out.println(Arrays.toString(bytes));

		byte[] b = ConvertUtil.toByteExt(value);
		System.out.println(Arrays.toString(b));
		System.out.println("byte2Double:" + ConvertUtil.toDoubleExt(b));
		System.out.println("double to Ascii:" + ConvertUtil.toChar(value));
		System.out.println("double to hex:" + Hex.toHexString(bytes));
		System.out.println("full hex:" + ConvertUtil.fullFormatHex(value));
		System.out.println("double to Hex:" + ConvertUtil.toHex(value));
		System.out.println("hex to double:" + ConvertUtil.hex2Double(ConvertUtil.toHex(value)));
		System.out.println("float to bin:" + ConvertUtil.toBin(value));
		System.out.println("bin to float:" + ConvertUtil.bin2Double(ConvertUtil.toBin(value)));

		double d = 10293.234;
		System.out.println(ConvertUtil.toHex(d));
		String doubleHex = "40c41a9df3b645a2";
		System.out.println(ConvertUtil.hex2Double(doubleHex));
	}

	@Test
	public void stringTest() {
		String str = "POS88884";
		String strHex = "504F533838383834";
		System.out.println("to hex1:" + ConvertUtil.toHex(str));
		System.out.println("to hex2:" + Hex.toHexString(str.getBytes()));

		System.out.println("hex to1:" + ConvertUtil.hex2String(strHex));
		System.out.println("hex to2:" + new String(Hex.decode(strHex.getBytes())));
	}

	@Test
	public void testUnicode2String() {
		System.out.println(ConvertUtil.string2Unicode("我是logback输出\\u的消息，快消费掉吧"));
		System.out.println(ConvertUtil.unicodeStr2String(
				"\\u6211\\u662f\\u6c\\u6f\\u67\\u62\\u61\\u63\\u6b\\u8f93\\u51fa\\u5c\\u75\\u7684\\u6d88\\u606f\\uff0c\\u5feb\\u6d88\\u8d39\\u6389\\u5427"));
	}
}
