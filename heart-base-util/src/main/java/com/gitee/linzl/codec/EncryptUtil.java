package com.gitee.linzl.codec;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Hex;

/**
 * java 自带的rsa,dsa,ecdsa 数字签名
 * 
 * @author linzl
 * 
 */
public class EncryptUtil {
	// 私钥加密
	private PrivateKey privateKey;
	// 公钥解密
	private PublicKey publicKey;
	// 算法名称
	private String algorithm;
	// 签名算法名称
	private String signAlgorithm;
	// 算法初始化长度
	private int initSize;

	private static String algorithmRSA = "RSA";
	private static String signAlgorithmRSA = "MD5withRSA";
	private static int initSizeRSA = 512;

	private static String algorithmDSA = "DSA";
	private static String signAlgorithmDSA = "SHA1withDSA";
	private static int initSizeDSA = 512;

	private static String algorithmEC = "EC";
	private static String signAlgorithmEC = "SHA1withECDSA";
	private static int initSizeEC = 256;

	private EncryptUtil() {
	}

	private EncryptUtil(String algorithm, String signAlgorithm, int initSize) {
		this.algorithm = algorithm;
		this.signAlgorithm = signAlgorithm;
		this.initSize = initSize;
		KeyPairGenerator kpg = null;
		try {
			kpg = KeyPairGenerator.getInstance(this.algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		kpg.initialize(this.initSize);
		KeyPair keyPair = kpg.generateKeyPair();
		// 私钥加密
		privateKey = keyPair.getPrivate();
		// 公钥解密
		publicKey = keyPair.getPublic();
	}

	public static EncryptUtil getMD5withRSAInstance() {
		return getInstance(algorithmRSA, signAlgorithmRSA, initSizeRSA);
	}

	public static EncryptUtil getSHA1withDSAInstance() {
		return getInstance(algorithmDSA, signAlgorithmDSA, initSizeDSA);
	}

	public static EncryptUtil getSHA1withECDSAInstance() {
		return getInstance(algorithmEC, signAlgorithmEC, initSizeEC);
	}

	/**
	 * 
	 * @param algorithm
	 *            算法名称
	 * @param signAlgorithm
	 *            签名算法名称
	 * @param initSize
	 *            算法初始化长度
	 * @return
	 */
	public static EncryptUtil getInstance(String algorithm, String signAlgorithm, int initSize) {
		return new EncryptUtil(algorithm, signAlgorithm, initSize);
	}

	public static EncryptUtil getInstance() {
		return new EncryptUtil();
	}

	// 2、执行私钥签名
	public byte[] sign(String data) {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
		KeyFactory keyFactory = null;
		byte[] signResult = null;
		try {
			keyFactory = KeyFactory.getInstance(this.algorithm);
			PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			Signature signature = Signature.getInstance(this.signAlgorithm);
			signature.initSign(privateKey);
			signature.update(data.getBytes());
			signResult = signature.sign();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return signResult;
	}

	// 自己指定私钥签名
	public byte[] sign(PrivateKey privateKey, String data) {
		this.privateKey = privateKey;
		return sign(data);
	}

	// 3、公钥验证签名
	public boolean verify(String data, byte[] signResult) {
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
		KeyFactory keyFactory = null;
		boolean verify = false;
		try {
			keyFactory = KeyFactory.getInstance(this.algorithm);
			PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
			Signature signature = Signature.getInstance(this.signAlgorithm);
			signature.initVerify(publicKey);
			signature.update(data.getBytes());
			verify = signature.verify(signResult);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return verify;
	}

	// 自己指定公钥验证签名
	public boolean verify(PublicKey publicKey, String data, byte[] signResult) {
		this.publicKey = publicKey;
		return verify(data, signResult);
	}

	public static void main(String[] args) {
		String data = "中华人民共和国政府";
		EncryptUtil util = getSHA1withECDSAInstance();
		byte[] signResult = util.sign(data);
		System.out.println(Hex.encodeHexString(signResult));
		boolean flag = util.verify(data, signResult);
		System.out.println(flag);
	}
}
