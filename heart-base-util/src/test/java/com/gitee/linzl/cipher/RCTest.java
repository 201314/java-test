package com.gitee.linzl.cipher;

import com.gitee.linzl.cipher.symmetric.SymmetricCipherBuilder;
import org.junit.Before;
import org.junit.Test;

import com.gitee.linzl.cipher.symmetric.RCCipherAlgorithms;

public class RCTest {
    private String text = null;

    @Before
    public void init() {
        text = "站在云端，敲下键盘，望着通往世界另一头的那扇窗，只为做那读懂0和1的人。。";
        System.out.println("原文：" + text);
    }

    // =============对称加密测试=============
    @Test
    public void RC2() throws Exception {
        excute(RCCipherAlgorithms.RC2);
    }

    @Test
    public void RC4() throws Exception {
        excute(RCCipherAlgorithms.RC4);
    }

    private void excute(IAlgorithm algorithm) throws Exception {
        byte[] rawSecretKey = AbstractCipher.generateKey(algorithm);
        BasePrint.printSecretKey(rawSecretKey);

        SymmetricCipherBuilder.EncryptBuilder encryptBuilder = new SymmetricCipherBuilder.EncryptBuilder(algorithm,
                rawSecretKey);
        byte[] encryptData = encryptBuilder.encrypt(text.getBytes()).finish();
        BasePrint.printEncryptData(encryptData);

        SymmetricCipherBuilder.DecryptBuilder decryptBuilder = new SymmetricCipherBuilder.DecryptBuilder(algorithm,
                rawSecretKey);
        byte[] decryptData = decryptBuilder.decrypt(encryptData).finish();
        BasePrint.printDecryptData(decryptData);
    }
}
