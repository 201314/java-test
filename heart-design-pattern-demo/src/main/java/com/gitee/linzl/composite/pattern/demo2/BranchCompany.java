package com.gitee.linzl.composite.pattern.demo2;

import com.gitee.linzl.composite.pattern.demo2.Company;

import java.util.ArrayList;
import java.util.List;

public class BranchCompany implements Company {
	private String name;
	private List<Company> deparmentList = new ArrayList<>();

	public BranchCompany(String name) {
		this.name = name;
	}

	@Override
	public void add(Company com) {
		deparmentList.add(com);
	}

	@Override
	public void remove(Company com) {
		deparmentList.remove(com);
	}

	@Override
	public void display(int depth) {
		String str = "";
		while (depth > 0) {
			str += "-";
			depth--;
		}
		System.out.println(str + getName());
		for (Company com : deparmentList) {
			com.display(depth + 6 - deparmentList.size());
		}
	}

	@Override
	public void fulfilDuty() {
		for (Company com : deparmentList) {
			com.fulfilDuty();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
