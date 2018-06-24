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
public class DecompressionUtil {
	private static byte[] buf = new byte[1024];

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
				int len = 0;
				ZipEntry entry = (ZipEntry) entries.nextElement();
				String outPath = (destDir.getPath() + "/" + entry.getName()).replaceAll("\\\\", "/").replaceAll("//*",
						"/");

				// 判断路径是否存在,不存在则创建文件路径
				File parentDir = new File(outPath.substring(0, outPath.lastIndexOf('/')));
				FileUtils.forceMkdir(parentDir);

				// 判断文件全路径是否为文件夹,如果是上面已经创建,不需要解压
				if (new File(outPath).isDirectory()) {
					continue;
				}

				try (InputStream in = zip.getInputStream(entry);
						OutputStream out = FileUtils.openOutputStream(new File(outPath));) {
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