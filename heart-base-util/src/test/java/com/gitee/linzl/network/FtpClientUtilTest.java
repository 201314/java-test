package com.gitee.linzl.network;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FtpClientUtilTest {
	FtpClientUtil util = null;

	@Before
	public void init() {
		util = new FtpClientUtil("127.0.0.1", "linzl", "linzl");
		util.init();
	}

	@Test
	public void mkdir() {
		util.mkdir("/test/", new File("hello/who/are/you"));
		util.close();
	}

	@Test
	public void mkdirParent() {
		util.mkdirParent(new File("/hello1/who1/are1/you"));
		util.close();
	}

	@Test
	public void listFileNames() {
		String[] file = util.listFileNames("/test");
		System.out.println(Arrays.toString(file));
		util.close();
	}

	@Test
	public void listFiles() {
		List<String> list = util.listFiles("/test", false);
		util.close();
		System.out.println(list);
	}

	@Test
	public void reName() {
		util.changeWorkingDirectory("/test/新建文件夹");
		util.reName("测试修改名称", "测试修改名称.txt");
		util.close();
	}

	@Test
	public void delFile() {
		util.changeWorkingDirectory("/test/新建文件夹");
		util.deleteFile("测试修改名称.txt");
		util.close();
	}

	@Test
	public void upload() throws Exception {
		util.upload("/test/新建文件夹", new File("D:\\etc\\身份信息\\test22.jpg"), "hello.jpg");
		util.upload("/test/新建文件夹", new File("D:\\etc\\身份信息\\consumehello.txt"), "111.txt");
		util.upload("/test/新建文件夹", new File("D:\\etc\\身份信息\\Linux常用命令.doc"), "222.doc");
		util.close();
	}

	@Test
	public void uploadDir() throws FileNotFoundException {
		util.upload("/test/", new File("D:\\etc\\身份信息"));
		util.close();
	}

	@Test
	public void uploadFile() throws FileNotFoundException {
		util.upload("/test/", new File("D:\\etc\\身份信息\\123_20190308_vehicle_head.jpg"));
		util.close();
	}

	@Test
	public void downloadAll() {
		util.downloadAll("/ConsumptionFiles", new File("D://etc//localFtp"));
		util.close();
	}

	@Test
	public void downloadStartWith() {
		util.downloadStartWith("/ConsumptionFiles", new File("D://etc//localFtp"), "105_20180102_1514823600100");
		util.close();
	}

	@Test
	public void downloadEndWith() {
		util.downloadEndWith("/ConsumptionFiles", new File("D://etc//localFtp"), "TB_DisputeData.txt");
		util.close();
	}

	@Test
	public void downloadEqualWith() {
		util.downloadEqualWith("/ConsumptionFiles", new File("D://etc//localFtp"),
				"105_20180102_1514823600100_TB_DisputeData.txt");
		util.close();
	}

	@Test
	public void downloadMatchWith() {
		util.downloadMatchWith("/ConsumptionFiles", new File("D://etc//localFtp"),
				"105_(\\d{8})_(\\d{13})_TB_DisputeData.txt");
		util.close();
	}
}
