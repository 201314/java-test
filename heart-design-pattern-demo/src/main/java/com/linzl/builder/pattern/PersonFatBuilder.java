package com.linzl.builder.pattern;

public class PersonFatBuilder extends PersonBuilder {

	public PersonFatBuilder(Object obj) {
		super(obj);
	}

	@Override
	public void BodyBuild() {
		System.out.println("创建胖胖的身体");
	}

	@Override
	public void FootBuild() {
		System.out.println("创建胖胖的脚");
	}

	@Override
	public void HandBuild() {
		System.out.println("创建胖胖的手");
	}

	@Override
	public void HeadBuild() {
		System.out.println("创建胖胖的头部");
	}

}
