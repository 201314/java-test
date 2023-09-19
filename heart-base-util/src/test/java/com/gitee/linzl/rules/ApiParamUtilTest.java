package com.gitee.linzl.rules;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

public class ApiParamUtilTest {
    String content = "{\n" +
        "\t\"callbackBody\": {\n" +
        "\t\t\"自定义返回key1\": \"${requestData.loginTime}\",\n" +
        "\t\t\"自定义返回key2\": \"${requestData.latitude}\",\n" +
        "\t\t\"自定义返回key3\": \"${requestData.arrayVal[1]}\",\n" +
        "\t\t\"自定义返回key4\": \"${requestData.arrayVal2[1].loginTime2}\"\n" +
        "\t},\n" +
        "\t\"requestData\": {\n" +
        "\t\t\"loginTime\": \"设备首次登录时间\",\n" +
        "\t\t\"latitude\": \"纬度\",\n" +
        "\t\t\"storage\": \"${md5}\",\n" +
        "\t\t\"longitude\": \"${number}\",\n" +
        "\t\t\"arrayVal\": [11, 22, 33],\n" +
        "\t\t\"arrayVal2\": [{\n" +
        "\t\t\t\"loginTime2\": \"设备首次登录时间11\"\n" +
        "\t\t}, {\n" +
        "\t\t\t\"loginTime2\": \"设备首次登录时间22\"\n" +
        "\t\t}]\n" +
        "\t},\n" +
        "\t\"md5\": \"保证整个JSON完整性\",\n" +
        "\t\"number\": \"11223\"\n" +
        "}";

    @Test
    public void testReturnBody4() {
        System.out.println(ApiPropertyParser.parse(content));
    }
}
