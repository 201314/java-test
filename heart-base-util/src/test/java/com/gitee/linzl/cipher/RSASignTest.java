package com.gitee.linzl.cipher;

import com.gitee.linzl.cipher.asymmetrical.DefaultSign;
import com.gitee.linzl.cipher.asymmetrical.SignatureAlgorithms;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.io.File;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSASignTest extends AsymmetricBaseTest {
    // =============非对称签名=============
    // 私钥签名，公钥验签
    @Test
    public void MD2withRSA() throws Exception {
        rsaSignRandom(SignatureAlgorithms.MD2withRSA);
        rsaSign(SignatureAlgorithms.MD2withRSA);
    }

    @Test
    public void MD2withRSA_File() throws Exception {
        rsaSignFile(SignatureAlgorithms.MD2withRSA);
    }

    @Test
    public void MD5withRSA() throws Exception {
        rsaSignRandom(SignatureAlgorithms.MD5withRSA);
        rsaSign(SignatureAlgorithms.MD5withRSA);
    }

    @Test
    public void MD5withRSA_File() throws Exception {
        rsaSignFile(SignatureAlgorithms.MD5withRSA);
    }

    @Test
    public void SHA1withRSA() throws Exception {
        rsaSignRandom(SignatureAlgorithms.SHA1withRSA);
        rsaSign(SignatureAlgorithms.SHA1withRSA);
    }

    @Test
    public void SHA256withRSA() throws Exception {
        rsaSignRandom(SignatureAlgorithms.SHA256withRSA);
        rsaSign(SignatureAlgorithms.SHA256withRSA);
    }

    @Test
    public void SHA384withRSA() throws Exception {
        rsaSignRandom(SignatureAlgorithms.SHA384withRSA);
        rsaSign(SignatureAlgorithms.SHA384withRSA);
    }

    @Test
    public void SHA512withRSA() throws Exception {
        rsaSignRandom(SignatureAlgorithms.SHA512withRSA);
        rsaSign(SignatureAlgorithms.SHA512withRSA);
    }

    private void rsaSignRandom(SignatureAlgorithms algorithm) throws Exception {
        KeyFactory factory = KeyFactory.getInstance(algorithm.getKeyAlgorithm());
        System.out.println("start===========JDK随机密钥===========start");
        KeyPair keyPair = BaseCipher.generateKeyPair(algorithm);

        PrivateKey priKey = keyPair.getPrivate();

        RSAPrivateKeySpec privateKeySpec = factory.getKeySpec(priKey, RSAPrivateKeySpec.class);
        BigInteger privateModulus = privateKeySpec.getModulus();
        //转换为二进制
        int length = privateModulus.toString(2).length();
        System.out.println("=========私钥长度===============" + length);

        DefaultSign signBuilder = new DefaultSign(algorithm, priKey);
        byte[] encryptData = signBuilder.sign(text.getBytes());
        printEncryptData(encryptData);

        PublicKey pubKey = keyPair.getPublic();

        RSAPublicKeySpec publicKeySpec = factory.getKeySpec(pubKey, RSAPublicKeySpec.class);
        BigInteger publicModulus = publicKeySpec.getModulus();
        //转换为二进制
        length = publicModulus.toString(2).length();
        System.out.println("=========公钥长度===============" + length);

        DefaultSign verifySignBuilder = new DefaultSign(algorithm, pubKey);
        boolean verifyResult = verifySignBuilder.verify(text.getBytes(), encryptData);
        System.out.println("验签结果: " + verifyResult);
        System.out.println("end===========JDK随机密钥===========end");
    }

    private void rsaSign(SignatureAlgorithms algorithm) throws Exception {
        System.out.println("start===========指定密钥===========start");
        byte[] privateKey = KeyPairPathUtil.getPrivateKeyFile();
        DefaultSign signBuilder = new DefaultSign(algorithm, BaseCipher.generatePrivate(algorithm, Base64.decodeBase64(privateKey)));

        byte[] encryptData = signBuilder.sign(text.getBytes());
        printEncryptData(encryptData);

        byte[] publicKey = KeyPairPathUtil.getPublicKeyFile();
        DefaultSign verifySignBuilder = new DefaultSign(algorithm, BaseCipher.generatePublic(algorithm, Base64.decodeBase64(publicKey)));

        boolean verifyResult = verifySignBuilder.verify(text.getBytes(), encryptData);
        System.out.println("验签结果: " + verifyResult);
        System.out.println("end===========指定密钥===========end");
    }

    private void rsaSignFile(SignatureAlgorithms algorithm) throws Exception {
        File file = new File("D:\\360极速浏览器下载", "JetbrainsCrack_jb51.rar");
        System.out.println("start===========指定密钥===========start");
        byte[] privateKey = KeyPairPathUtil.getPrivateKeyFile();
        DefaultSign signBuilder = new DefaultSign(algorithm, BaseCipher.generatePrivate(algorithm, Base64.decodeBase64(privateKey)));

        byte[] encryptData = signBuilder.sign(file);
        printEncryptData(encryptData);

        byte[] publicKey = KeyPairPathUtil.getPublicKeyFile();
        DefaultSign verifySignBuilder = new DefaultSign(algorithm, BaseCipher.generatePublic(algorithm, Base64.decodeBase64(publicKey)));

        boolean verifyResult = verifySignBuilder.verify(file, encryptData);
        System.out.println("验签结果: " + verifyResult);
        System.out.println("end===========指定密钥===========end");
    }
}
