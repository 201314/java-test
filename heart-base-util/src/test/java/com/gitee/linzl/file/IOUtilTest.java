package com.gitee.linzl.file;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * 文件压缩、解压功能
 * <p>
 * 使用了ant.jar包
 * 
 * @author linzl
 */
public class IOUtilTest {
	@Test
	public void zipFiles() throws IOException {
		File[] files = new File[] { new File("D:\\trawe_store\\play"), new File("D:\\trawe_store\\trawe_store.zip") };
		File zip = new File("d:/trawe_store/测试中文2.zip");
		IOUtil.zipFiles(files, zip);
	}

	@Test
	public void unZip() throws IOException {
		File zipFile = new File("d:/trawe_store/测试中文2.zip");
		File destFile = new File("d:/testDir/测试中文中文");
		IOUtil.unZipFiles(zipFile, destFile);
	}
}