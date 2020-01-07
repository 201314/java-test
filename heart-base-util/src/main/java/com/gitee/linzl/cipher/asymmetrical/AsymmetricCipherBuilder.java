package com.gitee.linzl.cipher.asymmetrical;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import com.gitee.linzl.cipher.AbstractCipher;
import com.gitee.linzl.cipher.IAlgorithm;

/**
 * 非对称加解密，公钥加签-->私钥解密，私钥加签--》公钥验签
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
 * 在服务端，使用私钥加密数据传给其他人，其他人再用公钥解密(一般不会这么做，普遍做法还是公钥加密私钥解密，只是双方交换各自生成的公钥)。
 * * <p>
 * 签名：只能加签验签，无法解签，私钥加签，公钥验签
 * @email 2225010489@qq.com
 * @date 2018年11月6日
 */
public class AsymmetricCipherBuilder {
    private AsymmetricCipherBuilder() {
    }

    public static class SignBuilder {
        private byte[] data;
        private PrivateKey privateKey;
        private IAlgorithm algorithm;

        /**
         * 非对称加密，加载私钥字节
         *
         * @param algorithm
         * @param privateKeyByte 私钥字节
         * @throws Exception
         */
        public SignBuilder(IAlgorithm algorithm, byte[] privateKeyByte) throws Exception {
            // 实例化密钥生成器
            String algorithmName = algorithm.getKeyAlgorithm();
            this.algorithm = algorithm;
            try {
                KeyFactory keyFactory = KeyFactory.getInstance(algorithmName);
                EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyByte);
                // RSAPrivateKey
                privateKey = keyFactory.generatePrivate(privateKeySpec);
            } catch (NoSuchAlgorithmException e) {
                throw new Exception("无此算法");
            } catch (InvalidKeySpecException e) {
                throw new Exception("私钥非法");
            }
        }

        public SignBuilder(IAlgorithm algorithm, String base64PrivateKey) throws Exception {
            this(algorithm, Base64.getDecoder().decode(base64PrivateKey));
        }

        public SignBuilder sign(byte[] data) {
            this.data = data;
            return this;
        }

        public SignBuilder sign(String base64Data) {
            this.data = Base64.getDecoder().decode(base64Data);
            return this;
        }

        /**
         * 对数据进行签名
         *
         * @return
         * @throws Exception
         */
        public byte[] finish() throws Exception {
            // 用私钥对信息生成数字签名
            return AbstractCipher.sign(data, privateKey, algorithm);
        }

