package com.gitee.linzl.rules;

import org.junit.Test;

public class ApiParamUtilTest {
	@Test
	public void testReturnBody() {
		String content = "{\r\n" + "	\"returnBody\": {\r\n"
				+ "		\"自定义返回key2\": \"${requestData.loginTime}\",\r\n"
				+ "		\"自定义返回key1\": \"${requestData.latitude}\"\r\n" + "	},\r\n" + "	\"requestData\": {\r\n"
				+ "		\"loginTime\": \"设备首次登录时间\",\r\n" + "		\"latitude\": \"纬度\",\r\n"
				+ "		\"storage\": \"${md5}\",\r\n" + "		\"longitude\":\"${number}\"\r\n" + "	},\r\n"
				+ "	\"md5\": \"保证整个JSON完整性\",\r\n" + "	 \"number\":11223\r\n" + "}";
		System.out.println(ApiParamUtil.replaceReturnBodyExpression(content));
	}
}
