package com.gitee.linzl.license;

import com.alibaba.fastjson.JSON;
import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.DefaultCipherParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;
import lombok.extern.slf4j.Slf4j;

import javax.security.auth.x500.X500Principal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.prefs.Preferences;

/**
 * License生成类
 */
@Slf4j
public class LicenseTools {
    private final static X500Principal DEFAULT_HOLDER_AND_ISSUER = new X500Principal("CN=localhost, OU=localhost, O=localhost, L=SH, ST=SH, C=CN");
    /**
     * 生成证书，用私钥
     */
    public void create(LicenseRelationParam param) {
        try {
            LicenseManager licenseManager = assembleLicenseManager(param);
            LicenseContent licenseContent = assembleLicenseContent(param);

            licenseManager.store(licenseContent, param.getLicense());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            log.info("------------------------------- 证书安装成功 -------------------------------");
            log.info(MessageFormat.format("证书有效期：{0} - {1}", format.format(licenseContent.getNotBefore()), format.format(licenseContent.getNotAfter())));
        } catch (Exception e) {
            log.error(MessageFormat.format("证书生成失败：{0}", param), e);
        }
    }

    /**
     * 安装License证书，读取证书相关的信息, 在bean加入容器的时候自动调用
     * <p>
     * 用公钥
     * @Bean(initMethod = "install", destroyMethod = "uninstall")
     */
    public void install(LicenseRelationParam param) {
        try {
            LicenseManager licenseManager = LicenseManagerHolder.getInstance(param);
            licenseManager.uninstall();
            LicenseContent licenseContent = licenseManager.install(param.getLicense());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            log.info("------------------------------- 证书安装成功 -------------------------------");
            log.info(MessageFormat.format("证书有效期：{0} - {1}", format.format(licenseContent.getNotBefore()), format.format(licenseContent.getNotAfter())));
        } catch (Exception e) {
            log.error("------------------------------- 证书安装失败 -------------------------------", e);
        }
    }

    /**
     * 卸载证书，在bean从容器移除的时候自动调用
     */
    public void uninstall() {
        try {
            LicenseManagerHolder.getInstance(null).uninstall();
        } catch (Exception e) {
            // ignore
        }
    }


    /**
     * 校验License证书
     */
    public boolean verify() {
        try {
            LicenseContent licenseContent = LicenseManagerHolder.getInstance(null).verify();
            log.debug("校验License证书:{}", JSON.toJSON(licenseContent));
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 设置证书生成正文信息
     */
    private LicenseContent assembleLicenseContent(LicenseRelationParam param) {
        LicenseContent licenseContent = new LicenseContent();
        licenseContent.setHolder(DEFAULT_HOLDER_AND_ISSUER);
        licenseContent.setIssuer(DEFAULT_HOLDER_AND_ISSUER);

        licenseContent.setSubject(param.getSubject());
        licenseContent.setIssued(param.getIssuedTime());
        licenseContent.setNotBefore(param.getIssuedTime());
        licenseContent.setNotAfter(param.getExpiryTime());
        licenseContent.setConsumerType(param.getConsumerType());
        licenseContent.setConsumerAmount(param.getConsumerAmount());
        licenseContent.setInfo(param.getDescription());

        if (Objects.nonNull(param.getLicenseCheckModel())) {
            //扩展校验，这里可以自定义一些额外的校验信息(也可以用json字符串保存)
            licenseContent.setExtra(param.getLicenseCheckModel());
        }
        return licenseContent;
    }


    private LicenseManager assembleLicenseManager(LicenseRelationParam param) {
        Preferences preferences = Preferences.userNodeForPackage(LicenseTools.class);

        KeyStoreParam keyStoreParam = new CustomKeyStoreParam(LicenseTools.class
                , param.getKeysStore()
                , param.getAlias()
                , param.getStorePass()
                , param.getKeyPass());

        //设置对证书内容加密的秘钥
        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());

        LicenseParam licenseParam = new DefaultLicenseParam(param.getSubject()
                , preferences
                , keyStoreParam
                , cipherParam);

        return new CustomLicenseManager(licenseParam);
    }
}