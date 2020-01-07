package com.gitee.linzl.cipher;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import com.gitee.linzl.cipher.asymmetrical.AsymmetricCipherBuilder;
import org.junit.Before;
import org.junit.Test;

import com.gitee.linzl.cipher.asymmetrical.SignatureAlgorithms;

public class DSASignTest {
    private String text = null;

    @Before
    public void init() {
        text = "站在云端，敲下键盘，望着通往世界另一头的那扇窗，只为做那读懂0和1的人。。";
        System.out.println("原文：" + text);
    }

    // =============非对称签名=============
    // 私钥签名，公钥验签
    @Test
    public void RawDSA() throws Exception {
        text = "12345678901234567890"; // 只能20个字节
        rsaSignRandom(SignatureAlgorithms.RawDSA);
    }

    @Test
    public void SHA1withDSA() throws Exception {
        rsaSignRandom(SignatureAlgorithms.SHA1withDSA);
    }

    @Test
    public void SHA224withDSA() throws Exception {
        rsaSignRandom(SignatureAlgorithms.SHA224withDSA);
    }

    @Test
    public void SHA256withDSA() throws Exception {
        rsaSignRandom(SignatureAlgorithms.SHA256withDSA);
    }

    private void rsaSignRandom(SignatureAlgorithms algorithm) throws Exception {
        System.out.println("start===========JDK随机密钥===========start");
        KeyPair keyPair = AbstractCipher.generateKeyPair(algorithm);
        PrivateKey privateKey = keyPair.getPrivate();
        BasePrint.printPrivateKey(privateKey.getEncoded());

        AsymmetricCipherBuilder.SignBuilder signBuilder = new AsymmetricCipherBuilder.SignBuilder(algorithm,
                privateKey.getEncoded());

        byte[] signData = signBuilder.sign(text.getBytes()).finish();
        BasePrint.printEncryptData(signData);

        PublicKey publicKey = keyPair.getPublic();
        BasePrint.printPublicKey(publicKey.getEncoded());
        AsymmetricCipherBuilder.VerifySignBuilder verifySignBuilder = new AsymmetricCipherBuilder.VerifySignBuilder(algorithm,
                publicKey.getEncoded());
        boolean verifyResult = verifySignBuilder.verify(text.getBytes(), signData).finish();
        System.out.println("验签结果: " + verifyResult);
        System.out.println("end===========JDK随机密钥===========end");
    }

}
