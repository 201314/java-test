package com.gitee.linzl.license;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.DefaultCipherParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

import java.util.prefs.Preferences;

public class LicenseManagerHolder {
    private static LicenseManager licenseManager;

    private LicenseManagerHolder() {
    }

    public static synchronized LicenseManager getInstance(LicenseRelationParam param) {
        if (licenseManager == null) {
            licenseManager = assembleLicenseManager(param);
        }
        return licenseManager;
    }

    private static LicenseManager assembleLicenseManager(LicenseRelationParam param) {
        Preferences preferences = Preferences.userNodeForPackage(LicenseManagerHolder.class);

        //设置对证书内容加密的秘钥
        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());

        KeyStoreParam privateStoreParam = new CustomKeyStoreParam(LicenseManagerHolder.class
                , param.getKeysStore()
                , param.getAlias()
                , param.getStorePass()
                , param.getKeyPass());

        LicenseParam licenseParam = new DefaultLicenseParam(param.getSubject()
                , preferences
                , privateStoreParam
                , cipherParam);

        return new CustomLicenseManager(licenseParam);
    }
}
