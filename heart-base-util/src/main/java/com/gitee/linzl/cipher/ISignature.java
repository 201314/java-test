package com.gitee.linzl.cipher;

import java.io.File;

/**
 * @author linzhenlie-jk
 * @date 2021/4/20
 */
public interface ISignature {
    byte[] sign(byte[] data) throws Exception;

    byte[] sign(File file) throws Exception;

    boolean verify(byte[] sourceData, byte[] signData) throws Exception;

    boolean verify(File file, byte[] signData) throws Exception;
}
