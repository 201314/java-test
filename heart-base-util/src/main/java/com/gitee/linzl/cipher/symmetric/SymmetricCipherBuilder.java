package com.gitee.linzl.cipher.symmetric;

import java.util.Base64;
import java.util.Objects;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.gitee.linzl.cipher.BaseCipher;
import com.gitee.linzl.cipher.IAlgorithm;

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
public class SymmetricCipherBuilder {
    private SymmetricCipherBuilder() {
    }

    public static class EncryptBuilder {
        private byte[] data;
        private IAlgorithm algorithm;
        private IvParameterSpec ivParameterSpec;
        private SecretKeySpec secretKeySpec;

        public EncryptBuilder(IAlgorithm algorithm, byte[] rawSecretKey) {
            this.secretKeySpec = new SecretKeySpec(rawSecretKey, algorithm.getKeyAlgorithm());
            this.algorithm = algorithm;
        }

        public EncryptBuilder(IAlgorithm algorithm, String base64SecretKey) {
            this.secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(base64SecretKey), algorithm.getKeyAlgorithm());
            this.algorithm = algorithm;
        }

        public EncryptBuilder encrypt(byte[] data) {
            this.data = data;
            return this;
        }

        public EncryptBuilder encrypt(String base64Data) {
            this.data = Base64.getDecoder().decode(base64Data);
            return this;
        }

        public EncryptBuilder ivParameterSpec(byte[] ivParameterSpec) {
            //用密钥初始化Cipher对象
            this.ivParameterSpec = new IvParameterSpec(ivParameterSpec);
            return this;
        }

        public EncryptBuilder ivParameterSpec(String ivParameterSpec) {
            this.ivParameterSpec = new IvParameterSpec(ivParameterSpec.getBytes());
            return this;
        }

        public byte[] finish() throws Exception {
            if (Objects.isNull(ivParameterSpec)) {
                return BaseCipher.encrypt(data, secretKeySpec, algorithm);
            }
            return BaseCipher.encrypt(data, secretKeySpec, algorithm, ivParameterSpec);
        }

        public String finishToBase64() throws Exception {
            byte[] out;
            if (Objects.isNull(ivParameterSpec)) {
                out = BaseCipher.encrypt(data, secretKeySpec, algorithm);
            } else {
                out = BaseCipher.encrypt(data, secretKeySpec, algorithm, ivParameterSpec);
            }
            return Base64.getEncoder().encodeToString(out);
        }
    }

    public static class DecryptBuilder {
        private byte[] data;
        private IAlgorithm algorithm;
        private IvParameterSpec ivParameterSpec;
        private SecretKeySpec secretKeySpec;

        public DecryptBuilder(IAlgorithm algorithm, byte[] rawSecretKey) {
            this.secretKeySpec = new SecretKeySpec(rawSecretKey, algorithm.getKeyAlgorithm());
            this.algorithm = algorithm;
        }

        public DecryptBuilder(IAlgorithm algorithm, String base64SecretKey) {
            this.secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(base64SecretKey), algorithm.getKeyAlgorithm());
            this.algorithm = algorithm;
        }

        public DecryptBuilder decrypt(byte[] data) {
            this.data = data;
            return this;
        }

        public DecryptBuilder decrypt(String base64Data) {
            this.data = Base64.getDecoder().decode(base64Data);
            return this;
        }

        public DecryptBuilder ivParameterSpec(byte[] ivParameterSpec) {
            this.ivParameterSpec = new IvParameterSpec(ivParameterSpec);
            return this;
        }

        public DecryptBuilder ivParameterSpec(String ivParameterSpec) {
            this.ivParameterSpec = new IvParameterSpec(ivParameterSpec.getBytes());
            return this;
        }

        public byte[] finish() throws Exception {
            if (Objects.isNull(ivParameterSpec)) {
                return BaseCipher.decrypt(data, secretKeySpec, algorithm);
            }
            return BaseCipher.decrypt(data, secretKeySpec, algorithm, ivParameterSpec);
        }

        public String finishToString() throws Exception {
            byte[] out;
            if (Objects.isNull(ivParameterSpec)) {
                out = BaseCipher.encrypt(data, secretKeySpec, algorithm);
            } else {
                out = BaseCipher.encrypt(data, secretKeySpec, algorithm, ivParameterSpec);
            }
            return new String(out);
        }
    }
}
