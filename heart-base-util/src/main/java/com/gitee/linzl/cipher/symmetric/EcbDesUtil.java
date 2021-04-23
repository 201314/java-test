package com.gitee.linzl.cipher.symmetric;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 3Des 双倍长密钥加密
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2019年2月25日
 */
public class EcbDesUtil {
	private static final Logger log = LoggerFactory.getLogger(EcbDesUtil.class);

	public static void main(String[] args) throws Exception {
		String key2 = "1234567890123456";
		key2 = Hex.encodeHexString(key2.getBytes());
		System.out.println("key2 Hex:" + key2);
		String srcStr = "112233445566778";
		// 将字节数组转换为十六进制字符串
		String data = Hex.encodeHexString(srcStr.getBytes());

		String eninfo = encode3DES(key2, data);
		System.out.println("3des加密后：" + eninfo);

		String data2 = decode3DES(key2, eninfo);
		// 将十六进制字符数组转换为字节数组,再转换成字符串
		String decodeStr = new String(Hex.decodeHex(data2));
		System.out.println("3des解密后：" + decodeStr);

	}

	/**
	 * 补位填充最后数据块
	 * 
	 * @param hexData
	 * @return
	 */
	private static String fillLastBlock(String hexData) {
		// 先检查最后一个数据块长度是否为8字节，进行补齐处理
		int n = hexData.length() % 16;
		if (n != 0) { // 最后的数据块为1-7字节长即为2-14位
			// 先其后加入‘80’刚好达到8字节，则继续，否则继续在后面补0直到8字节
			// 不要加80，否则还原时最后会是个乱码
			hexData += "00";
			n = hexData.length() % 16;
			if (n != 0) {
				for (int i = 0; i < (16 - n) / 2; i++) {
					hexData += "00";
				}
			}
		}
		return hexData;
	}

	/**
	 * 单倍长密钥DEA加密算法
	 * 
	 * @param hexKey  16进制密钥(单倍长密钥8字节)
	 * @param hexData 16进制的明文数据
	 */
	public static String encodeDES(String hexKey, String hexData) {
		if (hexKey.length() != 16) {
			throw new RuntimeException("单倍长密钥DEA加密算法密钥必须为16位十六进制数,此密钥长度为：" + hexKey.length());
		}
		byte[] rawSecretKey = null;
		try {
			rawSecretKey = Hex.decodeHex(hexKey);
		} catch (DecoderException e1) {
			e1.printStackTrace();
		}
		byte[] buffer;
		StringBuilder sb = new StringBuilder();
		try {
			/**
			 * 数据加密的方法： 1，用LD（1字节）表示明文数据的长度，在明文数据前加上LD产生新的数据块。
			 * 
			 * 2，将此数据块分成8字节为单位的数据块，表示为block1、block2、block3、block4等，最后的数据块有可能是1- 8字节。
			 * 
			 * 3，如果最后（或唯一）的数据块长度是8字节的话，转到第4步；如果不足8字节，则在其后加入十六进制数‘80’，如果达到8字节长度，则
			 * 转到第4步；否则在其后加入十六进制数‘00’直到长度达到8字节。
			 * 
			 * 4，按照图所述的算法使用指定密钥对每一个数据块进行加密。
			 * 
			 * 5，计算结束后，所有加密后的数据块按照顺序连接在一起。
			 */
			// 严格来说应该在数据块前面先加上1字节表示数据的长度
			// hexData=NumberUtil.format2Hex(hexData.length(),1)+hexData;
			hexData = fillLastBlock(hexData);
			// log.info("des数据补齐后="+hexData);
			DefaultSymmetricCipher cipherBuilder = new DefaultSymmetricCipher(
					DESCipherAlgorithms.DES_ECB_NOPADDING_56, rawSecretKey);
			for (int i = 0; i < hexData.length(); i = i + 16) {
				buffer = Hex.decodeHex(hexData.substring(i, i + 16));
				buffer = cipherBuilder.encrypt(buffer);
				sb.append(Hex.encodeHexString(buffer));
			}
		} catch (Exception e) {
			log.error("单倍长密钥DEA加密算法出错了,key=" + hexKey + ",data=" + hexData, e);
			return null;
		}
		// log.info("单倍长des算法加密结果="+sb.toString());
		return sb.toString();
	}

	/**
	 * 单倍长密钥DEA解密算法
	 * 
	 * @param hexKey  十六进制密钥(16字节)
	 * @param hexData 十六进制密文(长度为8字节倍数)
	 */
	public static String decodeDES(String hexKey, String hexData) {
		if (hexKey.length() != 16) {
			throw new RuntimeException("单倍长密钥DEA解密算法密钥必须为16位十六进制数,此密钥长度为：" + hexKey.length());
		}
		byte[] rawSecretKey = null;
		try {
			rawSecretKey = Hex.decodeHex(hexKey);
		} catch (DecoderException e1) {
			e1.printStackTrace();
		}
		byte[] buffer;
		StringBuilder sb = new StringBuilder();
		try {
			DefaultSymmetricCipher cipherBuilder = new DefaultSymmetricCipher(
					DESCipherAlgorithms.DES_ECB_NOPADDING_56, rawSecretKey);
			for (int i = 0; i < hexData.length(); i = i + 16) {
				buffer = Hex.decodeHex(hexData.substring(i, i + 16));
				buffer = cipherBuilder.decrypt(buffer);
				sb.append(Hex.encodeHexString(buffer));
			}
		} catch (Exception e) {
			log.error("单倍长密钥DEA解密算法出错了,key=" + hexKey + ",data=" + hexData, e);
			return null;
		}
		return sb.toString();
	}

