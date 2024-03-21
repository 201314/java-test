package com.gitee.linzl.dimension;

import java.io.File;

import org.junit.Test;

/**
 * 条形码工具类
 *
 * @author linzl
 * @createDate 2016年8月31日
 *
 */
public class LinearBarcodeTest {
	@Test
	public void generateBarcode() { 
		String msg = "00000000068";
		String basePath = this.getClass().getResource("/").getFile();
		String path = basePath + "/com/utea/dimension/barcode.gif";
		System.out.println(path);
		LinearBarcodeUtil.toBarcode(LinearBarcodeUtil.CodeType.UPCA, msg, new File(path));
	}
}