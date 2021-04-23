package com.gitee.linzl.cipher;

import com.gitee.linzl.cipher.symmetric.RCCipherAlgorithms;
import org.junit.Test;

public class RCTest extends SymmetricBaseTest {
    // =============对称加密测试=============
    @Test
    public void RC2() throws Exception {
        excute(RCCipherAlgorithms.RC2);
    }

    @Test
    public void RC4() throws Exception {
        excute(RCCipherAlgorithms.RC4);
    }
}
