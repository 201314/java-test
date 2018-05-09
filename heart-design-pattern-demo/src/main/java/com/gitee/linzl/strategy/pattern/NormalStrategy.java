package com.gitee.linzl.strategy.pattern;

//没有任何 优惠活动
public class NormalStrategy extends Strategy {

	@Override
	public double caculatePreferential(double money) {
		return money;
	}

}
