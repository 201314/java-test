package day06.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDemo {
	public static void main(String[] args) {
		 Pattern p = Pattern.compile("\\d{3,9}@(qq|sina|163)\\.com");
		 Matcher m = p.matcher("995876884@sina.com");
		 boolean b = m.matches();
		 System.out.println(b);
	}
}
