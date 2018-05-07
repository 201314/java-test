package com.gitee.linzl.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @description 会出现中文乱码
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2017年11月29日
 */
public class JDKZip {
	private static byte[] buf = new byte[1024];

	private void zip(File inputFile, File outputFile) throws Exception {
		OutputStream fos = new FileOutputStream(outputFile);
		ZipOutputStream out = new ZipOutputStream(fos);
		zip(out, inputFile, "");
		out.close();
	}

	private void zip(ZipOutputStream out, File fileList, String rootName) throws Exception {
		rootName = (rootName == null ? "" : rootName.trim());
		rootName = new String(rootName.getBytes(), "GBK");
		if (!rootName.endsWith("/") && !rootName.equalsIgnoreCase("")) {
			rootName += "/";
		}

		if (fileList.isDirectory()) {
			File[] files = fileList.listFiles();
			String srcPath = fileList.getName();
			srcPath = srcPath.replaceAll("\\\\", "/");
			if (!srcPath.endsWith("/")) {
				srcPath += "/";
			}
			System.err.println("压缩目录--->" + rootName + srcPath);
			out.putNextEntry(new ZipEntry(rootName + srcPath));

			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					zip(out, files[i], rootName + srcPath);
				} else {
					String name = files[i].getName();
					System.err.println("文件名称--》" + name);
					zip(out, files[i], rootName + srcPath + name);
				}
			}
		} else {
			out.putNextEntry(new ZipEntry(rootName));
			FileInputStream in = new FileInputStream(fileList);
			int readLength = 0;
			while ((readLength = in.read(buf)) != -1) {
				out.write(buf, 0, readLength);
				out.flush();
			}
			in.close();
		}
	}

	public void unzip(File file, String destDir) throws ZipException, IOException {
		ZipFile zipFile = new ZipFile(file);// 实例化ZipFile，每一个zip压缩文件都可以表示为一个ZipFile
		// 实例化一个Zip压缩文件的ZipInputStream对象，可以利用该类的getNextEntry()方法依次拿到每一个ZipEntry对象
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
		ZipEntry zipEntry = null;
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			String fileName = zipEntry.getName();
			File temp = new File(destDir + fileName);
			// 解压文件，不是目录则创建父文件夹
			if (!zipEntry.isDirectory()) {
				if (!temp.getParentFile().exists())
					temp.getParentFile().mkdirs();
			} else {
				// 是目录，则判断是否已经存在
				if (!temp.exists()) {
					temp.mkdirs();
				}
				continue;
			}
			OutputStream os = new FileOutputStream(temp);
			// 通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流
			InputStream is = zipFile.getInputStream(zipEntry);

			int readLength = 0;
			while ((readLength = is.read(buf)) != -1) {
				os.write(buf, 0, readLength);
				os.flush();
			}
			os.close();
			is.close();
		}
		zipInputStream.close();
	}

	public static void main(String[] temp) {
		// 使用java的压缩类时注意，中文文件名压缩会乱码，解压出来正常
		JDKZip book = new JDKZip();
		try {
			String inputFileName = "D:\\11"; // 你要压缩的文件夹
			String zipFileName = "d:/22.zip"; // 打包后文件名字
			book.zip(new File(inputFileName), new File(zipFileName));

			// File zipFile = new File(zipFileName);
			// book.unzip(zipFile, "d:/");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}