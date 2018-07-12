package com.gitee.linzl.http;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import com.gitee.linzl.http.HttpClientUtil;

/**
 * 
 * @author linzl
 *
 */
public class HttpClientUtilTest {
	@Test
	public void connect() {
		String url = "http://baidu.com?112=&";
		Map<String, String> params = new HashMap<String, String>();
		params.put("name1", "11");
		params.put("name2", "22");
		HttpClientUtil.httpGet(url, params);
	}

	@Test
	public void postToWecenter() {
		// 用户注册信息post到圈子
		String url = "https://quanzi.utea20.com/api/createOrUpdateUser.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("account", "testAccount"));
		list.add(new BasicNameValuePair("uuid", "110110"));
		list.add(new BasicNameValuePair("nickname", "测试"));
		list.add(new BasicNameValuePair("mobile", "110"));
		list.add(new BasicNameValuePair("email", "99@qq.com"));
		String resultJson = HttpClientUtil.postForm(url, list);
		System.out.println(resultJson);
	}

	@Test
	// 测试汕头https同步用户请求
	public void postToUtea() {
		String id = "1";
		String cmd = "User";
		String md5key = "gzutea";
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
		String time = formatter.format(date);
		String key = DigestUtils.md5Hex(cmd + time + md5key);

		String url = "https://stapi.utea20.com/utea/api/UserHandler.ashx?id=%s&cmd=%s&t=%s&k=%s";
		url = String.format(url, id, cmd, time, key);

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("account", "testAccount"));
		list.add(new BasicNameValuePair("data", "{uuid:'110110',nickname:'我是测试账号'}"));

		String result = HttpClientUtil.postForm(url, list);
		System.out.println("优茶返回结果==>" + result);
	}

	@Test
	public void getLgl() {
		String url = "http://purplerattle.w3.luyouxia.net/data";
		String json = HttpClientUtil.httpGet(url + "?st=as0");
		System.out.println("第一次==》" + json);
		json = HttpClientUtil.httpGet(url + "?st=as1");
		System.out.println("第二次==》" + json);
		json = HttpClientUtil.httpGet(url + "?st=as2");
		System.out.println("第三次==》" + json);
		json = HttpClientUtil.httpGet(url + "?st=as3");
		System.out.println("第四次==》" + json);
		json = HttpClientUtil.httpGet(url + "?st=as4");
		System.out.println("第五次==》" + json);
	}

	@Test
	public void testRandom() {
		Random random = new Random();
		int i = 0;
		while (i < 10) {
			System.out.println(random.nextInt());
			i++;
		}
	}
}
