package com.gitee.linzl.rules;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 可使用表达式，将需要返回的内容进行占位
 *
 * 可以再看看 Ognl替换 或 apollo client的PlaceholderHelper\ spring 的PropertyPlaceholderHelper 替换
 *
 * 自己写比较好控制
 */
public class ApiPropertyParser {
    private static final String ENABLE_DEFAULT_VALUE = "true";
    private static final String DEFAULT_VALUE_SEPARATOR = ":";
    private static final String ARRAY_START = "[";
    private static final String ARRAY_END = "]";

    private ApiPropertyParser() {
    }

    public static String parse(String content) {
        JSONObject variables = JSON.parseObject(content);
        VariableTokenHandler handler = new VariableTokenHandler(variables);
        GenericTokenParser parser = new GenericTokenParser("${", "}", handler);
        return parser.parse(content);
    }

    private static class VariableTokenHandler implements TokenHandler {
        private final JSONObject variables;
        private final boolean enableDefaultValue;
        private final String defaultValueSeparator;

        private VariableTokenHandler(JSONObject variables) {
            this.variables = variables;
            this.enableDefaultValue = Boolean.parseBoolean(ENABLE_DEFAULT_VALUE);
            this.defaultValueSeparator = DEFAULT_VALUE_SEPARATOR;
        }


        @Override
        public Object handleToken(String content) {
            if (variables != null) {
                String key = content;
                if (BooleanUtils.isFalse(enableDefaultValue)) {
                    return getExpressionVal(content,variables);
                }

                final int separatorIndex = content.indexOf(defaultValueSeparator);
                String defaultValue = null;
                if (separatorIndex >= 0) {
                    key = content.substring(0, separatorIndex);
                    defaultValue = content.substring(separatorIndex + defaultValueSeparator.length());
                }
                Object val = getExpressionVal(content,variables);
                return Objects.nonNull(val) ? val : defaultValue;
            }
            return null;
        }
    }



    public static Object getExpressionVal(String key, JSONObject jsonObject) {
        String value = null;
        String[] keys = StringUtils.split(key, ".");

        // 多个键
        // user.school.name,user.school.names[1]
        int index = 0, length = keys.length;
        JSON temp = jsonObject;
        while (index < length) {
            //数组
            String keyName = keys[index];
            String keyNameTmp = keyName;
            int keyValIdx = 0;

            int start = keyName.indexOf(ARRAY_START);
            int end = keyName.indexOf(ARRAY_END);
            if (start > 0 && end > 0) {
                keyNameTmp = keyName.substring(0, start);
                keyValIdx = Integer.parseInt(keyName.substring(start + 1, end));
            }

            if (temp instanceof JSONObject) {
                value = ((JSONObject) temp).getString(keyNameTmp);
                if (keyValIdx>0) {
                    value = JSONArray.parseArray(value).getString(keyValIdx);
                }
            } else if (temp instanceof JSONArray) {
                value = ((JSONArray) temp).getString(keyValIdx);
            }

            index++;
            if (index == length){
                return value;
            }

            if (temp instanceof JSONObject) {
                temp = JSONObject.parseObject(value);
            } else if(temp instanceof JSONArray) {
                temp = JSONArray.parseArray(value);
            }
        }
        return null;
    }
}