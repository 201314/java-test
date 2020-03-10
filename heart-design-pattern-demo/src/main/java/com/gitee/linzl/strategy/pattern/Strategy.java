package com.gitee.linzl.strategy.pattern;

/**
 * @author GDCC i心灵鸡汤you email:2225010489@qq.com 2013-4-8 下午07:33:25
 */

public interface Strategy {

	public double caculatePreferential(double money);
	
	public Integer type();
}
