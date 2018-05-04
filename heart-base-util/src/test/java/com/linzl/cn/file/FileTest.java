package com.linzl.cn.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class FileTest {

	// @Test
	// public void writeFile() throws IOException, InterruptedException {
	//
	// System.out.println(FileUtil.currentWorkDir);
	//
	// StringBuilder sb = new StringBuilder();
	//
	// long originFileSize = 1024 * 1024 * 100;// 100M
	// int blockFileSize = 1024 * 1024 * 5;// 15M
	//
	// // 生成一个大文件
	// for (int i = 0; i < originFileSize; i++) {
	// sb.append("A");
	// }
	//
	// String fileName = FileUtil.currentWorkDir + "origin.myfile.zip";
	// System.out.println(fileName);
	// System.out.println(FileUtil.write(fileName, sb.toString()));
	//
	// // 追加内容
	// sb.setLength(0);
	// sb.append("0123456789");
	// FileUtil.append(fileName, sb.toString());
	//
	// FileUtil fileUtil = new FileUtil();
	//
	// // 将origin.myfile拆分
	// fileUtil.splitBySize(new File(fileName), blockFileSize);
	//
	// Thread.sleep(10000);// 稍等10秒，等前面的小文件全都写完
	//
	// // 合并成新文件
	// fileUtil.synMergeFiles(FileUtil.currentWorkDir, ".part", blockFileSize,
	// FileUtil.currentWorkDir + "new.myfile");
	// Thread.sleep(10000);// 稍等10秒，等前面的小文件全都写完
	// System.out.println("等全部删除完");
	// }

	@Test
	public void testSplitFile() throws IOException {
		FileUtil fileUtil = new FileUtil();

		// String fileName = "D:\\trawe_store\\trawe_store.zip";
		// try {
		// fileUtil.splitBySize(new File(fileName), 1024 * 10);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		//
		// try {
		// Thread.sleep(10000);// 稍等10秒，等前面的小文件全都写完
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }

		// 合并成新文件
		fileUtil.synMergeFiles2("D:\\trawe_store", ".part", 1024 * 10, "D:\\trawe_store\\new.myfile.zip");
		
	}
}