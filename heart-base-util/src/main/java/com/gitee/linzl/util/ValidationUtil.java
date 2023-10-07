package com.gitee.linzl.util;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Java表单验证工具类
 * 
 * @author linzl
 * @date 2017/01/09
 */
public class ValidationUtil {
	/**
	 * 手机号码验证表达式
	 */
	public static final String CELL_PHONE_NUMBER = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$";
	/**
	 * 电话号码验证表达式
	 */
	public static final String PHONE_NUMBER = "^(\\d{3,4}-?)?\\d{7,9}$";
	/**
	 * URL验证表达式
	 */
	public static final String URL = "^http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";
	/**
	 * 匹配密码，以字母开头，长度在6-12之间，只能包含字符、数字和下划线。
	 */
	public static final String PWD = "^[a-zA-Z]\\w{6,12}$";
	/**
	 * 匹配Email地址
	 */
	public static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
	/**
	 * 匹配非负整数（正整数+0）
	 */
	public static final String POSITIVE_INTEGER = "^[+]?\\d+$";
	/**
	 * 匹配浮点数
	 */
	public static final String FLOAT = "^[-\\+]?\\d+(\\.\\d+)?$";
	/**
	 * 匹配固定位数值的浮点数 例如 .前 只能1-9 .后为0-7
	 */
	public static final String REGULAR_FLOAT = "\\d{1,9}\\.*\\d{0,7}";
	/**
	 * 只能输入数字 0-9
	 */
	public static final String DIGITS = "^[0-9]*$";
	/**
	 * 18身份证号码验证
	 */
	public static final String ID_NUMBER = "^(11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65)\\d{3}(18|19|(2\\d))\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|[1-2]0|3[0-1])\\d{3}[0-9Xx]$";
	/**
	 * 邮政编码验证
	 */
	public static final String POSTALCODE = "^[0-9]{6}$";
	/**
	 * 判断是否为合法字符(a-zA-Z0-9-_)
	 */
	public static final String VALID_CHARACTER = "^[A-Za-z0-9_-]+$";
	/**
	 * 判断英文字符(a-zA-Z)
	 */
	public static final String ENGLISH = "^[A-Za-z]+$";
	/**
	 * 判断中文字符(包括汉字和符号)
	 */
	public static final String CHINESE_CHAR = "^[\u0391-\uFFE5]+$";
	/**
	 * 匹配汉字
	 */
	public static final String CHINESE = "^[\u4e00-\u9fa5]+$";

	/**
	 * 验证字符，只能包含中文、英文、数字、下划线等字符。
	 */
	public static final String STRING_CHECK = "^[a-zA-Z0-9\u4e00-\u9fa5-_]+$";
	/**
	 * 车牌号码
	 */
	public static final String CAR_NO = "^([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z0-9]{1}[A-Z0-9]{1}([京津沪渝桂蒙宁新藏冀晋辽吉黑苏浙皖赣闽鲁粤鄂湘豫川云贵陕甘青琼])?[A-NP-Z0-9]{1}[A-NP-Z0-9]{3}[A-NP-Z0-9挂学警港澳领试超外]{1}([A-NP-Z0-9外])?)|([A-Z0-9]{7})$";

	/**
	 * 用户名不少于两位且只能为汉字、英文、数字及_ 最少2位 最多16位
	 * 
	 * @param str
	 * @return
	 */
	public final static boolean isUserName(String str) {
		return match(str, "(^[A-Za-z0-9]{2,16}$)|(^[\u4E00-\u9FA5]{2,8}$)");
	}

	/**
	 * 匹配URL地址
	 * 
	 * @param str
	 */
	public final static boolean isUrl(String str) {
		return match(str, URL);
	}

	/**
	 * 匹配密码，以字母开头，长度在6-12之间，只能包含字符、数字和下划线。
	 * 
	 * @param str
	 */
	public final static boolean isPwd(String str) {
		return match(str, PWD);
	}

	/**
	 * 验证字符，只能包含中文、英文、数字、下划线等字符。
	 * 
	 * @param str
	 */
	public final static boolean stringCheck(String str) {
		return match(str, STRING_CHECK);
	}

	/**
	 * 匹配Email地址
	 * 
	 * @param str
	 */
	public final static boolean isEmail(String str) {
		return match(str, EMAIL);
	}

