package com.linzl.decorator.pattern;

public class DressDecoration extends Decoration {

	@Override
	public void dressUp() {
		monkey.dressUp();
		System.out.println("穿悟空服");
	}
}
