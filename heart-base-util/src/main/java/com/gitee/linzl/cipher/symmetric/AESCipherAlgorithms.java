package com.gitee.linzl.cipher.symmetric;

import com.gitee.linzl.cipher.IAlgorithm;

/**
 * AES对称加密算法
 * <p>
 * 这里演示的是其Java6.0的实现,理所当然的BouncyCastle也支持AES对称加密算法
 * <p>
 * 另外,我们也可以以AES算法实现为参考,完成RC2,RC4和Blowfish算法的实现
 * <p>
 * 由于DES的不安全性以及DESede算法的低效,于是催生了AES算法(Advanced Encryption Standard)
 * <p>
 * 该算法比DES要快,安全性高,密钥建立时间短,灵敏性好,内存需求低,在各个领域应用广泛
 * <p>
 * 目前,AES算法通常用于移动通信系统以及一些软件的安全外壳,还有一些无线路由器中也是用AES算法构建加密协议
 * <p>
 * 由于Java6.0支持大部分的算法,但受到出口限制,其密钥长度不能满足需求
 * <p>
 * 所以特别注意:如果使用256位的密钥,则需要无政策限制文件(Unlimited Strength Jurisdiction Policy Files)
 * <p>
 * jdk是通过权限文件local_poblicy.jar和US_export_policy.jar做相应限制,可以搜索java 8 Unlimited
 * Strength Jurisdiction Policy Files下载替换文件,减少相关限制
 * <p>
 * 然后覆盖本地JDK目录和JRE目录下的security目录下的文件即可
 * <p>
 * 关于AES的更多详细介绍,可以参考此爷的博客http://blog.csdn.net/kongqz/article/category/800296
 * 
 * @author linzl 2016年11月21日
 */
/**
 * JDK默认支持
 * 
 * SupportedPaddings=NOPADDING|PKCS5PADDING|ISO10126PADDING,
 * SupportedKeyFormats=RAW,
 * SupportedModes=ECB|CBC|PCBC|CTR|CTS|CFB|OFB|CFB8|CFB16|CFB24|CFB32|CFB40|CFB48|CFB56|CFB64|OFB8|OFB16|OFB24|OFB32|OFB40|OFB48|OFB56|OFB64|GCM|CFB72|CFB80|CFB88|CFB96|CFB104|CFB112|CFB120|CFB128|OFB72|OFB80|OFB88|OFB96|OFB104|OFB112|OFB120|OFB128
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年11月2日
 */
public enum AESCipherAlgorithms implements IAlgorithm {
	// ============AES============
	AES_CBC_NOPADDING_128("AES", "AES/CBC/NoPadding", 128),

	AES_CBC_NOPADDING_192("AES", "AES/CBC/NoPadding", 192),

	AES_CBC_NOPADDING_256("AES", "AES/CBC/NoPadding", 256),

	AES_CBC_PKCS5PADDING_128("AES", "AES/CBC/PKCS5Padding", 128),

	AES_CBC_PKCS5PADDING_192("AES", "AES/CBC/PKCS5Padding", 192),

	AES_CBC_PKCS5PADDING_256("AES", "AES/CBC/PKCS5Padding", 256),

	AES_ECB_NOPADDING_128("AES", "AES/ECB/NoPadding", 128),

	AES_ECB_NOPADDING_192("AES", "AES/ECB/NoPadding", 192),

	AES_ECB_NOPADDING_256("AES", "AES/ECB/NoPadding", 256),
	// 默认使用，等同只指定AES算法名称
	AES_ECB_PKCS5PADDING_128_DEFAULT("AES", "AES/ECB/PKCS5Padding", 128),

	AES_ECB_PKCS5PADDING_192("AES", "AES/ECB/PKCS5Padding", 192),
	// 超过JDK默认长度,扩展的JCE
	AES_ECB_PKCS5PADDING_256("AES", "AES/ECB/PKCS5Padding", 256),

	// 支持BouncyCastleProvider TODO 测试
	// AES/CBC/PKCS7Padding
	BC_AES_CBC_PKCS7PADDING_128("AES", "AES/CBC/PKCS7Padding", 128),

	BC_AES_CBC_PKCS7PADDING_192("AES", "AES/CBC/PKCS7Padding", 192),

	BC_AES_CBC_PKCS7PADDING_256("AES", "AES/CBC/PKCS7Padding", 256),

	BC_AES_ECB_PKCS7PADDING_128("AES", "AES/ECB/PKCS7Padding", 128),

	BC_AES_ECB_PKCS7PADDING_192("AES", "AES/ECB/PKCS7Padding", 192),

	BC_AES_ECB_PKCS7PADDING_256("AES", "AES/ECB/PKCS7Padding", 256);

	private String keyAlgorithm;
	private String cipherAlgorithm;
	private Integer size;

	private AESCipherAlgorithms(String keyAlgorithm, String cipherAlgorithm, Integer size) {
		this.keyAlgorithm = keyAlgorithm;
		this.size = size;
		this.cipherAlgorithm = cipherAlgorithm;
	}

	@Override
	public String getKeyAlgorithm() {
		return keyAlgorithm;
	}

	@Override
	public Integer getSize() {
		return size;
	}

	@Override
	public String getCipherAlgorithm() {
		return cipherAlgorithm;
	}

	@Override
	public String getSignAlgorithm() {
		// TODO Auto-generated method stub
		return null;
	}
}
