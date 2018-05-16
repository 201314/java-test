package day06.file.stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class FileStreamDemo {
	public void copy(File file) throws Exception {
		File target = new File("D:\\hello.txt");
		try (InputStream is = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(is, "UTF-8");
				OutputStream os = new FileOutputStream(target);
				PrintWriter pw = new PrintWriter(os);) {

			BufferedReader buf = new BufferedReader(isr);
			String str = buf.readLine();
			StringBuffer sb = new StringBuffer();

			while (str != null) {
				sb.append(str).append("\r\n");
				str = buf.readLine();
			}
			pw.write(sb.toString());
		}
	}

	public void writeTo(File file) {
		try (OutputStream os = new FileOutputStream(file, true);

				PrintWriter pw = new PrintWriter(os);) {
			String str = "who are you?";
			pw.write(str);
		} catch (Exception e) {

		}
	}

	public void readAndWrite(File file) throws Exception {
		try (InputStream is = new FileInputStream(file);

				InputStreamReader isr = new InputStreamReader(is, "UTF-8");) {
			char[] b = new char[(int) (file.length()) / 2];
			while (true) {
				if (isr.read(b) != -1) {
					String str = new String(b);
					System.out.println(str);
				} else {
					break;
				}
			}
		}
	}

	public void readLine(File file) throws Exception {
		try (InputStream is = new FileInputStream(file);

				InputStreamReader isr = new InputStreamReader(is, "UTF-8");) {
			BufferedReader buf = new BufferedReader(isr);
			String str = buf.readLine();

			while (str != null) {
				System.out.println(str);
				str = buf.readLine();
			}
		}
	}
}
