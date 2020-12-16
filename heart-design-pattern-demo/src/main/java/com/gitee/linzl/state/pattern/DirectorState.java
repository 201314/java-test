package com.gitee.linzl.state.pattern;

/**
 * 总监审批
 */
public class DirectorState implements State {

    @Override
    public void handle(StateContext work) {
        System.out.println("总监审批通过，我是最后一个审批者");
        //审核通过之后的逻辑
        System.out.println("财务打款500元");
    }
}
