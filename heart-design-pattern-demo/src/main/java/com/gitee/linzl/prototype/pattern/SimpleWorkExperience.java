package com.gitee.linzl.prototype.pattern;

public class SimpleWorkExperience {
	private String time;
	private String company;

	public SimpleWorkExperience(String time, String company) {
		this.time = time;
		this.company = company;
	}

	public String getTime() {
		return time;
	}

	public String getCompany() {
		return company;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setCompany(String company) {
		this.company = company;
	}
}
