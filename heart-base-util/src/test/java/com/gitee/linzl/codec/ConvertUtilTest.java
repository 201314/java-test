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
		System.out.println("byte 2 ascii:" + ConvertUtil.byte2Ascii(value));
		System.out.println("byte 2 short:" + ConvertUtil.byte2Short(value));
		System.out.println("byte 2 int:" + ConvertUtil.byte2Int(value));
		System.out.println("to Int:" + ConvertUtil.toInt(value));
		System.out.println("byte 2 long:" + ConvertUtil.byte2Long(value));

		byte dd[] = { 0x41, (byte) 0xc8, 0, 0 };
		System.out.println("to long:" + ConvertUtil.toLong(dd));
		System.out.println("byte 2 float:" + ConvertUtil.byte2Float(value));
		System.out.println("to float:" + ConvertUtil.toFloat(value));
		System.out.println("byte 2 double:" + ConvertUtil.byte2Double(value));
		System.out.println("to double:" + ConvertUtil.toDouble(dd));
	}

	@Test
	public void shortTest() {
		short value = 110;
		byte[] bytes = ByteBuffer.allocate(2).putShort(value).array();
		System.out.println("short2Byte:" + ConvertUtil.toByte(value));

		byte[] b = ConvertUtil.short2Byte(value);
		System.out.println(Arrays.toString(b));
		System.out.println("byte2Short:" + ConvertUtil.byte2Short(b));
		System.out.println("toShort:" + ConvertUtil.toShort(b));
		System.out.println("Ascii:" + ConvertUtil.short2Ascii(value));
		System.out.println("to hex:" + Hex.toHexString(bytes));
		System.out.println("Hex:" + ConvertUtil.short2Hex(value));
	}

	@Test
	public void charTest() {
		char value = '3';
		System.out.println("char to int:" + ConvertUtil.char2Int(value));
		System.out.println("char to byte:" + Arrays.toString(ConvertUtil.char2Byte(value)));
		System.out.println("toByte:" + Arrays.toString(ConvertUtil.toByte(value)));
		System.out.println("char to hex:" + ConvertUtil.char2Hex(value));
		System.out.println("char to bin:" + Integer.toBinaryString(value));
	}

	@Test
	public void intTest() {
		int value = 216;
		byte[] bytes = ByteBuffer.allocate(4).putInt(value).array();
		System.out.println(Arrays.toString(bytes));

		byte[] b = ConvertUtil.int2Byte(value);
		System.out.println(Arrays.toString(b));
		System.out.println("byte2Int:" + ConvertUtil.byte2Int(b));
		System.out.println("int to Ascii:" + ConvertUtil.int2Ascii(value));
		System.out.println("int to hex:" + Hex.toHexString(bytes));
		System.out.println("full hex:" + ConvertUtil.fullFormatHex(value));
		System.out.println("int to Hex:" + ConvertUtil.int2Hex(value));
		System.out.println("hex to int:" + ConvertUtil.hex2Int(ConvertUtil.int2Hex(value)));
		System.out.println("int to bin:" + ConvertUtil.int2Bin(value));
		System.out.println("bin to int:" + ConvertUtil.bin2Int(ConvertUtil.int2Bin(value)));
	}

	@Test
	public void longTest() {
		long value = 216;
		byte[] bytes = ByteBuffer.allocate(8).putLong(value).array();
		System.out.println(Arrays.toString(bytes));

		byte[] b = ConvertUtil.long2Byte(value);
		System.out.println(Arrays.toString(b));
		System.out.println("byte2Long:" + ConvertUtil.byte2Long(b));
		System.out.println("long to Ascii:" + ConvertUtil.long2Ascii(value));
		System.out.println("long to  hex:" + Hex.toHexString(bytes));
		System.out.println("full hex:" + ConvertUtil.fullFormatHex(value));
		System.out.println("long to Hex:" + ConvertUtil.long2Hex(value));
		System.out.println("hex to long:" + ConvertUtil.hex2Long(ConvertUtil.long2Hex(value)));
		System.out.println("long to bin:" + ConvertUtil.long2Bin(value));
		System.out.println("bin to long:" + ConvertUtil.bin2Long(ConvertUtil.long2Bin(value)));
	}

	@Test
	public void floatTest() {
		float value = 216;
		byte[] bytes = ByteBuffer.allocate(4).putFloat(value).array();
		System.out.println(Arrays.toString(bytes));

		byte[] b = ConvertUtil.float2Byte(value);
		System.out.println(Arrays.toString(b));
		System.out.println("byte2Float:" + ConvertUtil.byte2Float(b));
		System.out.println("float to Ascii:" + ConvertUtil.float2Ascii(value));
		System.out.println("float to  hex:" + Hex.toHexString(bytes));
		System.out.println("full hex:" + ConvertUtil.fullFormatHex(value));
		System.out.println("float to Hex:" + ConvertUtil.float2Hex(value));
		System.out.println("hex to float:" + ConvertUtil.hex2Float(ConvertUtil.float2Hex(value)));
		System.out.println("float to bin:" + ConvertUtil.float2Bin(value));
		System.out.println("bin to float:" + ConvertUtil.bin2Float(ConvertUtil.float2Bin(value)));

		float f = 100.9f;
		System.out.println(ConvertUtil.float2Hex(f));
		String floatHex = "42c9cccd";
		System.out.println(ConvertUtil.hex2Float(floatHex));

	}

	@Test
	public void doubleTest() {
		double value = 216;
		byte[] bytes = ByteBuffer.allocate(8).putDouble(value).array();
		System.out.println(Arrays.toString(bytes));

		byte[] b = ConvertUtil.double2Byte(value);
		System.out.println(Arrays.toString(b));
		System.out.println("byte2Double:" + ConvertUtil.byte2Double(b));
		System.out.println("double to Ascii:" + ConvertUtil.double2Ascii(value));
		System.out.println("double to hex:" + Hex.toHexString(bytes));
		System.out.println("full hex:" + ConvertUtil.fullFormatHex(value));
		System.out.println("double to Hex:" + ConvertUtil.double2Hex(value));
		System.out.println("hex to double:" + ConvertUtil.hex2Double(ConvertUtil.double2Hex(value)));
		System.out.println("float to bin:" + ConvertUtil.double2Bin(value));
		System.out.println("bin to float:" + ConvertUtil.bin2Double(ConvertUtil.double2Bin(value)));

		double d = 10293.234;
		System.out.println(ConvertUtil.double2Hex(d));
		String doubleHex = "40c41a9df3b645a2";
		System.out.println(ConvertUtil.hex2Double(doubleHex));
	}

	@Test
	public void stringTest() {
		String str = "POS88884";
		String strHex = "504F533838383834";
		System.out.println("to hex1:" + ConvertUtil.string2Hex(str));
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
