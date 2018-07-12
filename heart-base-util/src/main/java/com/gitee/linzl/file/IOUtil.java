package com.gitee.linzl.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 文件压缩、解压功能
 * <p>
 * 使用了ant.jar包
 * 
 * @author linzl
 */
public class IOUtil {
	private static byte[] buf = new byte[1024];

	/**
	 * 压缩文件
	 * 
	 * @param srcFiles
	 *            需要压缩的文件
	 * @param targetZip
	 *            压缩后目标位置,如 D:/测试中文.zip
	 * @throws IOException
	 */
	public static void zipFiles(File[] srcFiles, File targetZip) throws IOException {
		zipFiles(srcFiles, targetZip, null);
	}

	/**
	 * 压缩文件
	 * 
	 * @param srcFiles
	 *            需要压缩的文件
	 * @param targetZip
	 *            压缩后目标位置,如 D:/测试中文.zip
	 * @param rootName
	 *            压缩成功后，根文件夹的全路径,如windows环境下 D:/test,linux环境下 /var/local
	 * @throws IOException
	 */
	public static void zipFiles(File[] srcFiles, File targetZip, String rootName) throws IOException {
		zipFiles(srcFiles, targetZip, rootName, null);
	}

	/**
	 * 压缩文件
	 * 
	 * @param srcFiles
	 *            需要压缩的文件
	 * @param targetZip
	 *            压缩后目标位置,如 D:/测试中文.zip
	 * @param rootName
	 *            压缩成功后，根文件夹的全路径,如windows环境下 D:/test,linux环境下 /var/local
	 * @param comment
	 *            压缩包说明
	 * @throws IOException
	 */
	public static void zipFiles(File[] srcFiles, File targetZip, String rootName, String comment) throws IOException {
		rootName = (rootName == null ? "/" : rootName.trim().replaceAll("\\\\", "/").replaceAll("//*", "/"));
		ZipOutputStream out = new ZipOutputStream(targetZip);
		zipFiles(out, srcFiles, rootName);
		comment = (comment == null ? "" : comment);
		out.setComment(comment);
		out.close();
	}

	/**
	 * 压缩文件
	 * 
	 * @param srcFiles
	 *            被压缩源文件
	 * @param rootName
	 *            压缩成功后，根文件夹的全路径,如windows环境下 D:/test,linux环境下 /var/local
	 */
	private static void zipFiles(ZipOutputStream out, File[] srcFiles, String rootName) {
		if (!"".equalsIgnoreCase(rootName) && !rootName.endsWith("/")) {
			rootName += "/";
		}

		try {
			for (int i = 0; i < srcFiles.length; i++) {
				String fileName = srcFiles[i].getName();
				fileName = rootName + fileName;
				if (srcFiles[i].isDirectory()) {
					fileName += "/"; // 标记为目录
					out.putNextEntry(new ZipEntry(fileName));
					zipFiles(out, srcFiles[i].listFiles(), fileName);
				} else {
					out.putNextEntry(new ZipEntry(fileName));
					InputStream in = FileUtils.openInputStream(srcFiles[i]);
					int len = 0;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}

					IOUtils.closeQuietly(in);
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
	 *            需要解压的压缩文件
	 * @param descDir
	 *            解压到指定目录
	 */
	@SuppressWarnings("rawtypes")
	public static void unZipFiles(File zipFile, File destDir) throws IOException {
		FileUtils.forceMkdir(destDir);

		try (ZipFile zip = new ZipFile(zipFile);) {
			Enumeration entries = zip.getEntries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();

				// 如果是文件夹，则创建完提前结束
				File file = new File(destDir.getPath(), entry.getName());
				if (entry.isDirectory()) {
					FileUtils.forceMkdir(file);
					continue;
				}

				try (InputStream in = zip.getInputStream(entry); OutputStream out = FileUtils.openOutputStream(file)) {
					int len = 0;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					IOUtils.closeQuietly(out);
					IOUtils.closeQuietly(in);
				}
			}
			ZipFile.closeQuietly(zip);
		}
	}

}
