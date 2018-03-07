package day04.file;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class Test {
	public static void main(String[] args) throws IOException {
		File file = new File("D:/test");
		String[] str = file.list();
		for (String string : str) {
			System.out.println(string);
		}
		String stre = ".*\\.exe";
		System.out.println("asdf.exe".matches(stre));
		System.out.println(Pattern.matches("a*b", "aaaaab"));
	}

}
