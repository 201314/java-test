package jdk7.features;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 可以一次性catch多个Exception
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年4月24日
 */
public class ExceptionDemo {
	public static void main(String[] args) {
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
}
