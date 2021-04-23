package com.gitee.linzl.cipher;

import com.gitee.linzl.cipher.symmetric.BlowfishCipherAlgorithms;
import org.junit.Test;

public class BlowfishTest extends SymmetricBaseTest {
    // =============对称加密测试=============
    @Test
    public void Blowfish() throws Exception {
        excute(BlowfishCipherAlgorithms.Blowfish);
    }
}
