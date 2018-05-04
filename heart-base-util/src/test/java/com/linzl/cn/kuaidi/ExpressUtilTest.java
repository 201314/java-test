package com.linzl.cn.kuaidi;

import org.junit.Test;

import com.linzl.cn.kuaidi.ExpressUtil;

/**
 * 快递单号查询接口
 * 
 * @author liny
 *
 */
public class ExpressUtilTest {

	@Test
	public void test() {
		ExpressUtil.getExpressEntityInfo("yunda", "3902150646701");
	}

}