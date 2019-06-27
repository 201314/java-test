package com.gitee.linzl.decorator.pattern;

public class HeadDecoration implements Person {
	private Person person;

	public HeadDecoration(Person person) {
		this.person = person;
	}

	@Override
	public void dressUp() {
		person.dressUp();
		System.out.println("戴头盔");
	}

}
