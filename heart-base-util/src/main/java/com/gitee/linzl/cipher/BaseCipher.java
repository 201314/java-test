package com.gitee.linzl.cipher;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class BaseCipher {
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
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance(algorithm.getKeyAlgorithm());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        /**
         * initialize(int keySize, SecureRandom random):使用给定的随机源（random）初始化特定密钥大小的密钥对生成器。
         * keySize: 健的大小值，这是一个特定于算法的度量。
         * 值越大，能加密的内容就越多，否则会抛异常：javax.crypto.IllegalBlockSizeException: Data must not be longer than xxx bytes
         * 如 keySize 为 2048 时加密数据的长度不能超过 245 字节。
         */
        if (algorithm.getSize() != null && algorithm.getSize() >= 0) {
            keyPairGen.initialize(algorithm.getSize());
        }
        return keyPairGen.generateKeyPair();
    }

    /**
     * 非对称加密，加载私钥字节
     *
     * @param privateKeyByte 私钥字节
     * @throws Exception 加载私钥时产生的异常
     */
    public static PrivateKey generatePrivate(IAlgorithm algorithm, byte[] privateKeyByte) throws Exception {
        // 实例化密钥生成器
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm.getKeyAlgorithm());
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
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm.getKeyAlgorithm());
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
}
