package com.gitee.linzl.cipher;

import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.gitee.linzl.cls.ClassUtils;

public class BaseCipher {
    private static final String aesIvParameterSpec = "0102030405060708";
    private static final String desIvParameterSpec = "12345678";
    private static final boolean bcPresent;

    static {
        ClassLoader classLoader = BaseCipher.class.getClassLoader();
        bcPresent = ClassUtils.isPresent("org.bouncycastle.jce.provider.BouncyCastleProvider", classLoader);

        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /**
     * 生成对称密钥,一般生成后转成base64存放在文件中，一般情况下不使用，只用于测试
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
     * 随机生成密钥对,非对称加解密，一般情况下不使用，只用于测试
     *
     * @param algorithm
     * @return
     */
    public static KeyPair generateKeyPair(IAlgorithm algorithm) {
        // 实例化密钥生成器
        String algorithmName = algorithm.getKeyAlgorithm();
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (algorithm.getSize() != null && algorithm.getSize() >= 0) {
            keyPairGen.initialize(algorithm.getSize());
        }
        KeyPair keyPair = keyPairGen.generateKeyPair();
        return keyPair;
    }

    /**
     * 非对称加密，加载私钥字节
     *
     * @param privateKeyByte 私钥字节
     * @throws Exception 加载私钥时产生的异常
     */
    public static PrivateKey generatePrivate(IAlgorithm algorithm, byte[] privateKeyByte) throws Exception {
        // 实例化密钥生成器
        String algorithmName = algorithm.getKeyAlgorithm();
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithmName);
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyByte);
            return keyFactory.generatePrivate(privateKeySpec);// RSAPrivateKey
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        }
    }

    /**
     * 非对称加密，加载公钥字节
     *
     * @param pubicKeyByte 公钥字节
     * @throws Exception 加载公钥时产生的异常
     */
    public static PublicKey generatePublic(IAlgorithm algorithm, byte[] pubicKeyByte) throws Exception {
        // 实例化密钥生成器
        String algorithmName = algorithm.getKeyAlgorithm();
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithmName);
            EncodedKeySpec keySpec = new X509EncodedKeySpec(pubicKeyByte);
            return keyFactory.generatePublic(keySpec);// RSAPublicKey
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }


    /**
     * @param rawData   原生数据
     * @param key       密钥
     * @param algorithm
     * @param iv        IV向量
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] rawData, Key key, IAlgorithm algorithm, IvParameterSpec iv) throws Exception {
        Cipher cipher = getCipher(algorithm);
        IvParameterSpec ivParameterSpec = getIvParameterSpec(algorithm, iv);
        // 初始化Cipher对象，设置为加密模式
        if (Objects.nonNull(ivParameterSpec)) {
            // 实例化Cipher对象，它用于完成实际的加密操作
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        }
        // 执行加密操作,加密后的结果通常都会用Base64编码进行传输
        return cipher.doFinal(rawData);
    }

    /**
     * @param rawData   待加密原生数据
     * @param key       密钥,非对称加密填写公钥
     * @param algorithm
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] rawData, Key key, IAlgorithm algorithm) throws Exception {
        return encrypt(rawData, key, algorithm, null);
    }

    /**
     * @param encryptedData 待解密的加密数据
     * @param key           非对称加密时，key为私钥
     * @param algorithm
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] encryptedData, Key key, IAlgorithm algorithm) throws Exception {
        return decrypt(encryptedData, key, algorithm, null);
    }

    /**
     * @param encryptedData 待解密的加密数据
     * @param key           非对称加密时，key为私钥
     * @param algorithm
     * @param iv
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] encryptedData, Key key, IAlgorithm algorithm, IvParameterSpec iv) throws Exception {
        Cipher cipher = getCipher(algorithm);
        IvParameterSpec ivParameterSpec = getIvParameterSpec(algorithm, iv);

        if (Objects.nonNull(ivParameterSpec)) {
            // 初始化Cipher对象，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key);
        }
        // 执行解密操作
        return cipher.doFinal(encryptedData);
    }

    public static String decrypt(String encryptedBase64, Key key, IAlgorithm algorithm, IvParameterSpec iv) throws Exception {
        byte[] out = decrypt(Base64.getDecoder().decode(encryptedBase64), key, algorithm, iv);
        return new String(out);
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

    /**
     * 对数据进行签名
     *
     * @param data       需要签名的数据
     * @param privateKey 私钥
     * @param algorithm  签名算法
     * @return
     * @throws Exception
     */
    public static byte[] sign(byte[] data, PrivateKey privateKey, IAlgorithm algorithm) throws Exception {
        // 用私钥对信息生成数字签名
        String signAlgorithm = algorithm.getSignAlgorithm();
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    /**
     * 验证签名
     *
     * @param data      签名前的数据
     * @param publicKey 公钥
     * @param sign      签名后的数据
     * @return
     * @throws Exception
     */
    public static boolean verifySign(byte[] data, PublicKey publicKey, byte[] sign, IAlgorithm algorithm)
            throws Exception {
        // 用私钥对信息生成数字签名
        String signAlgorithm = algorithm.getSignAlgorithm();
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initVerify(publicKey);
        signature.update(data);

        // 执行加密操作,加密后的结果通常都会用Base64编码进行传输
        // 验证签名是否正常
        return signature.verify(sign);
    }
}
