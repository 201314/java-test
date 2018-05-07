package com.gitee.linzl.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class ExtractZip {
	public static final int BUFFER_SIZE = 1024;

	/**
	 * 解压 zip 文件
	 * 
	 * @param zipFile
	 *            zip 压缩文件
	 * @param destDir
	 *            zip 压缩文件解压后保存的目录
	 * @return 返回 zip 压缩文件里的文件名的 list
	 * @throws Exception
	 */
	public static List<String> unZip(File zipFile, String destDir) throws Exception {
		// 如果 destDir 为 null, 空字符串, 或者全是空格, 则解压到压缩文件所在目录
		if (StringUtils.isBlank(destDir)) {
			destDir = zipFile.getParent();
		}

		destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
		ZipArchiveInputStream is = null;
		List<String> fileNames = new ArrayList<String>();

		try {
			is = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipFile), BUFFER_SIZE), "GBK");
			ZipArchiveEntry entry = null;

			while ((entry = is.getNextZipEntry()) != null) {
				fileNames.add(entry.getName());

				if (entry.isDirectory()) {
					File directory = new File(destDir, entry.getName());
					directory.mkdirs();
				} else {
					OutputStream os = null;
					try {
						os = new BufferedOutputStream(new FileOutputStream(new File(destDir, entry.getName())),
								BUFFER_SIZE);
						IOUtils.copy(is, os);
					} finally {
						IOUtils.closeQuietly(os);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			IOUtils.closeQuietly(is);
		}

		return fileNames;
	}

	/**
	 * 解压 zip 文件
	 * 
	 * @param zipfile
	 *            zip 压缩文件的路径
	 * @param destDir
	 *            zip 压缩文件解压后保存的目录
	 * @return 返回 zip 压缩文件里的文件名的 list
	 * @throws Exception
	 */
	public static List<String> unZip(String zipfile, String destDir) throws Exception {
		File zipFile = new File(zipfile);
		return unZip(zipFile, destDir);
	}

	public static void main(String[] args) throws Exception {
		List<String> names = unZip("D:\\1122.zip", "D:\\trawe_store\\");
		System.out.println(names);
	}
}