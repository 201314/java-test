package com.gitee.linzl.kuaidi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gitee.linzl.http.HttpClientUtil;

/**
 * 快递单号查询接口
 * 
 * @author liny
 *
 */
public class ExpressUtil {
	private static final Logger logger = LoggerFactory.getLogger(ExpressUtil.class);

	public static ExpressEntity getExpressEntityInfo(ExpressEnum enums, String expressNumber) {
		return getExpressEntityInfo(enums.getExpressCode(), expressNumber);
	}

	/**
	 * 根据单号查询快递情况
	 * 
	 * @param comCode
	 *            快递公司编码
	 * @param expressNumber
	 *            快递单号
	 * @return
	 */
	public static ExpressEntity getExpressEntityInfo(String comCode, String expressNumber) {
		String url = "http://www.kuaidi100.com/query";
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("type", comCode));
		list.add(new BasicNameValuePair("postid", expressNumber));
		String result = HttpClientUtil.postForm(url, list);
		logger.debug("快递查询结果result:{}", result);
		// JSONObject jsonObject = JSONObject.parseObject(result);
		// return (ExpressEntity) JSONObject.toJavaObject(jsonObject,
		// ExpressEntity.class);
		return null;
	}

}