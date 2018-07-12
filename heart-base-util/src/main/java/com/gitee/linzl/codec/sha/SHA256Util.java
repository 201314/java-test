package com.gitee.linzl.codec.sha;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 其他加密方式，可以使用commons-codec-1.3-dev.jar的DigestUtils
 * 
 * 密码加密方式入库
 * 
 * 后面可考虑使用 apache shiro
 * 
 * @author linzl
 *
 * @creatDate 2016年11月2日
 */
public class SHA256Util {
	public static final String PREFERRED_ENCODING = "UTF-8";
	private static final Logger logger = LoggerFactory.getLogger(SHA256Util.class);
	/**
	 * 静态私有盐
	 */
	private static String staticSalt = "linzl@163";

	/**
	 * sha256 加密密码
	 * 
	 * @param plainPass
	 *            需要加密的密码
	 * @param randomSalt
	 *            随机盐
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encrypt(String plainPass, String randomSalt) {
		byte[] privateSalt = toBytes(staticSalt, PREFERRED_ENCODING);

		randomSalt = (randomSalt == null ? getRandomSalt() : randomSalt);
		byte[] publicSalt = toBytes(randomSalt, PREFERRED_ENCODING);

		if (logger.isDebugEnabled()) {
			// 私有盐不打印
			// logger.debug("私有盐={}", new String(privateSalt));
			logger.debug("公有盐={}", new String(publicSalt));
		}

		// 按照sso server 盐的位置存放
		byte[] salt = combine(privateSalt, publicSalt);
		byte[] passByte = toBytes(plainPass, PREFERRED_ENCODING);

		MessageDigest digest = DigestUtils.getSha256Digest();
		digest.reset();
		digest.update(salt);
		passByte = digest.digest(passByte);
		return Hex.toHexString(passByte);
	}

	/**
	 * 获取随机盐
	 * 
	 * @return
	 */
	public static String getRandomSalt() {
		SecureRandom sr = null;
		byte[] salt = new byte[32];
		try {
			sr = SecureRandom.getInstance("SHA1PRNG");
			sr.nextBytes(salt);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return Hex.toHexString(salt);
	}

	private static byte[] combine(byte[] privateSalt, byte[] publicSalt) {
		int privateSaltLength = privateSalt.length;
		int extraBytesLength = publicSalt.length;

		int length = privateSaltLength + extraBytesLength;
		if (length <= 0) {
			return null;
		}
		byte[] combined = new byte[length];
		// int i = 0;
		// for (int j = 0; j < privateSaltLength; j++) {
		// combined[i++] = privateSalt[j];
		// }
		// for (int j = 0; j < extraBytesLength; j++) {
		// combined[i++] = publicSalt[j];
		// }
		System.arraycopy(privateSalt, 0, combined, 0, privateSaltLength - 1);
		System.arraycopy(publicSalt, 0, combined, privateSaltLength, extraBytesLength - 1);
		return combined;
	}

	/**
	 * 判断密码是否与数据库密码一致
	 * 
	 * @param plainPass
	 *            用户填写的密码
	 * @param encryptPass
	 *            数据库保存的密码
	 * @param randomSalt
	 *            数据库保存的随机盐
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static boolean equalsCipher(String plainPass, String encryptPass, String randomSalt)
			throws UnsupportedEncodingException {
		return encryptPass.equals(encrypt(plainPass, randomSalt));
	}

	private static byte[] toBytes(String source, String encoding) {
		try {
			return source.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			String msg = "Unable to convert source [" + source + "] to byte array using " + "encoding '" + encoding
					+ "'";
			logger.error(msg);
		}
		return null;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String userpass = "1";
		String randomText = SHA256Util.getRandomSalt();
		System.out.println("randomText==>" + randomText);
		// 用户加密后传输的密码
		String pass = SHA256Util.encrypt(userpass, randomText);
		System.out.println("pass==>" + pass);
		// 解密后的用户密码
		boolean flag = SHA256Util.equalsCipher(userpass, pass, randomText);
		System.out.println("是否一致==》" + flag);
	}
}
