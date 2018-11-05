package com.gitee.linzl.crypto.message;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 依赖commons-codec-1.3-dev.jar 这个包可以MD5加密不可逆，MD2加密，SHA加密
 * 
 * @author linzl 最后修改时间：2014年9月10日
 */
public class MD5Util {
	/**
	 * 对一个文件获取md5值
	 * 
	 * @return md5串
	 */
	public static String md5Hex(File file) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(file);
		return DigestUtils.md5Hex(fileInputStream);
	}

	public static void main(String[] args) throws IOException {
		File fileZIP = new File("D:\\S0001_全国版.cfg");
		String bb = MD5Util.md5Hex(fileZIP);
		System.out.println(bb);
	}
}
