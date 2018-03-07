package com.linzl.adapter.pattern;

/**
 * NBAPlayer 和 Adaptee 两个类所做的事情相同或者相似， 但是你要求使用的是attack,defend这样的方法，而Adaptee
 * 类似方法为 进攻、防守，则考虑使用Adapter
 * 
 * @author GDCC i心灵鸡汤you email:2225010489@qq.com 2013-4-20 下午07:46:44
 */
public class Test {
	public static void main(String[] args) {
		NBAPlayer adapter = new Adapter();
		adapter.attack();
		adapter.defend();
	}
}
