package com.gitee.linzl.state.pattern;

/**
 * 状态模式
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年5月9日
 */
public class StateTest {
	public static void main(String[] args) {
		StateContext work = new StateContext();
		work.setState(new SupervisorState());
		work.doProcess();
	}
}
