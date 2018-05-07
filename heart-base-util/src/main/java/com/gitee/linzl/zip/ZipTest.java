package com.gitee.linzl.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 使用了ant.jar包
 */
public class ZipTest {
	private static byte[] buf = new byte[1024];

	/**
	 * 压缩文件
	 * 
	 * @param targetZip
	 * @param srcFiles
	 *            需要压缩的文件
	 * @throws IOException
	 */
	public static void zipFiles(File targetZip, File... srcFiles)
			throws IOException {
		zipFiles(targetZip, "", srcFiles);
	}

	/**
	 * 压缩文件
	 * 
	 * @param srcFiles
	 *            需要压缩的文件
	 * @param targetZip
	 * @param rootName
	 *            压缩成功后，根文件夹的名称
	 * @throws IOException
	 */
	public static void zipFiles(File targetZip, String rootName,
			File... srcFiles) throws IOException {
		rootName = (rootName == null ? "/" : rootName.trim());
		rootName = rootName.replaceAll("\\\\", "/");
		ZipOutputStream out = new ZipOutputStream(targetZip);
		zipFiles(out, rootName, srcFiles);
		out.close();
	}

	/**
	 * 压缩文件
	 * 
	 * @param zipFile
	 *            zip文件
	 * @param rootName
	 *            压缩成功后，根文件夹的名称
	 * @param srcFiles
	 *            被压缩源文件
	 */
	private static void zipFiles(ZipOutputStream out, String rootName,
			File... srcFiles) {
		if (!rootName.endsWith("/") && !rootName.equalsIgnoreCase("")) {
			rootName += "/";
		}
		try {
			int len = 0;
			for (int i = 0; i < srcFiles.length; i++) {
				String fileName = srcFiles[i].getName();
				fileName = rootName + fileName;
				if (srcFiles[i].isDirectory()) {
					fileName += "/";
					out.putNextEntry(new ZipEntry(fileName));
					zipFiles(out, fileName, srcFiles[i].listFiles());
				} else {
					out.putNextEntry(new ZipEntry(fileName));
					InputStream in = new FileInputStream(srcFiles[i]);

					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					in.close();
					out.closeEntry();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解压文件到指定目录
	 * 
	 * @param zipFile
	 * @param descDir
	 */
	@SuppressWarnings("rawtypes")
	public static void unZipFiles(File zipFile, File destDir)
			throws IOException {
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		ZipFile zip = new ZipFile(zipFile);
		Enumeration entries = zip.getEntries();

		ZipEntry entry = null;
		InputStream in = null;
		OutputStream out = null;
		int len = 0;

		while (entries.hasMoreElements()) {
			entry = (ZipEntry) entries.nextElement();
			String outPath = (destDir.getPath() + "/" + entry.getName());
			outPath = outPath.replaceAll("\\\\", "/");
			System.out.println("outPath===>" + outPath);
			// 判断路径是否存在,不存在则创建文件路径
			File parentDir = new File(outPath.substring(0,
					outPath.lastIndexOf('/')));

			if (!parentDir.exists()) {
				parentDir.mkdirs();
			}

			// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if (new File(outPath).isDirectory()) {
				continue;
			}
			// 输出文件路径信息
			System.out.println(outPath);

			in = zip.getInputStream(entry);
			out = new FileOutputStream(outPath);

			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		}
	}

	public static void main(String[] args) throws IOException {
		/**
		 * 压缩文件
		 */
		// File[] files = new File[] { new File("d:/11/22"),
		// new File("d:/11.dgrp") };
		// File zip = new File("d:/22.zip");
		// zipFiles(zip, files);

		/**
		 * 解压文件
		 */
		File zipFile = new File("d:/11.zip");
		File destFile = new File("d:/33/");
		unZipFiles(zipFile, destFile);
	}

}