package com.gitee.linzl.cipher;

import com.gitee.linzl.properties.Resources;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * PS:
 * <p>
 * PS:签名：私钥签名，公钥验签。用自己的私钥签名，把公钥给别人。
 * <p>
 * Java生成的公私钥格式为 pkcs8, 而openssl默认生成的公私钥格式为 pkcs1，两者的密钥实际上是不能直接互用的
 * <p/>
 * openssl默认使用的是PEM格式，经过base64编码
 * <p/>
 * java加载密钥文件，不能带有注解
 * <p/>
 * --生成私钥，编码是PKCS#1格式
 * <p/>
 * openssl genrsa -out rsa_private_key.pem 1024
 * <p/>
 * --生成公钥
 * <p/>
 * openssl rsa -in rsa_private_key.pem -out rsa_public_key.pem -pubout
 * <p/>
 * --私钥不能直接被使用，需要进行PKCS#8编码
 * <p/>
 * openssl pkcs8 -topk8 -in rsa_private_key.pem -out pkcs8_rsa_private_key.pem
 * -nocrypt
 * <p>
 * 将rsa_public_key.pem内容复制给客户端加密用
 *
 * @author linzl
 * <p>
 * 非对称加密
 * @creatDate 2016年10月31日
 */
public class KeyPairPathUtil {
    private static final Logger logger = LoggerFactory.getLogger(KeyPairPathUtil.class);

    private static byte[] public_key_byte;
    private static byte[] private_key_byte;

    public static byte[] getPublicKeyFile() {
        if (Objects.nonNull(public_key_byte) && public_key_byte.length > 0) {
            return public_key_byte;
        }

        try (InputStream publicKey = Resources.getResourceAsStream("com/gitee/linzl/cipher/rsa_public_key.pem");) {
            if (logger.isDebugEnabled()) {
                logger.debug("加载公钥路径成功");
            }
            public_key_byte = IOUtils.toByteArray(publicKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return public_key_byte;
    }

    /**
     * 打成jar包后，读取资源文件，只有是以流的形式获取
     *
     * @return
     */
    public static byte[] getPrivateKeyFile() {
        if (Objects.nonNull(private_key_byte) && private_key_byte.length > 0) {
            return private_key_byte;
        }

        try (InputStream privateKey = Resources.getResourceAsStream("com/gitee/linzl/cipher/pkcs8_rsa_private_key.pem");) {
            if (logger.isDebugEnabled()) {
                logger.debug("加载私钥路径成功");
            }
            private_key_byte = IOUtils.toByteArray(privateKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return private_key_byte;
    }
}