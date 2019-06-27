package com.gitee.linzl.decorator.pattern;

public class Decoration implements Person {
	@Override
	public void dressUp() {
		System.out.println("准备开始打扮");
	}
}
