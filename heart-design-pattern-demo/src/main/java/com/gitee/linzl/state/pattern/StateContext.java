package com.gitee.linzl.state.pattern;

/**
 * 状态上下文,存储状态过程信息
 */
public class StateContext {
    private State state;

    public StateContext() {
    }

    public void doProcess() {
        this.state.handle(this);
    }

    public void setState(State state) {
        this.state = state;
    }
}
