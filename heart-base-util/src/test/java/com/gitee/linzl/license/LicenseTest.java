package com.gitee.linzl.license;

import org.junit.Test;

import java.io.File;
import java.util.Calendar;

/**
 * @author linzhenlie-jk
 * @date 2021/5/24
 */
public class LicenseTest {
    @Test
    public void licenseCreate() {
        // 生成license需要的一些参数
        LicenseRelationParam param = new LicenseRelationParam();
        param.setSubject("ioserver");
        param.setAlias("privateKey");
        param.setKeyPass("private_password1234");
        param.setStorePass("public_password1234");
        param.setLicense(new File("D:\\licenseTest\\license.lic"));
        param.setKeysStore(new File("D:\\licenseTest\\privateKeys.keystore"));

        Calendar issueCalendar = Calendar.getInstance();
        param.setIssuedTime(issueCalendar.getTime());
        Calendar expiryCalendar = Calendar.getInstance();
        expiryCalendar.set(2022, Calendar.DECEMBER, 31, 23, 59, 59);
        param.setExpiryTime(expiryCalendar.getTime());
        param.setConsumerType("user");
        param.setConsumerAmount(1);
        param.setDescription("证书由XXX公司生成,如过期请联系客服");
        AbstractServerInfos abstractServerInfos = new WindowsServerInfos();
        param.setLicenseCheckModel(abstractServerInfos.getServerInfos());
        LicenseTools licenseCreator = new LicenseTools();
        // 生成license
        licenseCreator.create(param);
    }

    @Test
    public void licenseVerify() {
        LicenseRelationParam param = new LicenseRelationParam();
        param.setSubject("ioserver");
        param.setAlias("publicCert");
        param.setKeyPass(null);
        param.setStorePass("public_password1234");
        param.setLicense(new File("D:\\licenseTest\\license.lic"));
        param.setKeysStore(new File("D:\\licenseTest\\publicCerts.keystore"));

        LicenseTools verify = new LicenseTools();
        verify.install(param);
        verify.verify();
    }
}
