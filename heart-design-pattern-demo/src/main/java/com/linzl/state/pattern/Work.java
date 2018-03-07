package com.linzl.state.pattern;
//工作类
public class Work {
	private double hour;
	private WorkState current;
	
	//初始化工作状态 为早上 精神抖擞
	public Work(){
		this.current=new MorningState();
	}
	
	public void writeProgram(){
		this.current.writeProgram(this);
	}
	
	public double getHour() {
		return hour;
	}
	public WorkState getCurrent() {
		return current;
	}
	public void setHour(double hour) {
		this.hour = hour;
	}
	public void setCurrent(WorkState current) {
		this.current = current;
	}
	
}
