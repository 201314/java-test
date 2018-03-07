package com.linzl.strategy.pattern;

//折扣类，打**折用此类
public class DiscountStrategy extends Strategy{
	private double discount;//折扣
	
	public DiscountStrategy(double discount){
		this.discount=discount;
	}

	@Override
	public double caculatePreferential(double money) {
		
		return discount*money;//折后价
	}
	
}
