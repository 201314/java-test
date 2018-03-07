package com.linzl.builder.pattern;

//指挥者，来指挥 创建一个完整的人
public class PersonDirector {
	private PersonBuilder person;

	public PersonDirector(PersonBuilder person) {
		this.person = person;
	}

	public void createPerson() {
		person.HeadBuild();
		person.BodyBuild();
		person.HandBuild();
		person.FootBuild();
	}
}
