package com.gitee.linzl.builder.pattern;

public abstract class PersonBuilder {
	Object obj = null;

	public PersonBuilder(Object obj) {
		this.obj = obj;
	}

	public abstract void HeadBuild();

	public abstract void BodyBuild();

	public abstract void HandBuild();

	public abstract void FootBuild();

}
