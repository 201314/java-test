package com.gitee.linzl.cipher;

import com.gitee.linzl.cipher.asymmetrical.DefaultAsymmetricCipher;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;

import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

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

    public static void printPrivateKey(byte[] privateKey) {
        System.out.println("=========私钥===============");
        System.out.println("私钥长度=>" + privateKey.length);
    }

    public static void printPublicKey(byte[] publicKey) {
        System.out.println("=========公钥===============");
        System.out.println("公钥长度=>" + publicKey.length);
    }

    protected void allocate(IAlgorithm algorithm) throws Exception {
        System.out.println("start===========指定密钥 公钥加密，私钥解密===========start");
        byte[] publicKey = KeyPairPathUtil.getPublicKeyFile();
        printPublicKey(publicKey);
        byte[] privateKey = KeyPairPathUtil.getPrivateKeyFile();
        printPrivateKey(privateKey);
        DefaultAsymmetricCipher cipher = new DefaultAsymmetricCipher(algorithm,
                BaseCipher.generatePublic(algorithm, Base64.decodeBase64(publicKey)),
                BaseCipher.generatePrivate(algorithm, Base64.decodeBase64(privateKey)));
        byte[] encryptData = cipher.encrypt(text.getBytes(Charset.defaultCharset()));
        printEncryptData(encryptData);

        byte[] decryptData = cipher.decrypt(encryptData);
        printDecryptData(decryptData);
        System.out.println("end===========指定密钥 公钥加密，私钥解密===========end");
    }

    protected void allocate22(IAlgorithm algorithm) throws Exception {
        System.out.println("start===========指定密钥 私钥加密，公钥解密===========start");
        byte[] privateKey = KeyPairPathUtil.getPrivateKeyFile();
        PrivateKey priKey = BaseCipher.generatePrivate(algorithm, Base64.decodeBase64(privateKey));
        printPrivateKey(priKey.getEncoded());
        byte[] publicKey = KeyPairPathUtil.getPublicKeyFile();
        PublicKey pubKey = BaseCipher.generatePublic(algorithm, Base64.decodeBase64(publicKey));
        printPublicKey(pubKey.getEncoded());

        DefaultAsymmetricCipher cipher = new DefaultAsymmetricCipher(algorithm, priKey, pubKey);
        byte[] encryptData = cipher.encrypt(text.getBytes(Charset.defaultCharset()));
        printEncryptData(encryptData);

        byte[] decryptData = cipher.decrypt(encryptData);
        printDecryptData(decryptData);
        System.out.println("end===========指定密钥  私钥加密，公钥解密===========end");
    }

    protected void random(IAlgorithm algorithm) throws Exception {
        System.out.println("start===========JDK随机密钥===========start");
        KeyPair keyPair = BaseCipher.generateKeyPair(algorithm);
        PrivateKey priKey = keyPair.getPrivate();
        printPrivateKey(priKey.getEncoded());
        PublicKey pubKey = keyPair.getPublic();
        printPublicKey(pubKey.getEncoded());

        DefaultAsymmetricCipher cipher = new DefaultAsymmetricCipher(algorithm, pubKey, priKey);
        byte[] encryptData = cipher.encrypt(text.getBytes(Charset.defaultCharset()));
        printEncryptData(encryptData);

        byte[] decryptData = cipher.decrypt(encryptData);
        printDecryptData(decryptData);
        System.out.println("end===========JDK随机密钥===========end");
    }
}
