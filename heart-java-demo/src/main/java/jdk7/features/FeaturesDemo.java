package jdk7.features;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * @description
 * 
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年4月24日
 */
public class FeaturesDemo {

	@Test
	/**
	 * 二进制变量的表示,支持将整数类型用二进制来表示，用0b开头
	 */
	public void binary() {
		int x = 0b01011;// 0b用来表示二进制
		System.out.println(x);
	}

	@Test
	/**
	 * 可以一次性catch多个Exception
	 */
	public void exception() {
		try {
			test();
			test1();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void test() throws SQLException {
	}

	public static void test1() throws IOException {
	}

	@Test
	/**
	 * JDK7开始，文件流只要实现了AutoCloseable、Flushable，JVM会自动调用close和flush。
	 * 文件流的创建，必须写在try()括号中，否则要自己手动close和flush
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void writeToFileZipFileContents() throws FileNotFoundException, IOException {
		String source = "D:\\my.zip";
		String target = "D:\\ext.zip";

		try (InputStream is = new FileInputStream(new File(source));
				OutputStream os = new FileOutputStream(new File(target))) {
			byte[] bb = new byte[is.available()];
			is.read(bb);
			os.write(bb);
		}
	}

	@Test
	/**
	 * Switch语句支持string类型
	 */
	public void switchString() {
		String test = "hello";
		switch (test) {
		case "world":
			System.out.println("我是word");
			break;
		case "hello":
			System.out.println("我是hello");
			break;
		default:
			break;
		}
	}

	@Test
	public void testGeneric() {
		// 7之前
		Map<String, List<String>> oldMap = new HashMap<String, List<String>>();
		// JDK7之后
		Map<String, List<String>> newMap = new HashMap<>();
	}

	@Test
	/**
	 * * 数字可以用下划线表示,这样看起来更人性化，直观
	 * </p>
	 * <strong>但是在以下情况下不允许添加下划线</strong>
	 * </p>
	 * At the beginning or end of a number:在第一个后或最后一数字后不能添加下划线
	 * </p>
	 * Adjacent to a decimal point in a floating point literal:浮点数中的小数点相邻位置不能使用下划线
	 * </p>
	 * Prior to an F or L suffix:在F或L后缀之前不能使用下划线
	 * </p>
	 * In positions where a string of digits is expected:
	 */
	public void testNum() {
		long creditCardNumber = 1234_5678_9012_3456L;
		System.out.println(creditCardNumber);
		long socialSecurityNumber = 999_99_9999L;
		float pi = 3.14_15F;
		long hexBytes = 0xFF_EC_DE_5E;
		long hexWords = 0xCAFE_BABE;
		long maxLong = 0x7fff_ffff_ffff_ffffL;
		byte nybbles = 0b0010_0101;
		long bytes = 0b11010010_01101001_10010100_10010010;
	}
}
