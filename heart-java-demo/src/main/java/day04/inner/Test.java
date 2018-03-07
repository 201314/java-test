package day04.inner;

import java.util.Date;

/**
 * 泛型使用
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年1月10日
 */
public class Test<T> {
	public void printType(T t) {
		System.out.println(t.toString());
	}

	public static void main(String[] args) throws Exception {
		Test<Date> test = new Test<Date>();
		Date d = new Date();
		test.printType(d);

		Test<String> test2 = new Test<String>();
		String str = "hello";
		test2.printType(str);
	}

}
