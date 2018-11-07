package com.gitee.linzl.cipher.message;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.util.encoders.Hex;

/**
 * 依赖commons-codec-xxx.jar 这个包可以MD5加密不可逆，MD2加密，SHA加密
 * 
 * @author linzl 最后修改时间：2014年9月10日
 */
@SuppressWarnings("deprecation")
public class DigestUtilsExt extends DigestUtils {
	/**
	 * 对一个文件获取md5值
	 * 
	 * @return md5串
	 */
	public static String md5Hex(File file) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(file);
		return md5Hex(fileInputStream);
	}

	/**
	 * 静态私有盐
	 */
	private static String staticPrivateSalt = "linzl@163";

	/**
	 * sha256 加密密码
	 * 
	 * @param plainPass
	 *            需要加密的密码
	 * @param randomSalt
	 *            随机盐 Use {@code getRandomSalt()}
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] sha256(String plainPass, String randomSalt) {
		byte[] salt = ArrayUtils.addAll(staticPrivateSalt.getBytes(), randomSalt.getBytes());
		MessageDigest digest = DigestUtils.getSha256Digest();
		digest.reset();
		digest.update(salt);
		return digest.digest(plainPass.getBytes());
	}

	public static String sha256Hex(String plainPass, String randomSalt) {
		return Hex.toHexString(sha256(plainPass, randomSalt));
	}

	/**
	 * 获取随机盐
	 * 
	 * @return
	 */
	public static String getRandomSalt() {
		byte[] salt = new byte[32];
		try {
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			sr.nextBytes(salt);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return Hex.toHexString(salt);
	}

}
