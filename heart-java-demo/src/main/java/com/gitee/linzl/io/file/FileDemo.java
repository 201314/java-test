package com.gitee.linzl.io.file;

import java.io.File;
import java.io.IOException;

public class FileDemo {

	public void create() {
		File file1 = new File("D:\\javaTest\\fileDelete.txt"); // 文件路径
		// 创建文件
		if (!file1.exists()) {
			try {
				file1.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			file1.delete();// 文件存在就删除
		}

		// 创建文件夹
		File file3 = new File("D:\\javaTest\\mkdirs\\directory");// 创建多级目录(和创建单级目录一样)

		if (!file3.exists()) {
			System.out.println(file3.mkdir());// 开始创建
			if (file3.isDirectory()) {
				System.out.println("证明创建成功");
			}
		}
	}

	public void delete(File file) {
		File[] filePath = file.listFiles();

		for (File file2 : filePath) {
			if (file2.isFile()) {// 是文件就打印出来
				file2.delete();
			} else { // 如果是文件夹，递归继续遍历
				delete(file2);
			}
		}
		file.delete();// 最后把自己也删除
	}

	public void listFiles(File file) {// 遍历路径,并打印出路径名称
		File[] filePath = file.listFiles();

		for (File file2 : filePath) {
			if (file2.isFile())// 是文件就打印出来
				System.out.println(file2);
			else { // 如果是文件夹，递归继续遍历
				System.out.println(file2 + "------------");
				listFiles(file2);
			}
		}
	}

}
