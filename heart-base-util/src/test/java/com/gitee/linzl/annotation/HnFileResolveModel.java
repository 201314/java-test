package com.gitee.linzl.annotation;

import java.util.Date;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * 湖南代扣文件以\t分隔,换行结束
 * 
 * 将银行或交通系统返回的信息进行解析，还原为模型
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2019年4月28日
 */
@Setter
@Getter
public class HnFileResolveModel {
	@FileField(order = 0)
	private String orgId;

	@FileField(order = 1, format = "yyyy-MM-dd")
	private Date statisDate;

	@FileField(order = 2)
	private String batchNo;

	@FileField(order = 3)
	private long eExitCnt;

	@FileField(order = 4)
	private long eExitETCMoneySum;

	@FileField(order = 5)
	private long disputeCnt;

	@FileField(order = 6)
	private long disputeETCMoneySum;

	public static void main(String[] args)
			throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		HnFileResolveModel model1 = new HnFileResolveModel();
		model1.setOrgId("103");
		model1.setStatisDate(new Date());
		model1.setBatchNo("1552047600108");
		model1.setEExitCnt(5635);
		model1.setEExitETCMoneySum(20817349);
		String content = "103\t2019-03-08\t1552047600108\t5635\t20817349\t0\t0";
		HnFileResolveModel model2 = ToObjectBuilder.toObject(0, content, HnFileResolveModel.class, "\t",
				System.lineSeparator());
		System.out.println(JSON.toJSONString(model2));
	}
}
