package com.gitee.linzl.state.pattern;

public class MorningState implements WorkState {

	@Override
	public void writeProgram(Work work) {
		if (work.getHour() < 12) {
			System.out.println("精神抖擞地在敲代码ing");
		} else {
			work.setCurrent(new NoonState());
			work.writeProgram();
		}
	}

}
