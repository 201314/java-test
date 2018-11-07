package com.gitee.linzl.cipher.mac;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.bouncycastle.util.encoders.Hex;

/**
 * 直接使用apache的HmacUtils,常用于接口签名验证
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年11月7日
 */
public class HmacUtilsExt {

	public static void main(String[] args) {
		byte[] mac = new HmacUtils(HmacAlgorithms.HMAC_MD5, "111").hmac("222");
		System.out.println(Hex.toHexString(mac));

		mac = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, "111").hmac("222");
		System.out.println("HMAC_SHA_1=" + Hex.toHexString(mac));

		mac = new HmacUtils(HmacAlgorithms.HMAC_SHA_224, "111").hmac("222");
		System.out.println("HMAC_SHA_224=" + Hex.toHexString(mac));

		mac = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, "111").hmac("222");
		System.out.println("HMAC_SHA_256=" + Hex.toHexString(mac));

		mac = new HmacUtils(HmacAlgorithms.HMAC_SHA_384, "111").hmac("222");
		System.out.println("HMAC_SHA_384=" + Hex.toHexString(mac));

		mac = new HmacUtils(HmacAlgorithms.HMAC_SHA_512, "111").hmac("222");
		System.out.println("HMAC_SHA_512=" + Hex.toHexString(mac));
	}
}
