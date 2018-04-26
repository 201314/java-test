package jdk7.features;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * JDK7开始，文件流只要实现了AutoCloseable、Flushable，JVM会自动调用close和flush。
 * 文件流的创建，必须写在try()括号中，否则要自己手动close和flush
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年4月24日
 */
public class IODemo {

	public static void writeToFileZipFileContents(String source, String target)
			throws FileNotFoundException, IOException {

		try (InputStream is = new FileInputStream(new File(source));
				OutputStream os = new FileOutputStream(new File(target))) {
			byte[] bb = new byte[is.available()];
			is.read(bb);
			os.write(bb);
		}
	}

	public static void main(String[] args) {
		try {
			writeToFileZipFileContents("D:\\my.zip", "D:\\ext.zip");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
