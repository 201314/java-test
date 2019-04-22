package com.gitee.linzl.annotation;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 一般用在银行、交通系统的文件生成
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2019年2月26日
 */
public class FileCreateModel {
	/**
	 * 汇总日期 格式为yyyyMMdd
	 */
	@FieldEncrypt(order = 0, length = 8, format = "yyyyMMdd")
	private LocalDateTime sumDate;
	/**
	 * 银行编号7位:测试数据 9700000
	 */
	@FieldEncrypt(order = 1, length = 7)
	private String bankId;
	/**
	 * 总金额14位,不足前补零
	 */
	@FieldEncrypt(order = 2, length = 14, padding = "0", direct = PaddingDirection.LEFT)
	private long chargeMoney;
	/**
	 * 生成时间 格式为yyyyMMddHHmmss
	 */
	@FieldEncrypt(order = 3, length = 14, format = "yyyyMMddHHmmss")
	private Date genTime;
	/**
	 * 银行名称长度60,不足后补空格
	 */
	@FieldEncrypt(order = 4, length = 60)
	private String bankName;

	public LocalDateTime getSumDate() {
		return sumDate;
	}

	public void setSumDate(LocalDateTime sumDate) {
		this.sumDate = sumDate;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public long getChargeMoney() {
		return chargeMoney;
	}

	public void setChargeMoney(long chargeMoney) {
		this.chargeMoney = chargeMoney;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String compute() {
		return ToStringBuilder.toString(this);
	}

	public static void main(String[] args) {
		FileCreateModel model = new FileCreateModel();
		model.setSumDate(LocalDateTime.now());
		model.setBankId("9700000");
		model.setChargeMoney(101);
		model.setGenTime(new Date());
		model.setBankName("支付宝网商银行");

		FileCreateModel model1 = new FileCreateModel();
		model1.setSumDate(LocalDateTime.now());
		model1.setBankId("9700001");
		model1.setChargeMoney(101);
		model1.setGenTime(new Date());
		model1.setBankName("支付宝网商银行");
		System.out.println("111:" + ToStringBuilder.toString(model));
		System.out.println("222:" + ToStringBuilder.toString(model1));
	}
}
