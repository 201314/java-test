package com.gitee.linzl.annotation;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * 湖南代扣文件以\t分隔,换行结束
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2019年4月28日
 */
public class HnCreateModel {
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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Date getStatisDate() {
		return statisDate;
	}

	public void setStatisDate(Date statisDate) {
		this.statisDate = statisDate;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public long geteExitCnt() {
		return eExitCnt;
	}

	public void seteExitCnt(long eExitCnt) {
		this.eExitCnt = eExitCnt;
	}

	public long geteExitETCMoneySum() {
		return eExitETCMoneySum;
	}

	public void seteExitETCMoneySum(long eExitETCMoneySum) {
		this.eExitETCMoneySum = eExitETCMoneySum;
	}

	public long getDisputeCnt() {
		return disputeCnt;
	}

	public void setDisputeCnt(long disputeCnt) {
		this.disputeCnt = disputeCnt;
	}

	public long getDisputeETCMoneySum() {
		return disputeETCMoneySum;
	}

	public void setDisputeETCMoneySum(long disputeETCMoneySum) {
		this.disputeETCMoneySum = disputeETCMoneySum;
	}

	public static void main(String[] args) {
		HnCreateModel model1 = new HnCreateModel();
		model1.setOrgId("103");
		model1.setStatisDate(new Date());
		model1.setBatchNo("1552047600108");
		model1.seteExitCnt(5635);
		model1.seteExitETCMoneySum(20817349);
		System.out.println(ToStringBuilder.toString(model1, Charset.forName("GBK"), "\t"));
	}
}