	/**
	 * 双倍长密钥（16字节长）3DEA加密算法
	 * 
	 * @param hexKey  16进制密钥(16字节32位长)
	 * @param hexData 16进制的明文数据
	 */
	public static String encode3DES(String hexKey, String hexData) {
		if (hexKey.length() != 32) {
			throw new RuntimeException("双倍长密钥（16字节长）3DEA加密算法密钥必须为32位十六进制数,当前密钥长度为：" + hexKey.length());
		}
		byte[] lkey = null;
		byte[] rkey = null;
		try {
			lkey = Hex.decodeHex(hexKey.substring(0, 16));
			rkey = Hex.decodeHex(hexKey.substring(16, 32));
		} catch (DecoderException e1) {
			e1.printStackTrace();
		}
		byte[] buffer;
		StringBuilder sb = new StringBuilder();
		try {
			/**
			 * 数据加密的方法：
			 * 
			 * 1，用LD（1字节）表示明文数据的长度，在明文数据前加上LD产生新的数据块。
			 * 
			 * 2，将此数据块分成8字节为单位的数据块，表示为block1、block2、block3、block4等，最后的数据块有可能是1- 8字节。
			 * 
			 * 3，如果最后（或唯一）的数据块长度是8字节的话，转到第4步；如果不足8字节，则在其后加入十六进制数‘80’，如果达到8字节长度，则
			 * 转到第4步；否则在其后加入十六进制数‘00’直到长度达到8字节。
			 * 
			 * 4，按照图所述的算法使用指定密钥对每一个数据块进行加密。
			 * 
			 * 5，计算结束后，所有加密后的数据块按照顺序连接在一起。
			 */
			// 严格来说应该在数据块前面先加上1字节表示数据的长度
			// hexData=NumberUtil.format2Hex(hexData.length(),1)+hexData;
			hexData = fillLastBlock(hexData);
			System.out.println("补足长度hex:" + hexData);

			DefaultSymmetricCipher encryptBuilder = new DefaultSymmetricCipher(DESCipherAlgorithms.DES_ECB_NOPADDING_56,
					lkey);

			DefaultSymmetricCipher decryptBuilder = new DefaultSymmetricCipher(DESCipherAlgorithms.DES_ECB_NOPADDING_56,
					rkey);

			for (int i = 0; i < hexData.length(); i = i + 16) {
				buffer = Hex.decodeHex(hexData.substring(i, i + 16));
				buffer = encryptBuilder.encrypt(buffer);
				buffer = decryptBuilder.decrypt(buffer);
				buffer = encryptBuilder.encrypt(buffer);
				sb.append(Hex.encodeHexString(buffer));
			}
		} catch (Exception e) {
			log.error("双倍长密钥（16字节长）3DEA加密算法出错了,key=" + hexKey + ",data=" + hexData, e);
			return null;
		}
		String enc = sb.toString();
		return enc;
	}

	/**
	 * 双倍长密钥（16字节长）3DEA解密算法
	 * 
	 * @param hexKey  十六进制密钥(32位16字节)
	 * @param hexData 十六进制密文(长度为8字节倍数)
	 */
	public static String decode3DES(String hexKey, String hexData) {
		if (hexKey.length() != 32) {
			throw new RuntimeException("双倍长密钥（16字节长）3DEA解密算法密钥必须为32位十六进制数");
		}
		byte[] lkey = null;
		byte[] rkey = null;
		try {
			lkey = Hex.decodeHex(hexKey.substring(0, 16));
			rkey = Hex.decodeHex(hexKey.substring(16, 32));
		} catch (DecoderException e1) {
			e1.printStackTrace();
		}
		byte[] buffer;
		StringBuilder sb = new StringBuilder();
		try {
			DefaultSymmetricCipher encryptBuilder = new DefaultSymmetricCipher(DESCipherAlgorithms.DES_ECB_NOPADDING_56,
					rkey);

			DefaultSymmetricCipher decryptBuilder = new DefaultSymmetricCipher(DESCipherAlgorithms.DES_ECB_NOPADDING_56,
					lkey);
			
			for (int i = 0; i < hexData.length(); i = i + 16) {
				buffer = Hex.decodeHex(hexData.substring(i, i + 16));
				buffer = decryptBuilder.decrypt(buffer);
				buffer = encryptBuilder.encrypt(buffer);
				buffer = decryptBuilder.decrypt(buffer);
				sb.append(Hex.encodeHexString(buffer));
			}
		} catch (Exception e) {
			log.error("双倍长密钥（16字节长）3DEA解密算法出错了,key=" + hexKey + ",data=" + hexData, e);
			return null;
		}
		return sb.toString();
	}
}
