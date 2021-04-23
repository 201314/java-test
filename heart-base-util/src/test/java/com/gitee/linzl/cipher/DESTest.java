package com.gitee.linzl.cipher;

import com.gitee.linzl.cipher.symmetric.DESCipherAlgorithms;
import org.junit.Test;

public class DESTest extends SymmetricBaseTest {
    // =============对称加密测试=============
    // CBC 要使用IV 密钥
    @Test
    public void DES_CBC_NOPADDING_56() throws Exception {
        // 必须是8的倍数
        text = "012345678910111213141516";
        excuteDesCBC(DESCipherAlgorithms.DES_CBC_NOPADDING_56);
    }

    @Test
    public void DES_CBC_PKCS5PADDING_56() throws Exception {
        excuteDesCBC(DESCipherAlgorithms.DES_CBC_PKCS5PADDING_56);
    }

    // ECB 不支持IV 密钥
    @Test
    public void DES_ECB_NOPADDING_56() throws Exception {
        // 必须是8的倍数
        text = "012345678910111213141516";
        excute(DESCipherAlgorithms.DES_ECB_NOPADDING_56);
    }

    @Test
    public void DES_ECB_PKCS5PADDING_56() throws Exception {
        excute(DESCipherAlgorithms.DES_ECB_PKCS5PADDING_56_DEFAULT);
    }

    @Test
    public void DESEDE_CBC_NOPADDING_112() throws Exception {
        // 必须是8的倍数
        text = "012345678910111213141516";
        excuteDesCBC(DESCipherAlgorithms.DESEDE_CBC_NOPADDING_112);
    }

    @Test
    public void DESEDE_CBC_NOPADDING_168() throws Exception {
        // 必须是8的倍数
        text = "012345678910111213141516";
        excuteDesCBC(DESCipherAlgorithms.DESEDE_CBC_NOPADDING_168);
    }

    @Test
    public void DESEDE_CBC_PKCS5PADDING_112() throws Exception {
        excuteDesCBC(DESCipherAlgorithms.DESEDE_CBC_PKCS5PADDING_112);
    }

    @Test
    public void DESEDE_CBC_PKCS5PADDING_168() throws Exception {
        excuteDesCBC(DESCipherAlgorithms.DESEDE_CBC_PKCS5PADDING_168);
    }

    @Test
    public void DESEDE_ECB_NOPADDING_112() throws Exception {
        // 必须是8的倍数
        text = "012345678910111213141516";
        excute(DESCipherAlgorithms.DESEDE_ECB_NOPADDING_112);
    }

    @Test
    public void DESEDE_ECB_NOPADDING_168() throws Exception {
        // 必须是8的倍数
        text = "012345678910111213141516";
        excute(DESCipherAlgorithms.DESEDE_ECB_NOPADDING_168);
    }

    @Test
    public void DESEDE_ECB_PKCS5PADDING_112() throws Exception {
        excute(DESCipherAlgorithms.DESEDE_ECB_PKCS5PADDING_112);
    }

    @Test
    public void DESEDE_ECB_PKCS5PADDING_168() throws Exception {
        excute(DESCipherAlgorithms.DESEDE_ECB_PKCS5PADDING_168);
    }
}
