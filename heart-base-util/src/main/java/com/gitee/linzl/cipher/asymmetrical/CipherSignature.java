package com.gitee.linzl.cipher.asymmetrical;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.gitee.linzl.cipher.asymmetrical.AsymmetricCipherBuilder.EncryptVerifyBuilder;
import com.gitee.linzl.lang.StringUtil;

public class CipherSignature {
	/**
	 * 
	 * @param sortedParams
	 * @return
	 */
	public static String getSignContent(Map<String, String> sortedParams) {
		List<String> keys = new ArrayList<>(sortedParams.keySet());
		Collections.sort(keys);
		StringBuffer content = new StringBuffer();
		int index = 0;
		for (int i = 0,size = keys.size(); i < size; i++) {
			String key = keys.get(i);
			String value = sortedParams.get(key);
			if (StringUtil.isEmpty(key, value)) {
				content.append((index == 0 ? "" : "&") + key + "=" + value);
				index++;
			}
		}
		return content.toString();
	}

	public static String getSignCheckContent(Map<String, String> params) {
		if (Objects.isNull(params)) {
			return null;
		}

		params.remove("sign");
		params.remove("sign_type");
		return getSignContent(params);
	}

	public static boolean rsa256CheckContent(byte[] content, byte[] sign, String base64PublicKey)
			throws Exception {
		EncryptVerifyBuilder encrypt = new AsymmetricCipherBuilder.EncryptVerifyBuilder(SignatureAlgorithms.SHA256withRSA,Base64.getDecoder().decode(base64PublicKey.getBytes()));
		return encrypt.verify(content , sign);
	}
}
