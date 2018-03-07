package com.linzl.composite.pattern.demo2;

public class HRDepartment implements Company {
	private String name;

	public HRDepartment(String name) {
		this.name = name;
	}

	@Override
	public void add(Company com) {
		System.out.println("Can't add to leaf");
	}

	@Override
	public void remove(Company com) {
		System.out.println("Can't remove from leaf");
	}

	@Override
	public void display(int depth) {
		String str = "";
		while (depth > 0) {
			str += "-";
			depth--;
		}
		System.out.println(str + name);
	}

	@Override
	public void fulfilDuty() {
		System.out.println(name + ":负责人力");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
