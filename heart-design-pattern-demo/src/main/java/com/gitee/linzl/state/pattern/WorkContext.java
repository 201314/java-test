package com.gitee.linzl.state.pattern;

//工作类
public class WorkContext {
    private double hour;
    private WorkState current;

    //初始化工作状态 为早上 精神抖擞
    public WorkContext() {
        this.current = new MorningState();
    }

    public void doProcess() {
        this.current.writeProgram(this);
    }

    public void setHour(double hour) {
        this.hour = hour;
    }

    public double getHour() {
        return hour;
    }

    public void setCurrent(WorkState state) {
        this.current = state;
    }

    public WorkState getCurrent() {
        return current;
    }
}
