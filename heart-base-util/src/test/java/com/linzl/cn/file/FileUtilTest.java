package com.linzl.cn.file;

import java.io.File;
import java.io.IOException;

import com.linzl.cn.file.FileUtilss;

/**
 * 文件压缩、解压功能
 * <p>
 * 使用了ant.jar包
 * 
 * @author linzl
 */
public class FileUtilTest {
	// @Test
	public void zipFiles() throws IOException {
		/**
		 * 压缩文件
		 */
		File[] files = new File[] { new File("d:/testDir/22"), new File("d:/testDir/中文.png") };
		File zip = new File("d:/testDir/测试中文2.zip");
		FileUtilss.zipFiles(files, zip);
		String str = "11/adsf////we\\\\asd";
		str = str.replaceAll("\\\\", "/");
		str = str.replaceAll("//*", "/");
		System.out.println(str);
	}

	// @Test
	public void unZip() throws IOException {
		/**
		 * 解压文件
		 */
		File zipFile = new File("d:/testDir/测试中文2.zip");
		File destFile = new File("d:/testDir/测试中文中文");
		FileUtilss.unZipFiles(zipFile, destFile);
	}
}