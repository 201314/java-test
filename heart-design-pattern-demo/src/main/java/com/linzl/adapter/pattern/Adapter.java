package com.linzl.adapter.pattern;

/**
 * 适配器 作为翻译官，为那些在NBA的中国球员：如 姚明， 为他们将英文翻译为中文
 * 
 * @author GDCC i心灵鸡汤you email:2225010489@qq.com 2013-4-20 下午07:43:00
 */
public class Adapter implements NBAPlayer {

	private Adaptee adaptee = new Adaptee();

	@Override
	public void attack() {
		adaptee.进攻();
	}

	@Override
	public void defend() {
		adaptee.防守();
	}

}
