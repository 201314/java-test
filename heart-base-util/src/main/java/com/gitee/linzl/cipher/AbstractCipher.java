package com.gitee.linzl.cipher;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.gitee.linzl.cls.ClassUtils;

public abstract class AbstractCipher {
	private static final String aesIvParameterSpec = "0102030405060708";
	private static final String desIvParameterSpec = "12345678";
	private static final boolean bcPresent;
	static {
		ClassLoader classLoader = AbstractCipher.class.getClassLoader();
		bcPresent = ClassUtils.isPresent("org.bouncycastle.jce.provider.BouncyCastleProvider", classLoader);

		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
			Security.addProvider(new BouncyCastleProvider());
		}
	}

	/**
	 * @param data      待加密数据
	 * @param key       密钥
	 * @param algorithm
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, Key key, IAlgorithm algorithm) throws Exception {
		return encrypt(data, key, algorithm, null);
	}

	/**
	 * @param data
	 * @param key       密钥
	 * @param algorithm
	 * @param iv        IV向量
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, Key key, IAlgorithm algorithm, IvParameterSpec iv) throws Exception {
		Cipher cipher = getCipher(algorithm);
		IvParameterSpec ivParameterSpec = getIvParameterSpec(algorithm, iv);

		if (Objects.nonNull(ivParameterSpec)) {
			// 实例化Cipher对象，它用于完成实际的加密操作
			cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
		} else {
			cipher.init(Cipher.ENCRYPT_MODE, key);
		}
		// 初始化Cipher对象，设置为加密模式
		byte[] output = cipher.doFinal(data);
		// 执行加密操作,加密后的结果通常都会用Base64编码进行传输
		return output;
	}

	/**
	 * 非对称加密时，key为私钥
	 * 
	 * @param data
	 * @param key
	 * @param algorithm
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, Key key, IAlgorithm algorithm) throws Exception {
		return decrypt(data, key, algorithm, null);
	}

	public static byte[] decrypt(byte[] data, Key key, IAlgorithm algorithm, IvParameterSpec iv) throws Exception {
		Cipher cipher = getCipher(algorithm);
		IvParameterSpec ivParameterSpec = getIvParameterSpec(algorithm, iv);

		if (Objects.nonNull(ivParameterSpec)) {
			// 初始化Cipher对象，设置为解密模式
			cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
		} else {
			cipher.init(Cipher.DECRYPT_MODE, key);
		}
		// 执行解密操作
		return cipher.doFinal(data);
	}

	private static Cipher getCipher(IAlgorithm algorithm)
			throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
		Cipher cipher;
		if (bcPresent) {
			cipher = Cipher.getInstance(algorithm.getCipherAlgorithm(), BouncyCastleProvider.PROVIDER_NAME);
		} else {
			cipher = Cipher.getInstance(algorithm.getCipherAlgorithm());
		}
		return cipher;
	}

	private static IvParameterSpec getIvParameterSpec(IAlgorithm algorithm, IvParameterSpec iv) {
		IvParameterSpec spec = iv;
		if (algorithm.getCipherAlgorithm().contains("CBC") && Objects.isNull(spec)) {
			if (algorithm.getKeyAlgorithm().equalsIgnoreCase("AES")) {
				spec = new IvParameterSpec(aesIvParameterSpec.getBytes());
			} else if (algorithm.getKeyAlgorithm().equalsIgnoreCase("DES")) {
				spec = new IvParameterSpec(desIvParameterSpec.getBytes());
			}
		}
		return spec;
	}
}
