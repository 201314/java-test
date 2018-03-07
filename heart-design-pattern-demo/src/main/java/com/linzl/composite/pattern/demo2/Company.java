package com.linzl.composite.pattern.demo2;

public interface Company {
	public void add(Company com);

	public void remove(Company com);

	public void display(int depth);

	// 履行职责
	public void fulfilDuty();
}
