package com.linzl.cn.dimension;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.zxing.WriterException;
import com.linzl.cn.dimension.ZxingUtil;

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
public class ZxingUtilTest {

	@Test
	public void createBarcode() throws WriterException, IOException {
		// 条形码生成
		// String content = "6923450656181";// 一般采用JSONObject的格式数据
		// TwoDimensionUtil util = new TwoDimensionUtil(content, new
		// File("D://barcode.png"));
		// util.createBarcode();
		// // 读取商品条形码、二维码
		System.out.println(ZxingUtil.read(new File("D://testDir//barcode.gif")));
	}

	@Test
	public void createTwo() throws WriterException, IOException {
		// 二维码生成
		File targetPath = new File("D://testDir//zxing.png");
		String content = "网址https://baidu.com";
		ZxingUtil util = new ZxingUtil(content, targetPath, 0, 0);
		util.createQRcode();
		File logo = new File("D://testDir//taobao.png");
		util.addQRLogo(targetPath, logo);

		// 读取商品条形码、二维码
		System.out.println(ZxingUtil.read(new File("D://testDir//zxing.png")));
	}
}
