package com.linzl.cn.dimension;

import java.io.File;

import org.junit.Test;

import com.linzl.cn.dimension.OneDimensionUtil;

/**
 * 条形码工具类
 *
 * @author linzl
 * @createDate 2016年8月31日
 *
 */
public class BarcodeTest {
	@Test
	public void generateBarcode() {
		String msg = "00000000068";
		String basePath = this.getClass().getResource("/").getFile();
		String path = basePath + "/com/utea/dimension/barcode.gif";
		OneDimensionUtil.toBarcode(OneDimensionUtil.CodeType.UPCA, msg, new File(path));
	}
}