        public String finishToBase64() throws Exception {
            // 用私钥对信息生成数字签名
            byte[] out = AbstractCipher.sign(data, privateKey, algorithm);
            return Base64.getEncoder().encodeToString(out);
        }
    }

    public static class VerifySignBuilder {
        /**
         * 未签名前的数据
         **/
        private byte[] data;
        private PublicKey publicKey;
        private IAlgorithm algorithm;
        /**
         * 签名数据
         */
        private byte[] signData;

        /**
         * 非对称加密，加载公钥字节
         *
         * @param algorithm
         * @param publicKeyByte 公钥字节
         * @throws Exception
         */
        public VerifySignBuilder(IAlgorithm algorithm, byte[] publicKeyByte) throws Exception {
            // 实例化密钥生成器
            String algorithmName = algorithm.getKeyAlgorithm();
            this.algorithm = algorithm;
            try {
                KeyFactory keyFactory = KeyFactory.getInstance(algorithmName);
                EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyByte);
                // RSAPublicKey
                this.publicKey = keyFactory.generatePublic(keySpec);
            } catch (NoSuchAlgorithmException e) {
                throw new Exception("无此算法");
            } catch (InvalidKeySpecException e) {
                throw new Exception("私钥非法");
            }
        }

        public VerifySignBuilder(IAlgorithm algorithm, String base64PublicKey) throws Exception {
            this(algorithm, Base64.getDecoder().decode(base64PublicKey));
        }

        public VerifySignBuilder verify(byte[] sourceData, byte[] signData) {
            this.data = sourceData;
            this.signData = signData;
            return this;
        }

        public VerifySignBuilder verify(String sourceBase64Data, byte[] signData) {
            this.data = Base64.getDecoder().decode(sourceBase64Data);
            this.signData = signData;
            return this;
        }

        public VerifySignBuilder verify(byte[] sourceData, String base64Sign) {
            this.data = sourceData;
            this.signData = Base64.getDecoder().decode(base64Sign);
            return this;
        }

        public VerifySignBuilder verify(String sourceBase64Data, String base64Sign) {
            this.data = Base64.getDecoder().decode(sourceBase64Data);
            this.signData = Base64.getDecoder().decode(base64Sign);
            return this;
        }

        /**
         * 验证签名
         *
         * @return
         * @throws Exception
         */
        public boolean finish() throws Exception {
            return AbstractCipher.verifySign(data, publicKey, signData, algorithm);
        }
    }

    public static class EncryptBuilder {
        private byte[] data;
        private IAlgorithm algorithm;
        private PublicKey publicKey;

        /**
         * 非对称加密，加载公钥字节
         *
         * @param algorithm
         * @param publicKeyByte 公钥字节
         * @throws Exception
         */
        public EncryptBuilder(IAlgorithm algorithm, byte[] publicKeyByte) throws Exception {
            // 实例化密钥生成器
            String algorithmName = algorithm.getKeyAlgorithm();
            this.algorithm = algorithm;
            try {
                KeyFactory keyFactory = KeyFactory.getInstance(algorithmName);
                EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyByte);
                // RSAPublicKey
                this.publicKey = keyFactory.generatePublic(keySpec);

            } catch (NoSuchAlgorithmException e) {
                throw new Exception("无此算法");
            } catch (InvalidKeySpecException e) {
                throw new Exception("私钥非法");
            }
        }

        public EncryptBuilder(IAlgorithm algorithm, String base64PrivateKey) throws Exception {
            this(algorithm, Base64.getDecoder().decode(base64PrivateKey));
        }

        public EncryptBuilder encrypt(byte[] data) {
            this.data = data;
            return this;
        }

        public EncryptBuilder encrypt(String base64Data) {
            this.data = Base64.getDecoder().decode(base64Data);
            return this;
        }

        public byte[] finish() throws Exception {
            return AbstractCipher.encrypt(data, publicKey, algorithm);
        }

        public String finishToBase64() throws Exception {
            byte[] out = AbstractCipher.encrypt(data, publicKey, algorithm);
            return Base64.getEncoder().encodeToString(out);
        }
    }

    public static class DecryptBuilder {
        private byte[] data;
        private IAlgorithm algorithm;
        private PrivateKey privateKey;

        public DecryptBuilder(IAlgorithm algorithm, byte[] privateKeyByte) throws Exception {
            // 实例化密钥生成器
            String algorithmName = algorithm.getKeyAlgorithm();
            this.algorithm = algorithm;
            try {
                KeyFactory keyFactory = KeyFactory.getInstance(algorithmName);
                EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyByte);
                // RSAPrivateKey
                privateKey = keyFactory.generatePrivate(privateKeySpec);
            } catch (NoSuchAlgorithmException e) {
                throw new Exception("无此算法");
            } catch (InvalidKeySpecException e) {
                throw new Exception("私钥非法");
            }
        }

        public DecryptBuilder(IAlgorithm algorithm, String base64PrivateKey) throws Exception {
            this(algorithm, Base64.getDecoder().decode(base64PrivateKey));
        }

        public DecryptBuilder decrypt(byte[] data) {
            this.data = data;
            return this;
        }

        public DecryptBuilder decrypt(String base64Data) {
            this.data = Base64.getDecoder().decode(base64Data);
            return this;
        }

        public byte[] finish() throws Exception {
            return AbstractCipher.decrypt(data, privateKey, algorithm);
        }

        public String finishToString() throws Exception {
            byte[] out = AbstractCipher.encrypt(data, privateKey, algorithm);
            return new String(out);
        }
    }
}
