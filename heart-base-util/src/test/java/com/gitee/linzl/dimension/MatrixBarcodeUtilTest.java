package com.gitee.linzl.dimension;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.google.zxing.WriterException;

/**
 * 关于条形码的校验算法 http://www.xdemo.org/barcode-validate/
 * <p>
 * 使用google 的Zxing
 * <p>
 * 使用举例
 * <p>
 * 条形码生成:
 * <p>
 * String content = "6923450656181";// 一般采用JSONObject格式的数据
 * <p>
 * BarCodeUtilutil = new BarCodeUtil(content, new File("D://barcode.png"));
 * <p>
 * util.createBarcode();
 * <p>
 * 二维码生成:
 * <p>
 * File targetPath1 = new File("D://zxing.png");
 * <p>
 * content = "网址https://baidu.com";// 一般采用JSONObject格式的数据
 * <p>
 * util = new BarCodeUtil(content, targetPath1, 0, 0);
 * <p>
 * util.createQRcode();
 * <p>
 * File logo = new File("D://taobao.png");
 * <p>
 * util.appendLogo(targetPath1, logo);
 * <p>
 * 依赖 core-3.2.1.jar \javase-3.2.1.jar
 * 
 * @author linzl
 */
public class MatrixBarcodeUtilTest {

	@Test
	public void createBarcode() throws WriterException, IOException {
		// 条形码生成
		// String content = "6923450656181";// 一般采用JSONObject的格式数据
		// TwoDimensionUtil util = new TwoDimensionUtil(content, new
		// File("D://barcode.png"));
		// util.createBarcode();
		// // 读取商品条形码、二维码
		System.out.println(MatrixBarcodeUtil.read(new File("D://testDir//barcode.gif"), null));
	}

	@Test
	public void createTwo() throws WriterException, IOException {
		// 二维码生成
		File targetPath = new File("D://testDir//zxing.png");
		FileUtils.forceMkdirParent(targetPath);
		String content = "网址q中国人民https://baidu.com";
		File logo = new File("D://testDir//taobao.png");
		MatrixBarcodeUtil util = new MatrixBarcodeUtil.WriteBuilder(content, targetPath).logo(logo).build();
		util.create();

		// 读取商品条形码、二维码
		System.out.println(MatrixBarcodeUtil.read(new File("D://testDir//zxing.png"), null));
	}
}
