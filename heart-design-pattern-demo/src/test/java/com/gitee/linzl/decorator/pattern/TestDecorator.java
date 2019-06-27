package com.gitee.linzl.decorator.pattern;

public class TestDecorator {
	public static void main(String[] args) {
		// 装饰过程:客户指定了装饰者需要装饰的是哪一个类
		Person person = new HeadDecoration(new DressDecoration(new Decoration()));
		person.dressUp();
	}
}
