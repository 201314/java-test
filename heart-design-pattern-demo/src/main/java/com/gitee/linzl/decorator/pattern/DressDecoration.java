package com.gitee.linzl.decorator.pattern;

public class DressDecoration implements Person {
	private Person person;

	public DressDecoration(Person person) {
		this.person = person;
	}

	@Override
	public void dressUp() {
		person.dressUp();
		System.out.println("穿悟空服");
	}
}
