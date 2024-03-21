package com.gitee.linzl.cipher;

import com.gitee.linzl.cipher.symmetric.AESCipherAlgorithms;
import org.junit.Test;

public class AESTest extends SymmetricBaseTest {
    // =============对称加密测试=============
    @Test
    public void AES_CBC_NOPADDING_128() throws Exception {
        // 必须是16的倍数
        text = "123456789012345612345678901234561234567890123456123456789012345612345678901234561234567890123456123456789012345612345678901234561234567890123456";
        excuteAesCBC(AESCipherAlgorithms.AES_CBC_NOPADDING_128);
    }

    @Test
    public void AEC_CBC_NOPADDING_192() throws Exception {
        // 必须是16的倍数
        text = "123456789012345612345678901234561234567890123456123456789012345612345678901234561234567890123456123456789012345612345678901234561234567890123456";
        excuteAesCBC(AESCipherAlgorithms.AES_CBC_NOPADDING_192);
    }

    @Test
    public void AEC_CBC_NOPADDING_256() throws Exception {
        // 必须是16的倍数
        text = "123456789012345612345678901234561234567890123456123456789012345612345678901234561234567890123456123456789012345612345678901234561234567890123456";
        excuteAesCBC(AESCipherAlgorithms.AES_CBC_NOPADDING_256);
    }

    @Test
    public void AES_CBC_PKCS5PADDING_128() throws Exception {
        excuteAesCBC(AESCipherAlgorithms.AES_CBC_PKCS5PADDING_128);
    }

    @Test
    public void AES_CBC_PKCS5PADDING_192() throws Exception {
        excuteAesCBC(AESCipherAlgorithms.AES_CBC_PKCS5PADDING_192);
    }

    @Test
    public void AES_CBC_PKCS5PADDING_256() throws Exception {
        excuteAesCBC(AESCipherAlgorithms.AES_CBC_PKCS5PADDING_256);
    }

    @Test
    public void AES_ECB_NOPADDING_128() throws Exception {
        // 必须是16的倍数
        text = "123456789012345612345678901234561234567890123456123456789012345612345678901234561234567890123456123456789012345612345678901234561234567890123456";
        excute(AESCipherAlgorithms.AES_ECB_NOPADDING_128);
    }

    @Test
    public void AES_ECB_NOPADDING_192() throws Exception {
        // 必须是16的倍数
        text = "123456789012345612345678901234561234567890123456123456789012345612345678901234561234567890123456123456789012345612345678901234561234567890123456";
        excute(AESCipherAlgorithms.AES_ECB_NOPADDING_192);
    }

    @Test
    public void AES_ECB_NOPADDING_256() throws Exception {
        // 必须是16的倍数
        text = "123456789012345612345678901234561234567890123456123456789012345612345678901234561234567890123456123456789012345612345678901234561234567890123456";
        excute(AESCipherAlgorithms.AES_ECB_NOPADDING_256);
    }

    @Test
    public void AES_ECB_PKCS5PADDING_128() throws Exception {
        excute(AESCipherAlgorithms.AES_ECB_PKCS5PADDING_128_DEFAULT);
    }

    @Test
    public void AES_ECB_PKCS5PADDING_128_File() throws Exception {
        excuteFile(AESCipherAlgorithms.AES_ECB_PKCS5PADDING_128_DEFAULT);
    }

    @Test
    public void AES_ECB_PKCS5PADDING_192() throws Exception {
        excute(AESCipherAlgorithms.AES_ECB_PKCS5PADDING_192);
    }

    @Test
    public void AES_ECB_PKCS5PADDING_256() throws Exception {
        excute(AESCipherAlgorithms.AES_ECB_PKCS5PADDING_256);
    }

    @Test
    public void BC_AES_CBC_PKCS7PADDING_128() throws Exception {
        excuteAesCBC(AESCipherAlgorithms.BC_AES_CBC_PKCS7PADDING_128);
    }

    @Test
    public void BC_AES_CBC_PKCS7PADDING_192() throws Exception {
        excuteAesCBC(AESCipherAlgorithms.BC_AES_CBC_PKCS7PADDING_192);
    }

    @Test
    public void BC_AES_CBC_PKCS7PADDING_256() throws Exception {
        excuteAesCBC(AESCipherAlgorithms.BC_AES_CBC_PKCS7PADDING_256);
    }

    @Test
    public void BC_AES_ECB_PKCS7PADDING_128() throws Exception {
        excute(AESCipherAlgorithms.BC_AES_ECB_PKCS7PADDING_128);
    }

    @Test
    public void BC_AES_ECB_PKCS7PADDING_192() throws Exception {
        excute(AESCipherAlgorithms.BC_AES_ECB_PKCS7PADDING_192);
    }

    @Test
    public void BC_AES_ECB_PKCS7PADDING_256() throws Exception {
        excute(AESCipherAlgorithms.BC_AES_ECB_PKCS7PADDING_256);
    }
}
