package com.gitee.linzl.rules;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.BooleanUtils;

import java.util.Objects;

public class PropertyParser {
    private static final String ENABLE_DEFAULT_VALUE = "true";
    private static final String DEFAULT_VALUE_SEPARATOR = ":";

    private PropertyParser() {
    }

    public static String parse(String content, JSONObject variables) {
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
                    return OgnlCache.getValue(key, variables);
                }

                final int separatorIndex = content.indexOf(defaultValueSeparator);
                String defaultValue = null;
                if (separatorIndex >= 0) {
                    key = content.substring(0, separatorIndex);
                    defaultValue = content.substring(separatorIndex + defaultValueSeparator.length());
                }
                Object val = OgnlCache.getValue(key, variables);
                return Objects.nonNull(val) ? val : defaultValue;
            }
            return "${" + content + "}";
        }
    }
}