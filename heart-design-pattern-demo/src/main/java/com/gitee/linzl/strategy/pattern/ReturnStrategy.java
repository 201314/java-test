package com.gitee.linzl.strategy.pattern;

// 类似  满 100 返10 元 
public class ReturnStrategy extends Strategy {
	// 返利条件
	private double returnCondition;
	// 返利金额
	private double returnMoney;

	public ReturnStrategy(double returnCondition, double returnMoney) {
		this.returnCondition = returnCondition;
		this.returnMoney = returnMoney;
	}

	@Override
	public double caculatePreferential(double money) {

		if (money >= returnCondition) {// 达到返利条件
			return money - money / returnCondition * returnMoney;
		}
		return money;
	}

}
