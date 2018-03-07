package com.linzl.builder.pattern;

public class PersonThinBuilder extends PersonBuilder {

	public PersonThinBuilder(Object obj) {
		super(obj);
	}

	@Override
	public void BodyBuild() {
		System.out.println("创建瘦瘦的身体");
	}

	@Override
	public void FootBuild() {
		System.out.println("创建瘦瘦的脚");
	}

	@Override
	public void HandBuild() {
		System.out.println("创建瘦瘦的手");
	}

	@Override
	public void HeadBuild() {
		System.out.println("创建瘦瘦的头部");
	}

}
