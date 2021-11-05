package com.gitee.linzl.cipher.asymmetrical;

import com.gitee.linzl.cipher.IAlgorithm;
import com.gitee.linzl.cipher.ICipher;
import com.gitee.linzl.cipher.symmetric.DefaultSymmetricCipher;
import com.gitee.linzl.reflection.ClassUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.ws.rs.NotSupportedException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.Security;

/**
 * 非对称加解密:【合作方A的公钥加密,我方B的私钥加签 --> 合作方B的私钥解密，我方A的公钥验签】
 * <p>
 * 密钥管理: 密钥管理容易
 * <p>
 * 安全性:高
 * <p>
 * 速度:慢
 * <p>
 * 适合场景:适合小量数据加密，支持数字签名
 * <p>
 * 实际应用:采用非对称加密算法管理对称算法的密钥，用对称加密算法加密数据，即提高了加密速度，
 * <p>
 * 又实现了解密的安全RSA建议采用1024位的数字，ECC建议采用160位，AES采用128为即可。
 *
 * @author linzl
 * @description 数据加密传输:
 * <p>
 * 非对称加解密：如果密钥是自己生成的话,
 * <p>
 * 在网页端，公钥加密传数据给服务器，服务器私钥解密。
 * </p>
 * 如果1 : A私钥加密数据传给B,B用A的公钥解密
 * 一般不会这样做,因为这样只要拥有A的公钥,加密传输的数据都可以被解出来
 * 所以必须是 A使用B的公钥加密数据传给B,B用B的私钥解密，这样只有B可以解密出数据
 * <p>
 * 如果2 : A用B的公钥加签传给B,B用私钥验签
 * 一般不会这样做，因为这样只要B的公钥是公布出去的,所有人都用B的公钥加签,就无法保证是由A传输过来的
 * 所以必须是 A用A的私钥加签传给B,B用A的公钥验签,这样才确保是由A传输过来的数据
 * </P>
 * @email 2225010489@qq.com
 * @date 2018年11月6日
 */
public class DefaultAsymmetricCipher implements ICipher {
    private static final boolean BC_PRESENT;

    static {
        ClassLoader classLoader = DefaultSymmetricCipher.class.getClassLoader();
        BC_PRESENT = ClassUtils.isPresent("org.bouncycastle.jce.provider.BouncyCastleProvider", classLoader);

        if (BC_PRESENT && Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    private IAlgorithm algorithm;
    private Key encryptKey;
    private Key decryptKey;

    public IAlgorithm getAlgorithm() {
        return algorithm;
    }

    public Key getEncryptKey() {
        return encryptKey;
    }

    public Key getDecryptKey() {
        return decryptKey;
    }

    /**
     * 非对称加密
     *
     * @param algorithm
     * @param encryptKey 加密密钥，一般为公钥
     * @param decryptKey 解密密钥，一般为私钥
     * @throws Exception
     */
    public DefaultAsymmetricCipher(IAlgorithm algorithm, Key encryptKey, Key decryptKey) {
        this.algorithm = algorithm;
        this.encryptKey = encryptKey;
        this.decryptKey = decryptKey;
    }

    @Override
    public byte[] encrypt(byte[] data) throws Exception {
        Cipher cipher = getCipher(encryptKey, algorithm, null, Cipher.ENCRYPT_MODE);
        return doFinal(data, cipher, Cipher.ENCRYPT_MODE);
    }

    @Override
    public void encrypt(File sourceFile, File targetFile) throws Exception {
        // 不建议用非对称加密文件，这样文件会变大
        throw new NotSupportedException();
    }

    @Override
    public void encrypt(File sourceFile, OutputStream targetFile) throws Exception {
        // 不建议用非对称加密文件，这样文件会变大
        throw new NotSupportedException();
    }

    @Override
    public void encrypt(InputStream sourceFile, OutputStream targetFile) throws Exception {
        // 不建议用非对称加密文件，这样文件会变大
        throw new NotSupportedException();
    }

    @Override
    public byte[] decrypt(byte[] data) throws Exception {
        Cipher cipher = getCipher(decryptKey, algorithm, null, Cipher.DECRYPT_MODE);
        return doFinal(data, cipher, Cipher.DECRYPT_MODE);
    }

    @Override
    public void decrypt(File encryptedFile, File decryptFile) throws Exception {
        throw new NotSupportedException();
    }

    @Override
    public void decrypt(File encryptedFile, OutputStream decryptFile) throws Exception {
        throw new NotSupportedException();
    }

    @Override
    public void decrypt(InputStream encryptedFile, OutputStream decryptFile) throws Exception {
        throw new NotSupportedException();
    }

    private byte[] doFinal(byte[] data, Cipher cipher, int mode) throws Exception {
        // 执行加密操作,加密后的结果通常都会用Base64编码进行传输
        int inputLen = data.length;
        int actBlockSize = cipher.getBlockSize();
        if (Cipher.ENCRYPT_MODE == mode) {
            // 加密模式
            System.out.println("加密模式getBlockSize ===" + cipher.getBlockSize());
            System.out.println("加密模式getOutputSize===" + cipher.getOutputSize(inputLen));
            if (actBlockSize == 0) {
                actBlockSize = cipher.getOutputSize(inputLen) - 11;
            }
        } else if (Cipher.DECRYPT_MODE == mode) {
            // 解密模式
            System.out.println("解密模式getBlockSize ===" + cipher.getBlockSize());
            System.out.println("解密模式getOutputSize===" + cipher.getOutputSize(inputLen));
            if (actBlockSize == 0) {
                actBlockSize = cipher.getOutputSize(inputLen);
            }
        }
        System.out.println("actBlockSize===" + actBlockSize);

        // 如果rsa加解密数据过大，会发生 too much data for RSA block,所以必须分段加解密
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] cache;
            int offSet = 0;
            while (inputLen - offSet > 0) {
                int cipherLength = inputLen - offSet > actBlockSize ? actBlockSize : inputLen - offSet;
                cipher.update(data, offSet, cipherLength);
                cache = cipher.doFinal();
                out.write(cache, 0, cache.length);
                offSet += actBlockSize;
            }
            return out.toByteArray();
        }
    }
}
