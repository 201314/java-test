package com.gitee.linzl.annotation;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 一般用在银行、交通系统的文件生成
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2019年2月26日
 */
@Setter
@Getter
public class FileCreateModel {
	/**
	 * 汇总日期 格式为yyyyMMdd
	 */
	@FileField(order = 0, length = 8, format = "yyyyMMdd")
	private LocalDateTime sumDate;
	/**
	 * 银行编号7位:测试数据 9700000
	 */
	@FileField(order = 1, length = 7)
	private String bankId;
	/**
	 * 总金额14位,不足前补零
	 */
	@FileField(order = 2, length = 14, padding = "0", direct = PaddingDirection.LEFT)
	private long chargeMoney;
	/**
	 * 生成时间 格式为yyyyMMddHHmmss
	 */
	@FileField(order = 3, length = 14, format = "yyyyMMddHHmmss")
	private Date genTime;
	/**
	 * 银行名称长度60,不足后补空格
	 */
	@FileField(order = 4, length = 60)
	private String bankName;

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
