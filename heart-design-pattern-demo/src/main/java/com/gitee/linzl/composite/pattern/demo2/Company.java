package com.gitee.linzl.composite.pattern.demo2;

public interface Company {
	void add(Company com);

	void remove(Company com);

	void display(int depth);

	// 履行职责
	void fulfilDuty();
}
