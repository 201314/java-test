package com.gitee.linzl.codec;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;

import org.apache.commons.io.FileUtils;

/**
 * 
 * @author linzl 最后修改时间：2014年8月11日
 */
public class Base64Util {
	/**
	 * base64 编码文件
	 * 
	 * @param filePath 文件路径
	 * @return
	 * @throws IOException
	 */
	public static String encode(File file) throws IOException {
		byte[] fileByte = FileUtils.readFileToByteArray(file);
		return Base64.getEncoder().encodeToString(fileByte);
	}

	/**
	 * base64 解码文件
	 * 
	 * @param targetPath 文件解码目标路径
	 * @param fileStr    编码后的文件内容
	 * @throws IOException
	 */
	public static void decode(String targetPath, String fileStr) throws IOException {
		byte[] fileByte = Base64.getDecoder().decode(fileStr);
		FileUtils.writeByteArrayToFile(new File(targetPath), fileByte);
	}

	public static void main(String[] args) throws Exception {
		String content = FileUtils.readFileToString(new File("F:\\anjianOAdoc\\oadoc\\taitan\\missive\\副本.xml"),
				Charset.forName("UTF-8"));
		decode("F:\\anjianOAdoc\\oadoc\\taitan\\missive\\测试标题_4920.pdf", content);
	}
}
