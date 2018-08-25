package com.gitee.linzl.rules;

/**
 * LUHN算法:被用于最后一位为校验码的一串数字的校验,主要应用于解决银行卡号,社保号等重要信息传输出错问题
 * 
 * 按照从右往左的顺序,从这串数字的右边开始,包含校验码(序号为1),将偶数位数字乘以2,
 * 
 * 如果每次乘2操作的结果大于9(如 8 × 2 = 16),然后计算个位和十位数字的和(如 1 + 6 = 7)或者用这个结果减去9(如 16 - 9 =
 * 7);
 * 
 * 第一步操作过后会得到新的一串数字,计算所有数字的和(包含校验码);
 * 
 * 用第二步操作得到的和进行“模10”运算,如果结果位0,表示校验通过,否则失败。
 * 
 * @description
 * 
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年8月25日
 */
public class LuhnUtil {
	/**
	 * 根据LUHN算法得到核验位的数值
	 * 
	 * @param no
	 *            原始卡号
	 * @return 检验位的数值
	 */
	public static int getCheckSum(long no) {
		return getCheckSum(String.valueOf(no));
	}

	/**
	 * 根据LUHN算法得到核验位的数值
	 * 
	 * @param no
	 *            原始卡号
	 * @return 检验位的数值
	 */
	public static int getCheckSum(String no) {
		if (!isNumber(no)) {
			throw new IllegalArgumentException("传入参数不正常，不全是数字：" + no);
		}
		int sum = 0;
		int index = 0;
		for (int i = no.length() - 1; i >= 0; i--) {
			int n = 0;
			if (index % 2 == 0) {
				n = getEvenNumber(no.charAt(i));
			} else {
				n = Integer.parseInt(String.valueOf(no.charAt(i)));
			}
			index++;
			sum += n;
		}
		if (sum % 10 == 0) {
			return 0;
		} else {
			return 10 - sum % 10;
		}
	}

	/**
	 * 根据LUHN算法，计算偶数值的值
	 * 
	 * @param num
	 * @return
	 */
	private static int getEvenNumber(char num) {
		int n = Integer.parseInt(String.valueOf(num));
		n = n * 2;
		if (n >= 10) {
			return n / 10 + n % 10;
		} else {
			return n;
		}
	}

	/**
	 * 根据LUHN算法校验卡号是否正确
	 * 
	 * @param no
	 *            卡号
	 * @return true:正确 false:不正确
	 */
	public static boolean validateCheckSum(long no) {
		return validateCheckSum(String.valueOf(no));
	}

	/**
	 * 根据LUHN算法校验卡号是否正确
	 * 
	 * @param no
	 *            卡号
	 * @return true:正确 false:不正确
	 */
	public static boolean validateCheckSum(String no) {
		if (!isNumber(no)) {
			throw new IllegalArgumentException("传入参数不正常，不全是数字：" + no);
		}
		String verifyCode = no.substring(no.length() - 1);
		// 计算校验码
		int computeVerifyCode = getCheckSum(no.substring(0, no.length() - 1));
		// 减掉最后一位校验码
		return verifyCode.equals(String.valueOf(computeVerifyCode));
	}

	/** 检查一个字符串是否是数字组成的 */
	private static boolean isNumber(String s) {
		if (s == null || s.length() == 0) {
			return false;
		}

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		System.out.println(validateCheckSum("6217214000023002070"));
	}
}
