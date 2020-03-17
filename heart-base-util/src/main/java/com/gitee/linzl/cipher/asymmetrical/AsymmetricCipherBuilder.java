package com.gitee.linzl.cipher.asymmetrical;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import com.gitee.linzl.cipher.BaseCipher;
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
 

    public static class EncryptVerifyBuilder {
        private IAlgorithm algorithm;
        private PublicKey publicKey;

        /**
         * 非对称加密，加载公钥字节
         *
         * @param algorithm
         * @param publicKeyByte 公钥字节
         * @throws Exception
         */
        public EncryptVerifyBuilder(IAlgorithm algorithm, byte[] publicKeyByte) throws Exception {
            this.algorithm = algorithm;
            this.publicKey = BaseCipher.generatePublic(algorithm, publicKeyByte);
        }

        public EncryptVerifyBuilder(IAlgorithm algorithm, String base64PrivateKey) throws Exception {
            this(algorithm, Base64.getDecoder().decode(base64PrivateKey));
        }

        public byte[] encrypt(byte[] data)throws Exception {
            return BaseCipher.encrypt(data, publicKey, algorithm);
        }

        public String encrypt(String base64Data) throws Exception {
        	byte[] data = Base64.getDecoder().decode(base64Data);
            byte[] out = BaseCipher.encrypt(data, publicKey, algorithm);
            return Base64.getEncoder().encodeToString(out);
        }

        public boolean verify(byte[] sourceData, byte[] signData)  throws Exception {
            return BaseCipher.verifySign(sourceData, publicKey, signData, algorithm);
        }

        public boolean verify(byte[] sourceData, String base64Sign)  throws Exception {
            byte[] signData = Base64.getDecoder().decode(base64Sign);
            return BaseCipher.verifySign(sourceData, publicKey, signData, algorithm);
        }

        public boolean verify(String sourceBase64Data, String base64Sign) throws Exception {
        	byte[] data = Base64.getDecoder().decode(sourceBase64Data);
            byte[] signData = Base64.getDecoder().decode(base64Sign);
            return BaseCipher.verifySign(data, publicKey, signData, algorithm);
        }
    }

    public static class DecryptSignBuilder {
        private IAlgorithm algorithm;
        private PrivateKey privateKey;

        public DecryptSignBuilder(IAlgorithm algorithm, byte[] privateKeyByte) throws Exception {
            this.algorithm = algorithm;
            this.privateKey = BaseCipher.generatePrivate(algorithm, privateKeyByte);
        }

        public DecryptSignBuilder(IAlgorithm algorithm, String base64PrivateKey) throws Exception {
            this(algorithm, Base64.getDecoder().decode(base64PrivateKey));
        }

        public byte[] decrypt(byte[] data) throws Exception {
            return BaseCipher.decrypt(data, privateKey, algorithm);
        }

        public String decrypt(String base64Data)  throws Exception {
        	byte[] data = Base64.getDecoder().decode(base64Data);
            byte[] out = BaseCipher.encrypt(data, privateKey, algorithm);
            return new String(out);
        }

        public byte[] sign(byte[] data) throws Exception {
            // 用私钥对信息生成数字签名
            byte[] out = BaseCipher.sign(data, privateKey, algorithm);
            return out;
        }

        public String sign(String base64Data) throws Exception {
        	byte[] data = Base64.getDecoder().decode(base64Data);
            // 用私钥对信息生成数字签名
            byte[] out = BaseCipher.sign(data, privateKey, algorithm);
            return Base64.getEncoder().encodeToString(out);
        }
    }
}
