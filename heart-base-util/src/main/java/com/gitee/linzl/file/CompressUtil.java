package com.gitee.linzl.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 文件压缩、解压功能
 * <p>
 * 使用了ant.jar包
 * 
 * @author linzl
 */
public class CompressUtil {
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
	 * @param rootName
	 *            压缩成功后，根文件夹的全路径,如windows环境下 D:/test,linux环境下 /var/local
	 * @param srcFiles
	 *            被压缩源文件
	 */
	private static void zipFiles(ZipOutputStream out, File[] srcFiles, String rootName) {
		if (!"".equalsIgnoreCase(rootName) && !rootName.endsWith("/")) {
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
					zipFiles(out, srcFiles[i].listFiles(), fileName);
				} else {
					out.putNextEntry(new ZipEntry(fileName));
					InputStream in = FileUtils.openInputStream(srcFiles[i]);

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
}
