package com.gitee.linzl.lambda.collection;

import java.math.BigDecimal;

public class Student {
	private String name;
	private Integer score;
	private BigDecimal money;

	public Student() {

	}

	public Student(String name, Integer score) {
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

}
