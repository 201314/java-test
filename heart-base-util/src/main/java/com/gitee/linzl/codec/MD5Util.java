package com.gitee.linzl.codec;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 依赖commons-codec-1.3-dev.jar 这个包可以MD5加密不可逆，MD2加密，SHA加密
 * 
 * @author linzl 最后修改时间：2014年9月10日
 */
public class MD5Util {

	static MessageDigest MD5 = null;

	static {
		try {
			MD5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ne) {
			ne.printStackTrace();
		}
	}

	/**
	 * 对一个文件获取md5值
	 * 
	 * @return md5串
	 */
	public static String getMD5(File file) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			byte[] buffer = new byte[8192];
			int length;
			while ((length = fileInputStream.read(buffer)) != -1) {
				MD5.update(buffer, 0, length);
			}

			return new String(Hex.encodeHex(MD5.digest()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				fileInputStream = null;
			}
		}
	}

	/**
	 * 对一个文件获取md5值
	 * 
	 * @return md5串
	 */
	public static String MD5File(File file) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(file);
		return DigestUtils.md5Hex(fileInputStream);
	}

	/**
	 * 求一个字符串的md5值
	 * 
	 * @param target
	 *            字符串
	 * @return md5 value
	 */
	public static String MD5(String target) {
		return DigestUtils.md5Hex(target);
	}
	

	public static void main(String[] args) throws IOException {
		File fileZIP = new File("D:\\S0001_全国版.cfg");
		String md5 = MD5File(fileZIP);
		System.out.println("fileZIP->MD5:" + md5);

		String bb = DigestUtils.md5Hex(new FileInputStream(fileZIP));
		System.out.println(bb);
	}
}
