package com.gitee.linzl.strategy.pattern;

//没有任何 优惠活动
public class NormalStrategy implements Strategy {
	public double caculatePreferential(double money) {
		return money;
	}

	@Override
	public Integer type() {
		return 0;
	}
}
