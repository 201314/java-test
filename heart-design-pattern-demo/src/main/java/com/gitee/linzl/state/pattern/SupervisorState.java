package com.gitee.linzl.state.pattern;

/**
 * 主管审批
 */
public class SupervisorState implements State {

    @Override
    public void handle(StateContext work) {
        System.out.println("主管审批通过，下一个经理审批");
        work.setState(new ManagerState());
        work.doProcess();
    }
}
