package com.linzl.decorator.pattern;

//装饰器 是为了装饰monkey的
public abstract class Decoration extends Person {
	Person monkey;

	public void decorate(Person monkey) {
		this.monkey = monkey;
	}

	public void dressUp() {
		if (monkey != null) {
			monkey.dressUp();
		}
	}

}
