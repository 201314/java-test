package com.gitee.linzl.cipher.asymmetrical;

import com.gitee.linzl.lang.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CipherSignature {
    /**
     * @param sortedParams
     * @return
     */
    public static String getSignContent(Map<String, String> sortedParams) {
        if (Objects.isNull(sortedParams)){
            return null;
        }
        List<String> keys = new ArrayList<>(sortedParams.keySet());
        Collections.sort(keys);
        StringBuffer content = new StringBuffer();
        for (int i = 0, size = keys.size(); i < size; i++) {
            String key = keys.get(i);
            String value = sortedParams.get(key);
            if (StringUtil.orEquals(key, "sign", "sign_type") || StringUtil.isEmpty(key, value)) {
                continue;
            }
            content.append(key).append("=").append(value).append("&");
        }
        // 删除最后一个&
        content.delete(content.length() - 1, content.length());
        return content.toString();
    }
}
