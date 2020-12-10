package com.gitee.linzl.state.pattern;

public class NoonState implements WorkState {

    @Override
    public void writeProgram(WorkContext work) {
        if (work.getHour() < 14) {
            System.out.println("此时大家正在午睡，请勿打扰……");
            return;
        }
        work.setCurrent(new AfternoonState());
        work.doProcess();
    }
}
