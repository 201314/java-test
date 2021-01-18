package com.gitee.linzl.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 定义restful api时，可使用表达式，将需要返回的内容进行占位
 *
 * TODO 考虑使用 Ognl来替换
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年9月18日
 */
public class ApiParamUtil {
	private static final String INCLUDE_BRACKETS_EXPRESSION = "\\$\\{[a-zA-Z0-9.]*\\}";
	private static final String EXCLUDE_BRACKETS_EXPRESSION = "(?<=\\$\\{)[a-zA-Z0-9.]+[^\\}]";

	/**
	 * 找出所有表达式
	 *
	 * @param content
	 * @return
	 */
	public static List<String> findAllExpression(String content) {
		Matcher match = Pattern.compile(INCLUDE_BRACKETS_EXPRESSION).matcher(content);
		List<String> expression = new ArrayList<>();
		while (match.find()) {
			String target = match.group();
			Matcher actualKey = Pattern.compile(EXCLUDE_BRACKETS_EXPRESSION).matcher(target);
			if (actualKey.find()) {
				expression.add(actualKey.group());
			}
		}
		return expression;
	}

	/**
	 * 获取表达式对应的值
	 *
	 * @param content
	 * @param keysList
	 * @return
	 */
	public static Map<String, Object> getExpressionVal(String content, List<String> keysList) {
		JSONObject obj = JSON.parseObject(content);
		Map<String, Object> map = new HashMap<>();

		for (String key : keysList) {
			Object value;
			if (key.indexOf(".") < 0) {// 单个键
				value = obj.get(key);
				map.put(key, String.valueOf(value == null ? "" : value));
			} else {// user.school.name
				String[] keys = StringUtils.split(key, ".");
				int index = 0;
				int length = keys.length;
				JSONObject temp = obj;
				while (index < length) {
					if ((value = temp.get(keys[index])) instanceof JSONObject) {
						temp = (JSONObject) value;
					} else if (value instanceof String) {
						map.put(key, String.valueOf(value == null ? "" : value));
					} else if (value instanceof Number) {
						map.put(key, value);
					}
					index++;
				}
			}
		}
		return map;
	}

	/**
	 * 替换所有表达式为真正需要的值
	 *
	 * @param content
	 * @param expressionVal
	 * @return
	 */
	public static String replaceExpression(String content, Map<String, Object> expressionVal) {
		Matcher match = Pattern.compile(INCLUDE_BRACKETS_EXPRESSION).matcher(content);
		if (match.find()) {
			String target = match.group();
			Matcher actualKey = Pattern.compile(EXCLUDE_BRACKETS_EXPRESSION).matcher(target);
			if (actualKey.find()) {
				Object value = expressionVal.get(actualKey.group());
				String str = match.replaceFirst(value == null ? "" : value.toString());
				str = replaceExpression(str, expressionVal);
				return str;
			}
		}
		return content;
	}

	public static String replaceReturnBodyExpression(String content) {
		List<String> keysList = findAllExpression(content);
		Map<String, Object> expressionVal = getExpressionVal(content, keysList);
		return replaceExpression(content, expressionVal);
	}
}
