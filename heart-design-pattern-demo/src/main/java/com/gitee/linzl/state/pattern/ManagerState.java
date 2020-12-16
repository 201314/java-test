package com.gitee.linzl.state.pattern;

/**
 * 经理审批
 */
public class ManagerState implements State {

    @Override
    public void handle(StateContext work) {
        System.out.println("经理审批通过，下一个总监审批");
        work.setState(new DirectorState());
        work.doProcess();
    }
}
