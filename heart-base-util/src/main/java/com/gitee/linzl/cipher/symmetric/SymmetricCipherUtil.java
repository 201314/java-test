package com.gitee.linzl.cipher.symmetric;

import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.gitee.linzl.cipher.AbstractCipher;
import com.gitee.linzl.cipher.IAlgorithm;
import com.gitee.linzl.cipher.asymmetrical.AsymmetricalCipherUtil;
import com.gitee.linzl.cls.ClassUtils;

/**
 * 对称加解密工具：加密密钥与解密密钥相同。
 * 
 * 密钥管理:比较难，不适合互联网，一般用于内部系统
 * 
 * 安全性:中
 * 
 * 速度:快好几个数量级
 * 
 * 适合场景:适合大量数据加密处理，不支持数字签名
 * 
 * 实际应用:采用非对称加密算法管理对称算法的密钥，用对称加密算法加密数据，即提高了加密速度，
 * 
 * 又实现了解密的安全RSA建议采用1024位的数字，ECC建议采用160位，AES采用128位即可。
 * 
 * DES(Data Encryption Standard): 数据加密标准，速度较快，适用于加密大量数据的场合;
 * 
 * 3DES(Triple DES): 是基于DES，对一块数据用三个不同的密钥进行三次加密，强度更高;
 * 
 * AES(Advanced Encryption Standard): 高级加密标准，是下一代的加密算法标准，速度快，安全级别高，AES 标准的一个实现是
 * Rijndael 算法;
 * 
 * RC2和RC4:用变长密钥对大量数据进行加密，比 DES 快;
 * 
 * Blowfish
 * 
 * @author linzl
 */
public class SymmetricCipherUtil extends AbstractCipher {
	/**
	 * 生成密钥
	 * 
	 * @return
	 */
	public static byte[] generateKey(IAlgorithm algorithm) throws Exception {
		// 实例化密钥生成器
		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm.getKeyAlgorithm());
		// 此处解决mac，linux报错
		// SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		// keyGenerator.init(algorithm.getSize(), random);
		keyGenerator.init(algorithm.getSize());
		// 生成密钥
		SecretKey secretKey = keyGenerator.generateKey();
		// 获取二进制密钥编码形式
		return secretKey.getEncoded();
	}

	/**
	 * 转换密钥
	 * 
	 * @param key
	 * @param algorithm
	 * @return
	 * @throws Exception
	 */
	public static Key toKey(byte[] key, IAlgorithm algorithm) throws Exception {
		return new SecretKeySpec(key, algorithm.getKeyAlgorithm());
	}

	/**
	 * 加密数据,用于对称加密。非对称加密，是不可能还原密钥的
	 * 
	 * @param data 待加密数据
	 * @param key  公钥
	 * @return 加密后的数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key, IAlgorithm algorithm) throws Exception {
		return encrypt(data, key, algorithm, null);
	}

	public static byte[] encrypt(byte[] data, byte[] key, IAlgorithm algorithm, IvParameterSpec iv) throws Exception {
		// 还原密钥
		Key secretKey = toKey(key, algorithm);
		return encrypt(data, secretKey, algorithm, iv);
	}

	/**
	 * 解密数据
	 * 
	 * @param data 待解密数据
	 * @param key  密钥
	 * @return 解密后的数据
	 */
	public static byte[] decrypt(byte[] data, byte[] key, IAlgorithm algorithm) throws Exception {
		return decrypt(data, key, algorithm, null);
	}

	public static byte[] decrypt(byte[] data, byte[] key, IAlgorithm algorithm, IvParameterSpec iv) throws Exception {
		// 还原密钥
		Key secretKey = toKey(key, algorithm);
		return decrypt(data, secretKey, algorithm, iv);
	}

}
