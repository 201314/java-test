package com.gitee.linzl.cipher.asymmetrical;

import com.gitee.linzl.cipher.IAlgorithm;
import com.gitee.linzl.cipher.ISignature;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

/**
 * 签名：只能加签验签，无法解签，私钥加签，公钥验签
 *
 * @email 2225010489@qq.com
 * @date 2021/4/20
 */
public class DefaultSign implements ISignature {
    private static final int STREAM_BUFFER_LENGTH = 1024;
    private Signature sign;
    private Signature verify;

    /**
     * 私钥加签
     *
     * @param algorithm
     * @param privateKey
     * @throws Exception
     */
    public DefaultSign(IAlgorithm algorithm, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance(algorithm.getSignAlgorithm());
        signature.initSign(privateKey);
        sign = signature;
    }

    /**
     * 公钥验签
     *
     * @param algorithm
     * @param publicKey
     * @throws Exception
     */
    public DefaultSign(IAlgorithm algorithm, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance(algorithm.getSignAlgorithm());
        signature.initVerify(publicKey);
        verify = signature;
    }

    /**
     * 对数据进行签名
     *
     * @param data 需要签名的数据
     * @return
     * @throws Exception
     */
    @Override
    public byte[] sign(byte[] data) throws Exception {
        sign.update(data);
        return sign.sign();
    }

    @Override
    public byte[] sign(File file) throws Exception {
        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file))) {
            final byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
            int read = stream.read(buffer, 0, STREAM_BUFFER_LENGTH);

            while (read > -1) {
                sign.update(buffer, 0, read);
                read = stream.read(buffer, 0, STREAM_BUFFER_LENGTH);
            }
        }
        return sign.sign();
    }

    @Override
    public boolean verify(byte[] data, byte[] sign) throws Exception {
        // 用私钥对信息生成数字签名
        verify.update(data);
        // 验证签名是否正常
        return verify.verify(sign);
    }

    @Override
    public boolean verify(File file, byte[] sign) throws Exception {
        // 用私钥对信息生成数字签名
        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file))) {
            final byte[] buffer = new byte[1024];
            int read = stream.read(buffer, 0, 1024);

            while (read > -1) {
                verify.update(buffer, 0, read);
                read = stream.read(buffer, 0, 1024);
            }
        }
        // 验证签名是否正常
        return verify.verify(sign);
    }
}