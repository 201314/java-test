package com.gitee.linzl.cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Objects;

/**
 * @author linzhenlie-jk
 * @date 2021/4/20
 */
public interface ICipher {
    int STREAM_BUFFER_LENGTH = 1024;

    default Cipher getCipher(Key key, IAlgorithm algorithm, IvParameterSpec iv, int mode) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        Cipher cipher = customCipher(false, algorithm);

        IvParameterSpec ivParameterSpec = getIvParameterSpec(algorithm, iv);
        if (Objects.nonNull(ivParameterSpec)) {
            // 初始化Cipher对象，设置为解密模式
            cipher.init(mode, key, ivParameterSpec);
        } else {
            cipher.init(mode, key);
        }
        return cipher;
    }

    default Cipher customCipher(boolean bcPresent, IAlgorithm algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        return bcPresent ? Cipher.getInstance(algorithm.getCipherAlgorithm(),
                BouncyCastleProvider.PROVIDER_NAME) : Cipher.getInstance(algorithm.getCipherAlgorithm());
    }

    default IvParameterSpec getIvParameterSpec(IAlgorithm algorithm, IvParameterSpec iv) {
        return iv;
    }

    byte[] encrypt(byte[] data) throws Exception;

    void encrypt(File sourceFile, File targetFile) throws Exception;

    void encrypt(File sourceFile, OutputStream targetFile) throws Exception;

    void encrypt(InputStream sourceFile, OutputStream targetFile) throws Exception;

    byte[] decrypt(byte[] data) throws Exception;

    void decrypt(File encryptedFile, File decryptFile) throws Exception;

    void decrypt(File encryptedFile, OutputStream decryptFile) throws Exception;

    void decrypt(InputStream encryptedFile, OutputStream decryptFile) throws Exception;
}
