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
		File zip = new File("d:/trawe_store/play.7z");
		IOUtil.compressionFiles(files, zip);
	}

	@Test
	public void unZip() throws IOException {
		File zipFile = new File("d:/trawe_store/play.7z");
		File destFile = new File("d:/testDir/测试中文中文1");
		IOUtil.decompressionFiles(zipFile, destFile);
	}
	
	@Test
	public void tarFiles() throws IOException {
		File zipFile = new File("D:\\etc\\身份信息");
		File destFile = new File("D:\\etc\\身份信息.tar.gz");
		IOUtil.compressionFiles(zipFile, destFile);
	}	
}