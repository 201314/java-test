package com.gitee.linzl.cipher.symmetric;

import com.gitee.linzl.cipher.IAlgorithm;
import com.gitee.linzl.cipher.ICipher;
import com.gitee.linzl.reflection.ClassUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Objects;

/**
 * 对称加解密工具：加密密钥与解密密钥相同。
 * <p>
 * 密钥管理:比较难，不适合互联网，一般用于内部系统
 * <p>
 * 安全性:中
 * <p>
 * 速度:快好几个数量级
 * <p>
 * 适合场景:适合大量数据加密处理，不支持数字签名
 * <p>
 * 实际应用:采用非对称加密算法管理对称算法的密钥，用对称加密算法加密数据，即提高了加密速度，
 * <p>
 * 又实现了解密的安全RSA建议采用1024位的数字，ECC建议采用160位，AES采用128位即可。
 * <p>
 * DES(Data Encryption Standard): 数据加密标准，速度较快，适用于加密大量数据的场合;
 * <p>
 * 3DES(Triple DES): 是基于DES，对一块数据用三个不同的密钥进行三次加密，强度更高;
 * <p>
 * AES(Advanced Encryption Standard): 高级加密标准，是下一代的加密算法标准，速度快，安全级别高，AES 标准的一个实现是
 * Rijndael 算法;
 * <p>
 * RC2和RC4:用变长密钥对大量数据进行加密，比 DES 快;
 * <p>
 * Blowfish
 *
 * @author linzl
 */
public class DefaultSymmetricCipher implements ICipher {
    private static final String AES_IV_PARAMETER_SPEC = "0102030405060708";
    private static final String DES_IV_PARAMETER_SPEC = "12345678";
    private static final boolean BC_PRESENT;
    protected IAlgorithm algorithm;
    protected IvParameterSpec ivParameterSpec;
    protected SecretKeySpec secretKeySpec;

    static {
        ClassLoader classLoader = DefaultSymmetricCipher.class.getClassLoader();
        BC_PRESENT = ClassUtils.isPresent("org.bouncycastle.jce.provider.BouncyCastleProvider", classLoader);

        if (BC_PRESENT && Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public DefaultSymmetricCipher(IAlgorithm algorithm, byte[] rawSecretKey) {
        this(algorithm, rawSecretKey, null);
    }

    public DefaultSymmetricCipher(IAlgorithm algorithm, byte[] rawSecretKey, byte[] ivParameterSpec) {
        this.algorithm = algorithm;
        this.secretKeySpec = new SecretKeySpec(rawSecretKey, algorithm.getKeyAlgorithm());
        if (Objects.nonNull(ivParameterSpec)) {
            this.ivParameterSpec = new IvParameterSpec(ivParameterSpec);
        }
    }

    @Override
    public Cipher customCipher(boolean bcPresent, IAlgorithm algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        return ICipher.super.customCipher(BC_PRESENT, algorithm);
    }

    @Override
    public IvParameterSpec getIvParameterSpec(IAlgorithm algorithm, IvParameterSpec iv) {
        if (Objects.nonNull(iv) || !algorithm.getCipherAlgorithm().contains("CBC")) {
            return iv;
        }
        // 使用 CBC 模式时必须传入该参数
        if ("AES".equalsIgnoreCase(algorithm.getKeyAlgorithm())) {
            iv = new IvParameterSpec(AES_IV_PARAMETER_SPEC.getBytes());
        } else if ("DES".equalsIgnoreCase(algorithm.getKeyAlgorithm())) {
            iv = new IvParameterSpec(DES_IV_PARAMETER_SPEC.getBytes());
        }
        return iv;
    }

    @Override
    public byte[] encrypt(byte[] data) throws Exception {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            encrypt(bis, os);
            return os.toByteArray();
        }
    }

    @Override
    public void encrypt(File sourceFile, File targetFile) throws Exception {
        encrypt(sourceFile, new FileOutputStream(targetFile));
    }

    @Override
    public void encrypt(File sourceFile, OutputStream targetFile) throws Exception {
        try (BufferedInputStream istream = new BufferedInputStream(new FileInputStream(sourceFile));
             BufferedOutputStream ostream = new BufferedOutputStream(targetFile)) {
            encrypt(istream, ostream);
        }
    }

    @Override
    public void encrypt(InputStream sourceFile, OutputStream targetFile) throws Exception {
        Cipher cipher = getCipher(secretKeySpec, algorithm, ivParameterSpec, Cipher.ENCRYPT_MODE);
        System.out.println(cipher.getAlgorithm());
        try (CipherInputStream cis = new CipherInputStream(sourceFile, cipher)) {
            byte[] block = new byte[STREAM_BUFFER_LENGTH];
            int i;
            while ((i = cis.read(block)) != -1) {
                targetFile.write(block, 0, i);
            }
        }
    }

    @Override
    public byte[] decrypt(byte[] data) throws Exception {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            decrypt(bis, os);
            return os.toByteArray();
        }
    }

    @Override
    public void decrypt(File encryptedFile, File decryptFile) throws Exception {
        decrypt(encryptedFile, new FileOutputStream(decryptFile));
    }

    @Override
    public void decrypt(File encryptedFile, OutputStream decryptFile) throws Exception {
        try (BufferedInputStream istream = new BufferedInputStream(new FileInputStream(encryptedFile));
             BufferedOutputStream ostream = new BufferedOutputStream(decryptFile)) {
            decrypt(istream, ostream);
        }
    }

    @Override
    public void decrypt(InputStream encryptedFile, OutputStream decryptFile) throws Exception {
        Cipher cipher = getCipher(secretKeySpec, algorithm, ivParameterSpec, Cipher.DECRYPT_MODE);
        try (CipherOutputStream cos = new CipherOutputStream(decryptFile, cipher)) {
            byte[] block = new byte[STREAM_BUFFER_LENGTH];
            int i;
            while ((i = encryptedFile.read(block)) != -1) {
                cos.write(block, 0, i);
            }
        }
    }
}