	/**
	 * 匹配非负整数（正整数+0）
	 * 
	 * @param str
	 */
	public final static boolean isPositiveInteger(String str) {
		return match(str, POSITIVE_INTEGER);
	}

	/**
	 * 判断数值类型，包括非负整数和浮点数
	 * 
	 * @param str
	 */
	public final static boolean isNumeric(String str) {
		if (isFloat(str) || isPositiveInteger(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 只能输入数字
	 * 
	 * @param str
	 */
	public final static boolean isDigits(String str) {
		return match(str, DIGITS);
	}

	/**
	 * 匹配浮点数
	 * 
	 * @param str
	 */
	public final static boolean isFloat(String str) {
		return match(str, FLOAT);
	}

	/**
	 * 匹配浮点数
	 * 
	 * @param str
	 */
	public final static boolean isRegularFloat(String str) {
		return match(str, REGULAR_FLOAT);
	}

	/**
	 * 联系电话(手机/电话皆可)验证
	 * 
	 * @param text
	 */
	public final static boolean isTel(String text) {
		if (isCellPhone(text) || isPhone(text)) {
			return true;
		}
		return false;
	}

	/**
	 * 电话号码验证
	 * 
	 * @param text
	 */
	public final static boolean isPhone(String text) {
		return match(text, PHONE_NUMBER);
	}

	/**
	 * 手机号码验证
	 * 
	 * @param text
	 */
	public final static boolean isCellPhone(String text) {
		if (text.length() != 11) {
			return false;
		}
		return match(text, CELL_PHONE_NUMBER);
	}

	/**
	 * 身份证号码验证
	 * 
	 * @param text
	 */
	public final static boolean isIdCardNo(String text) {
		return match(text, ID_NUMBER);
	}

	/**
	 * 邮政编码验证
	 * 
	 * @param text
	 */
	public final static boolean isPostalCode(String text) {
		return match(text, POSTALCODE);
	}

	/**
	 * 判断是否为合法字符(a-zA-Z0-9-_)
	 * 
	 * @param text
	 */
	public final static boolean isRightfulString(String text) {
		return match(text, VALID_CHARACTER);
	}

	/**
	 * 判断英文字符(a-zA-Z)
	 * 
	 * @param text
	 */
	public final static boolean isEnglish(String text) {
		return match(text, ENGLISH);
	}

	/**
	 * 判断中文字符(包括汉字和符号)
	 * 
	 * @param text
	 */
	public final static boolean isChineseChar(String text) {
		return match(text, CHINESE_CHAR);
	}

	/**
	 * 匹配汉字
	 * 
	 * @param text
	 */
	public final static boolean isChinese(String text) {
		return match(text, CHINESE);
	}

	/**
	 * 是否包含中英文特殊字符，除英文"-_"字符外
	 * 
	 * @param text
	 */
	public static boolean isContainsSpecialChar(String text) {
		if (StringUtils.isBlank(text)) {
			return false;
		}
		String[] chars = { "[", "`", "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "+", "=", "|", "{", "}",
				"'", ":", ";", "'", ",", "[", "]", ".", "<", ">", "/", "?", "~", "！", "@", "#", "￥", "%", "…", "&", "*",
				"（", "）", "—", "+", "|", "{", "}", "【", "】", "‘", "；", "：", "”", "“", "’", "。", "，", "、", "？", "]" };
		for (String ch : chars) {
			if (text.contains(ch)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 过滤中英文特殊字符，除英文"-_"字符外
	 * 
	 * @param text
	 * @return
	 */
	public static String stringFilter(String text) {
		String regExpr = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regExpr);
		Matcher m = p.matcher(text);
		return m.replaceAll("").trim();
	}

	/**
	 * 过滤html代码
	 * 
	 * @param inputString
	 *            含html标签的字符串
	 * @return
	 */
	public static String htmlFilter(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		java.util.regex.Pattern p_ba;
		java.util.regex.Matcher m_ba;

		try {
			// 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>}
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";

			// 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>}
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String patternStr = "\\s+";

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			p_ba = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
			m_ba = p_ba.matcher(htmlStr);
			htmlStr = m_ba.replaceAll(""); // 过滤空格

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}
		return textStr;// 返回文本字符串
	}

	public final static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0 || "null".equals(str.toLowerCase());
	}

	public final static boolean isNotEmtpy(String str) {
		return !isEmpty(str);
	}

	public final static boolean isEmpty(Integer integer) {
		if (integer == null || integer == 0) {
			return true;
		}
		return false;
	}

	public final static boolean isNotEmtpy(Integer integer) {
		return !isEmpty(integer);
	}

	public final static boolean isEmpty(Long longs) {
		if (longs == null || longs == 0) {
			return true;
		}
		return false;
	}

	public final static boolean isNotEmtpy(Long longs) {
		return !isEmpty(longs);
	}

	public final static boolean isEmpty(Collection<?> collection) {
		if (collection == null || collection.size() == 0) {
			return true;
		}
		return false;
	}

	public final static boolean isNotEmtpy(Collection<?> collection) {
		return !isEmpty(collection);
	}

	public final static boolean isEmpty(Map<Object, Object> map) {
		if (map == null || map.size() == 0) {
			return true;
		}
		return false;
	}

	public final static boolean isNotEmtpy(Map<Object, Object> map) {
		return !isEmpty(map);
	}

	public final static boolean isEmpty(Object obj) {
		return Objects.isNull(obj);
	}

	public final static boolean isNotEmtpy(Object obj) {
		return Objects.nonNull(obj);
	}

	public final static boolean isEmpty(Object[] objs) {
		if (objs == null || objs.length == 0) {
			return true;
		}
		return false;
	}

	public final static boolean isNotEmtpy(Object[] objs) {
		return !isEmpty(objs);
	}

	/**
	 * 正则表达式匹配
	 * 
	 * @param text
	 *            待匹配的文本
	 * @param reg
	 *            正则表达式
	 * @author huanghp
	 */
	public final static boolean match(String text, String reg) {
		if (StringUtils.isBlank(text) || StringUtils.isBlank(reg)) {
			return false;
		}
		return Pattern.compile(reg).matcher(text).matches();
	}

	public static void main(String[] args) {
		System.out.println("0或4:" + ValidationUtil.match("湘EG9135",
				"^([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z0-9]{1}[A-Z0-9]{1}([京津沪渝桂蒙宁新藏冀晋辽吉黑苏浙皖赣闽鲁粤鄂湘豫川云贵陕甘青琼])?[A-NP-Z0-9]{1}[A-NP-Z0-9]{3}[A-NP-Z0-9挂学警港澳领试超外]{1}([A-NP-Z0-9外])?)|([A-Z0-9]{7})$"));
		// System.out.println("用户名验证：" + ValidationUtil.isUserName("e22222222"));
		// System.out.println("密码验证："+RegexUtil.isDigits("0"));
		// System.out.println("密码验证："+RegexUtil.isPwd("jjj9090--"));
		// System.out.println("手机号码验证: "+RegexUtil.isCellPhone("18918611111"));
		// System.out.println("电话号码验证: "+RegexUtil.isPhone("8889333"));
		// System.out.println("电话号码验证: "+RegexUtil.isNumeric("888.9333"));
		// System.out.println("过滤中英文特殊字符:
		// "+RegexUtil.stringFilter("中国~~!#$%%."));
		// System.out.println("是否包含中英文特殊字符:
		// "+RegexUtil.isContainsSpecialChar("12"));
		// System.out.println("过滤html代码:
		// "+RegexUtil.htmltoText("<JAVASCRIPT>12</JAVASCRIPT>DDDDD"));
		// System.out.println("判断中文字符: "+RegexUtil.isChineseChar("中国！"));
		// System.out.println("匹配汉字: "+RegexUtil.isChinese("中国！"));
		// System.out.println("判断英文字符: "+RegexUtil.isEnglish("abc!"));
		// System.out.println("判断合法字符:
		// "+RegexUtil.isRightfulString("abc_-11AAA"));
		// System.out.println("邮政编码验证: "+RegexUtil.isZipCode("162406"));
		System.out.println("身份证号码验证: " + isIdCardNo("35052419880210133X"));

		// System.out.println("匹配密码: "+RegexUtil.isPwd("d888d_ddddd"));
		// System.out.println("匹配密码: "+RegexUtil.isUrl("http://baidu.com"));
		// System.out.println("验证字符: "+RegexUtil.stringCheck("中文aabc001_-"));
		// System.out.println(isEmail("416501600@qq.com"));
		// http://baidu.com www.baidu.com baidu.com
		// System.out.println(NumberUtils.toInt("-0000000002"));
	}

}