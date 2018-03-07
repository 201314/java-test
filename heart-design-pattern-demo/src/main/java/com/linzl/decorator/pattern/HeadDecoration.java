package com.linzl.decorator.pattern;

public class HeadDecoration extends Decoration {

	@Override
	public void dressUp() {
		monkey.dressUp();
		System.out.println("戴头盔");
	}

}
