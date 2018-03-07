package com.linzl.state.pattern;

public class NoonState implements WorkState {

	@Override
	public void writeProgram(Work work) {
		if (work.getHour() < 14) {
			System.out.println("此时大家正在午睡，请勿打扰……");
		} else {
			work.setCurrent(new AfternoonState());
			work.writeProgram();
		}
	}

}
