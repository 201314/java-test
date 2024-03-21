package com.gitee.linzl.file;

import org.junit.Test;

import java.io.File;

public class ZipUtilTest {

	@Test
	public void addPwd() {
		ZipUtil.zip(new File("D:\\test"), "124");
	}
}
