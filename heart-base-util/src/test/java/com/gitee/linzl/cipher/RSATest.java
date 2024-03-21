package com.gitee.linzl.cipher;

import com.gitee.linzl.cipher.asymmetrical.RSACipherAlgorithms;
import org.junit.Test;

public class RSATest extends AsymmetricBaseTest {
    // =============非对称加密测试=============
    @Test
    public void RSA_None_NOPADDING_1024() throws Exception {
        allocate(RSACipherAlgorithms.RSA_None_NOPADDING_1024_DEFAULT);
        allocate22(RSACipherAlgorithms.RSA_None_NOPADDING_1024_DEFAULT);
    }

    @Test
    public void RSA_None_PKCS1PADDING_1024() throws Exception {
        allocate(RSACipherAlgorithms.RSA_None_PKCS1PADDING_1024);
        allocate22(RSACipherAlgorithms.RSA_None_PKCS1PADDING_1024);
    }

    @Test  // TODO 无论是否BC,这个都有乱码 只有getOutputSize> 0 ????
    public void RSA_ECB_NOPADDING_1024() throws Exception {
        allocate(RSACipherAlgorithms.RSA_ECB_NOPADDING_1024);
        allocate22(RSACipherAlgorithms.RSA_ECB_NOPADDING_1024);
    }

    @Test
    public void RSA_ECB_PKCS1PADDING_1024() throws Exception {
        allocate(RSACipherAlgorithms.RSA_ECB_PKCS1PADDING_1024);
        random(RSACipherAlgorithms.RSA_ECB_PKCS1PADDING_1024);
    }

    @Test
    public void RSA_ECB_PKCS1PADDING_2048() throws Exception {
        allocate(RSACipherAlgorithms.RSA_ECB_PKCS1PADDING_2048_DEFAULT);
        random(RSACipherAlgorithms.RSA_ECB_PKCS1PADDING_2048_DEFAULT);
    }
}
