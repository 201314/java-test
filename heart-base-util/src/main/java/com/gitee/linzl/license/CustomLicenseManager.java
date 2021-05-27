package com.gitee.linzl.license;

import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseContentException;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseNotary;
import de.schlichtherle.license.LicenseParam;
import de.schlichtherle.license.NoLicenseInstalledException;
import de.schlichtherle.xml.GenericCertificate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;

/**
 * 自定义LicenseManager，用于增加额外的服务器硬件信息校验
 */
@Slf4j
public class CustomLicenseManager extends LicenseManager {
    private static final String XML_CHARSET = "UTF-8";
    private static final int DEFAULT_BUFSIZE = 8 * 1024;

    public CustomLicenseManager(LicenseParam param) {
        super(param);
    }

    /**
     * 复写verify方法，调用本类中的validate方法，校验IP地址、Mac地址等其他信息
     */
    @Override
    protected synchronized LicenseContent verify(final LicenseNotary notary)
            throws Exception {
        GenericCertificate certificate = getCertificate();
        if (Objects.nonNull(certificate)) {
            return (LicenseContent) certificate.getContent();
        }

        // Load license key from preferences,
        final byte[] key = getLicenseKey();
        if (Objects.isNull(key)) {
            throw new NoLicenseInstalledException(getLicenseParam().getSubject());
        }
        certificate = getPrivacyGuard().key2cert(key);
        notary.verify(certificate);
        final LicenseContent content = (LicenseContent) this.load(certificate.getEncoded());
        this.validate(content);
        setCertificate(certificate);

        return content;
    }

    /**
     * 复写validate方法，增加IP地址、Mac地址等其他信息校验
     */
    @Override
    protected synchronized void validate(final LicenseContent content)
            throws LicenseContentException {
        super.validate(content);

        //2. 然后校验自定义的License中可被允许的参数信息
        LicenseCheckModel expectedModel = (LicenseCheckModel) content.getExtra();
        if (Objects.isNull(expectedModel)) {
            return;
        }

        LicenseCheckModel serverModel = ServerInfosFactory.getObject().getServerInfos();
        if (Objects.isNull(serverModel)) {
            throw new LicenseContentException("无法获取服务器参数信息");
        }

        //校验IP地址
        if (!checkIpAddress(expectedModel.getIpAddress(), serverModel.getIpAddress())) {
            throw new LicenseContentException("当前服务器的IP没在授权范围内");
        }

        //校验Mac地址
        if (!checkIpAddress(expectedModel.getMacAddress(), serverModel.getMacAddress())) {
            throw new LicenseContentException("当前服务器的Mac地址没在授权范围内");
        }

        //校验主板序列号
        if (!checkSerial(expectedModel.getMainBoardSerial(), serverModel.getMainBoardSerial())) {
            throw new LicenseContentException("当前服务器的主板序列号没在授权范围内");
        }

        //校验CPU序列号
        if (!checkSerial(expectedModel.getCpuSerial(), serverModel.getCpuSerial())) {
            throw new LicenseContentException("当前服务器的CPU序列号没在授权范围内");
        }
    }

    private Object load(String encoded) {
        try (BufferedInputStream inputStream =
                     new BufferedInputStream(new ByteArrayInputStream(encoded.getBytes(XML_CHARSET)), DEFAULT_BUFSIZE);
             XMLDecoder decoder = new XMLDecoder(inputStream)) {
            return decoder.readObject();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 校验当前服务器的IP/Mac地址是否在可被允许的IP范围内
     * 如果存在IP在可被允许的IP/Mac地址范围内，则返回true
     */
    private boolean checkIpAddress(List<String> expectedList, List<String> serverList) {
        if (CollectionUtils.isNotEmpty(expectedList) && CollectionUtils.isEmpty(serverList)) {
            return false;
        }
        if (CollectionUtils.isNotEmpty(expectedList) && CollectionUtils.isNotEmpty(serverList)) {
            return expectedList.stream().filter(ip -> serverList.contains(ip)).findFirst().isPresent();
        }
        return CollectionUtils.isEmpty(expectedList);
    }

    /**
     * 校验当前服务器硬件（主板、CPU等）序列号是否在可允许范围内
     */
    private boolean checkSerial(String expectedSerial, String serverSerial) {
        if (StringUtils.isNotBlank(expectedSerial) && StringUtils.isBlank(serverSerial)) {
            return false;
        }
        if (StringUtils.isNotBlank(expectedSerial) && StringUtils.isNotBlank(serverSerial)) {
            return StringUtils.equals(expectedSerial, serverSerial);
        }
        return StringUtils.isBlank(expectedSerial);
    }
}