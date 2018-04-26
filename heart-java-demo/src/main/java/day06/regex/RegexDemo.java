package day06.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDemo {
	public static void test() {
		String regex = "http://\\d{3}\\.\\d{3}\\.\\d{1}\\.\\d{3}";
		System.out.println("http://192.168.9.151".matches(regex));
		String regex1 = "eee*\\.exe";
		System.out.println(".exe".matches(regex1));
	}

	public static void main(String[] args) {
		Pattern p = Pattern.compile("\\d{3,9}@(qq|sina|163)\\.com");
		Matcher m = p.matcher("995876884@sina.com");
		boolean b = m.matches();
		System.out.println(b);
	}
}
