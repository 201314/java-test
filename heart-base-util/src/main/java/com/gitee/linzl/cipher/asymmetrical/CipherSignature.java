package com.gitee.linzl.cipher.asymmetrical;

import com.gitee.linzl.cipher.asymmetrical.AsymmetricCipherBuilder.EncryptVerifyBuilder;
import com.gitee.linzl.lang.StringUtil;

import java.util.*;

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
		for (int i = 0,size = keys.size(); i < size; i++) {
			String key = keys.get(i);
			String value = sortedParams.get(key);
			if (StringUtil.isEmpty(key, value)) {
				content.append(key).append("=").append(value).append("&");
			}
		}
		// 删除最后一个&
		content.delete(content.length() - 1, content.length());
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
