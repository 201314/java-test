package com.linzl.state.pattern;

public class AfternoonState implements WorkState {

	@Override
	public void writeProgram(Work work) {
		if (work.getHour() < 18) {
			System.out.println("下午时间工作要抓紧了，不然晚上可能要加班咯……");
		} else {
			System.out.println("下班回家咯^^");
		}
	}
}
