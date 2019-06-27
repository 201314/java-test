package com.gitee.linzl.ext;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.gitee.linzl.codec.ConvertUtil;
import com.gitee.linzl.lang.StringUtil;
import com.gitee.linzl.time.DateFormatUtil;

/**
 * 主鍵生成器
 * 
 * @author linzl
 */
public class KeyGeneratorUtil {
	/**
	 * 主键生成器使用这个做为ID
	 * 
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String getTimstamp() {
		return String.valueOf(Calendar.getInstance().getTimeInMillis());
	}

	public static long getRandomId() {
		return ThreadLocalRandom.current().nextLong();
	}

	/**
	 * 自增ID,不能含有特殊字符
	 * 
	 * @param src
	 * @return
	 */
	public static String autoIncrease(String src) {
		return StringUtil.incrementAlphanumeric(src);
	}

	/**
	 * not Thread safe
	 * 
	 * @param prefix
	 * @return
	 */
	public static String createRuleId(String prefix) {
		return prefix + DateFormatUtil.format(new Date(), "yyMMddHHmmss")
				+ ThreadLocalRandom.current().ints(100000, 999999).findAny().getAsInt();
	}

	public static void main(String[] args) throws Exception {
		InetAddress ip = InetAddress.getLocalHost();
		System.out.println(ip.getHostAddress());
		NetworkInterface network = NetworkInterface.getByInetAddress(ip);
		byte[] mac = network.getHardwareAddress();
		System.out.println(ConvertUtil.byte2Hex(mac));
	}
}