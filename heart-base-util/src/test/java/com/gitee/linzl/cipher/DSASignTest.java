package com.gitee.linzl.cipher;

import com.gitee.linzl.cipher.asymmetrical.DefaultSign;
import com.gitee.linzl.cipher.asymmetrical.SignatureAlgorithms;
import org.junit.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class DSASignTest extends SymmetricBaseTest {
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
        KeyPair keyPair = BaseCipher.generateKeyPair(algorithm);
        PrivateKey privateKey = keyPair.getPrivate();
        printPrivateKey(privateKey.getEncoded());

        DefaultSign signBuilder = new DefaultSign(algorithm, privateKey);

        byte[] signData = signBuilder.sign(text.getBytes());
        printEncryptData(signData);

        PublicKey publicKey = keyPair.getPublic();
        printPublicKey(publicKey.getEncoded());
        DefaultSign verifySignBuilder = new DefaultSign(algorithm, publicKey);
        boolean verifyResult = verifySignBuilder.verify(text.getBytes(), signData);
        System.out.println("验签结果: " + verifyResult);
        System.out.println("end===========JDK随机密钥===========end");
    }

}
