package com.linzl.prototype.pattern;

public class DeepWorkExperience implements Cloneable{
	private String time;
	private String company;
	
	public Object clone(){
		DeepWorkExperience work=null;
		try {
			work = (DeepWorkExperience)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return work;
	}
	
	public DeepWorkExperience(String time,String company){
		this.time=time;
		this.company=company;
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
