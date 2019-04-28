package com.gitee.linzl.network;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FtpClientUtilTest {
	@Test
	public void mkdir() {
		try (FtpClientUtil util = new FtpClientUtil("127.0.0.1", "linzl", "linzl");) {
			util.connect();
			util.mkdir("/test/", new File("hello/who/are/you"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void mkdirParent() {
		try (FtpClientUtil util = new FtpClientUtil("127.0.0.1", "linzl", "linzl");) {
			util.connect();
			util.mkdirParent(new File("/hello1/who1/are1/you"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void listFileNames() {
		try (FtpClientUtil util = new FtpClientUtil("127.0.0.1", "linzl", "linzl");) {
			util.connect();
			String[] file = util.listFileNames("/test");
			System.out.println(Arrays.toString(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void listFiles() {
		try (FtpClientUtil util = new FtpClientUtil("127.0.0.1", "linzl", "linzl");) {
			util.connect();
			List<String> list = util.listFiles("/test", false);
			System.out.println(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void reName() {
		try (FtpClientUtil util = new FtpClientUtil("127.0.0.1", "linzl", "linzl");) {
			util.connect();
			util.changeWorkingDirectory("/test/新建文件夹");
			util.reName("测试修改名称", "测试修改名称.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void delFile() {
		try (FtpClientUtil util = new FtpClientUtil("127.0.0.1", "linzl", "linzl");) {
			util.changeWorkingDirectory("/test/新建文件夹");
			util.deleteFile("测试修改名称.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void upload() throws Exception {
		try (FtpClientUtil util = new FtpClientUtil("127.0.0.1", "linzl", "linzl");) {
			util.upload("/test/新建文件夹", new File("D:\\etc\\身份信息\\test22.jpg"), "hello.jpg");
			util.upload("/test/新建文件夹", new File("D:\\etc\\身份信息\\consumehello.txt"), "111.txt");
			util.upload("/test/新建文件夹", new File("D:\\etc\\身份信息\\Linux常用命令.doc"), "222.doc");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void uploadDir() throws FileNotFoundException {
		try (FtpClientUtil util = new FtpClientUtil("127.0.0.1", "linzl", "linzl");) {
			util.upload("/test/", new File("D:\\etc\\身份信息"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void uploadFile() throws FileNotFoundException {
		try (FtpClientUtil util = new FtpClientUtil("127.0.0.1", "linzl", "linzl");) {
			util.upload("/test/", new File("D:\\etc\\身份信息\\123_20190308_vehicle_head.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void downloadAll() {
		try (FtpClientUtil util = new FtpClientUtil("127.0.0.1", "linzl", "linzl");) {
			util.connect();
			util.downloadAll("/ConsumptionFiles", new File("D://etc//localFtp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void downloadStartWith() {
		try (FtpClientUtil util = new FtpClientUtil("127.0.0.1", "linzl", "linzl");) {
			util.connect();
			util.downloadStartWith("/ConsumptionFiles", new File("D://etc//localFtp"), "105_20180102_1514823600100");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void downloadEndWith() {
		try (FtpClientUtil util = new FtpClientUtil("127.0.0.1", "linzl", "linzl");) {
			util.connect();
			util.downloadEndWith("/ConsumptionFiles", new File("D://etc//localFtp"), "TB_DisputeData.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void downloadEqualWith() {
		try (FtpClientUtil util = new FtpClientUtil("127.0.0.1", "linzl", "linzl");) {
			util.connect();
			util.downloadEqualWith("/ConsumptionFiles", new File("D://etc//localFtp"),
					"105_20180102_1514823600100_TB_SettlementData.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void downloadMatchWith() {
		try (FtpClientUtil util = new FtpClientUtil("127.0.0.1", "linzl", "linzl");) {
			util.connect();
			util.downloadMatchWith("/ConsumptionFiles", new File("D://etc//localFtp"),
					"105_(\\d{8})_(\\d{13})_TB_DisputeData.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void downloadDir() {
		try (FtpClientUtil util = new FtpClientUtil("127.0.0.1", "linzl", "linzl");) {
			util.connect();
			util.download("/11", new File("D://etc//localFtp"), null, null, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 断点下载续传
	 */
	@Test
	public void breakDownload() {
		try (FtpClientUtil util = new FtpClientUtil("127.0.0.1", "linzl", "linzl");) {
			util.connect();
			util.download("/11/中国人民.iso", new File("D://etc//localFtp"), null, null, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 断点下载 TODO
	 */
	@Test
	public void breakUpload() {
		try (FtpClientUtil util = new FtpClientUtil("127.0.0.1", "linzl", "linzl");) {
			util.connect();
			util.upload("/11", new File("D:\\etc\\localFtp\\中国人民.iso"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
