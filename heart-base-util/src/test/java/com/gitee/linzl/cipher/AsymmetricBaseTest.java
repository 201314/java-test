package com.gitee.linzl.cipher;

import com.gitee.linzl.cipher.asymmetrical.DefaultAsymmetricCipher;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class AsymmetricBaseTest {
    protected String text = null;

    @Before
    public void init() {
        text = "我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据1" + System.lineSeparator() +
                "我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据2" + System.lineSeparator() +
                "我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据3" + System.lineSeparator() +
                "我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据4" + System.lineSeparator() +
                "我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据5" + System.lineSeparator() +
                "我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据6" + System.lineSeparator() +
                "我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据7" + System.lineSeparator() +
                "我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据8" + System.lineSeparator() +
                "我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据9" + System.lineSeparator() +
                "我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据10" + System.lineSeparator() +
                "我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据11" + System.lineSeparator() +
                "我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据12" + System.lineSeparator() +
                "我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据13" + System.lineSeparator() +
                "我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据14" + System.lineSeparator() +
                "我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据15" + System.lineSeparator() +
                "我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据16" + System.lineSeparator() +
                "我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据我是加密数据17";
        System.out.println("字符串大小：" + text.getBytes().length);
    }

    public static void printEncryptData(byte[] encryptData) {
        System.out.println("========================");
        System.out.println("明文加密base64=>" + org.apache.commons.codec.binary.Base64.encodeBase64String(encryptData));
    }

    public static void printDecryptData(byte[] encryptData) {
        System.out.println("========================");
        System.out.println("密文解密后=>" + new String(encryptData, Charset.defaultCharset()));
    }


    protected void allocate(IAlgorithm algorithm) throws Exception {
        KeyFactory factory = KeyFactory.getInstance(algorithm.getKeyAlgorithm());
        System.out.println("start===========指定密钥 公钥加密，私钥解密===========start");
        byte[] publicKey = KeyPairPathUtil.getPublicKeyFile();
        byte[] privateKey = KeyPairPathUtil.getPrivateKeyFile();
        DefaultAsymmetricCipher cipher = new DefaultAsymmetricCipher(algorithm,
                BaseCipher.generatePublic(algorithm, Base64.decodeBase64(publicKey)),
                BaseCipher.generatePrivate(algorithm, Base64.decodeBase64(privateKey)));
        byte[] encryptData = cipher.encrypt(text.getBytes(Charset.defaultCharset()));

        RSAPublicKeySpec publicKeySpec = factory.getKeySpec(cipher.getEncryptKey(), RSAPublicKeySpec.class);
        BigInteger publicModulus = publicKeySpec.getModulus();
        //转换为二进制
        int length = publicModulus.toString(2).length();
        System.out.println("=========公钥长度===============" + length);

        RSAPrivateKeySpec privateKeySpec = factory.getKeySpec(cipher.getEncryptKey(), RSAPrivateKeySpec.class);
        BigInteger privateModulus = privateKeySpec.getModulus();
        //转换为二进制
        length = privateModulus.toString(2).length();
        System.out.println("=========私钥长度===============" + length);

        printEncryptData(encryptData);

        byte[] decryptData = cipher.decrypt(encryptData);
        printDecryptData(decryptData);
        System.out.println("end===========指定密钥 公钥加密，私钥解密===========end");
    }

    protected void allocate22(IAlgorithm algorithm) throws Exception {
        KeyFactory factory = KeyFactory.getInstance(algorithm.getKeyAlgorithm());
        System.out.println("start===========指定密钥 私钥加密，公钥解密===========start");

        byte[] privateKey = KeyPairPathUtil.getPrivateKeyFile();
        PrivateKey priKey = BaseCipher.generatePrivate(algorithm, Base64.decodeBase64(privateKey));

        RSAPrivateKeySpec privateKeySpec = factory.getKeySpec(priKey, RSAPrivateKeySpec.class);
        BigInteger privateModulus = privateKeySpec.getModulus();
        //转换为二进制
        int length = privateModulus.toString(2).length();
        System.out.println("=========私钥长度===============" + length);

        byte[] publicKey = KeyPairPathUtil.getPublicKeyFile();
        PublicKey pubKey = BaseCipher.generatePublic(algorithm, Base64.decodeBase64(publicKey));

        RSAPublicKeySpec publicKeySpec = factory.getKeySpec(pubKey, RSAPublicKeySpec.class);
        BigInteger publicModulus = publicKeySpec.getModulus();
        //转换为二进制
        length = publicModulus.toString(2).length();
        System.out.println("=========公钥长度===============" + length);

        DefaultAsymmetricCipher cipher = new DefaultAsymmetricCipher(algorithm, priKey, pubKey);
        byte[] encryptData = cipher.encrypt(text.getBytes(Charset.defaultCharset()));
        printEncryptData(encryptData);

        byte[] decryptData = cipher.decrypt(encryptData);
        printDecryptData(decryptData);
        System.out.println("end===========指定密钥  私钥加密，公钥解密===========end");
    }

    protected void random(IAlgorithm algorithm) throws Exception {
        KeyFactory factory = KeyFactory.getInstance(algorithm.getKeyAlgorithm());
        System.out.println("start===========JDK随机密钥===========start");
        KeyPair keyPair = BaseCipher.generateKeyPair(algorithm);
        PrivateKey priKey = keyPair.getPrivate();

        RSAPrivateKeySpec privateKeySpec = factory.getKeySpec(priKey, RSAPrivateKeySpec.class);
        BigInteger privateModulus = privateKeySpec.getModulus();
        //转换为二进制
        int length = privateModulus.toString(2).length();
        System.out.println("=========私钥长度===============" + length);

        PublicKey pubKey = keyPair.getPublic();

        RSAPublicKeySpec publicKeySpec = factory.getKeySpec(pubKey, RSAPublicKeySpec.class);
        BigInteger publicModulus = publicKeySpec.getModulus();
        //转换为二进制
        length = publicModulus.toString(2).length();
        System.out.println("=========公钥长度===============" + length);

        DefaultAsymmetricCipher cipher = new DefaultAsymmetricCipher(algorithm, pubKey, priKey);
        byte[] encryptData = cipher.encrypt(text.getBytes(Charset.defaultCharset()));
        printEncryptData(encryptData);

        byte[] decryptData = cipher.decrypt(encryptData);
        printDecryptData(decryptData);
        System.out.println("end===========JDK随机密钥===========end");
    }
}
