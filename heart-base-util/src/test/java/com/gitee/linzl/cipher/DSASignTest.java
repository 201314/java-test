package com.gitee.linzl.cipher;

import com.gitee.linzl.cipher.asymmetrical.DefaultSign;
import com.gitee.linzl.cipher.asymmetrical.SignatureAlgorithms;
import org.junit.Test;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;

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
        KeyFactory factory = KeyFactory.getInstance(algorithm.getKeyAlgorithm());
        System.out.println("start===========JDK随机密钥===========start");
        KeyPair keyPair = BaseCipher.generateKeyPair(algorithm);

        PrivateKey privateKey = keyPair.getPrivate();

        DSAPrivateKeySpec privateKeySpec = factory.getKeySpec(privateKey, DSAPrivateKeySpec.class);
        BigInteger privateModulus = privateKeySpec.getP();
        //转换为二进制
        int length = privateModulus.toString(2).length();
        System.out.println("=========私钥长度===============" + length);

        DefaultSign signBuilder = new DefaultSign(algorithm, privateKey);

        byte[] signData = signBuilder.sign(text.getBytes());
        printEncryptData(signData);

        PublicKey publicKey = keyPair.getPublic();

        DSAPublicKeySpec publicKeySpec = factory.getKeySpec(publicKey, DSAPublicKeySpec.class);
        BigInteger publicModuls = publicKeySpec.getP();
        //转换为二进制
        length = publicModuls.toString(2).length();
        System.out.println("=========公钥长度===============" + length);

        DefaultSign verifySignBuilder = new DefaultSign(algorithm, publicKey);
        boolean verifyResult = verifySignBuilder.verify(text.getBytes(), signData);
        System.out.println("验签结果: " + verifyResult);
        System.out.println("end===========JDK随机密钥===========end");
    }

}
