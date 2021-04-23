package com.gitee.linzl.cipher;

import com.gitee.linzl.cipher.symmetric.DefaultSymmetricCipher;
import org.junit.Before;

import java.io.File;
import java.nio.charset.Charset;

public class SymmetricBaseTest {
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

    public static void printSecretKey(byte[] secretKey) {
        System.out.println("========================");
        System.out.println("密钥长度=>" + secretKey.length);
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


    /**
     * jdk默认 CBC 要使用IV 密钥
     *
     * @param aes
     * @throws Exception
     */
    protected void excuteAesCBC(IAlgorithm aes) throws Exception {
        byte[] rawSecretKey = BaseCipher.generateKey(aes);
        printSecretKey(rawSecretKey);

        byte[] keyiv = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6};// 长度必须是16


        DefaultSymmetricCipher cipherBuilder = new DefaultSymmetricCipher(aes, rawSecretKey, keyiv);
        byte[] encryptData = cipherBuilder.encrypt(text.getBytes(Charset.defaultCharset()));
        printEncryptData(encryptData);

        byte[] decryptData = cipherBuilder.decrypt(encryptData);
        printDecryptData(decryptData);
    }

    // CBC 要使用IV 密钥
    protected void excuteDesCBC(IAlgorithm algorithm) throws Exception {
        byte[] rawSecretKey = BaseCipher.generateKey(algorithm);
        printSecretKey(rawSecretKey);

        byte[] ivParameterSpec = {1, 2, 3, 4, 5, 6, 7, 8};// 长度必须是8
        DefaultSymmetricCipher cipherBuilder = new DefaultSymmetricCipher(algorithm, rawSecretKey, ivParameterSpec);
        byte[] encryptData = cipherBuilder.encrypt(text.getBytes(Charset.defaultCharset()));
        printEncryptData(encryptData);

        byte[] decryptData = cipherBuilder.decrypt(encryptData);
        printDecryptData(decryptData);
    }

    /**
     * jdk默认
     *
     * @param aes
     * @throws Exception
     */
    protected void excute(IAlgorithm aes) throws Exception {
        byte[] rawSecretKey = BaseCipher.generateKey(aes);
        printSecretKey(rawSecretKey);

        DefaultSymmetricCipher cipherBuilder = new DefaultSymmetricCipher(aes, rawSecretKey);
        byte[] encryptData = cipherBuilder.encrypt(text.getBytes(Charset.defaultCharset()));
        printEncryptData(encryptData);

        byte[] decryptData = cipherBuilder.decrypt(encryptData);
        printDecryptData(decryptData);
    }

    protected void excuteFile(IAlgorithm aes) throws Exception {
        File file = new File("D:\\360极速浏览器下载", "身份证.docx");
        byte[] rawSecretKey = BaseCipher.generateKey(aes);
        printSecretKey(rawSecretKey);

        DefaultSymmetricCipher cipherBuilder = new DefaultSymmetricCipher(aes, rawSecretKey);
        cipherBuilder.encrypt(file, new File("D:\\360极速浏览器下载", "test.docx"));

        cipherBuilder.decrypt(new File("D:\\360极速浏览器下载", "test.docx"), new File("D:\\360极速浏览器下载",
                "test2.docx"));
    }
}
