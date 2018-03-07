package day04.file;

import java.io.File;

public class ReNameToUpper {

	public static void main(String[] args) {
		File file = new File("D:/test/cc");
		String[] l = file.list();
		File file1 = new File("D:/test/DDD");
		for (int i = 0; i < l.length; i++) {
			System.out.println(l[i]);
		}

		System.out.println(file.renameTo(file1));
	}

}
