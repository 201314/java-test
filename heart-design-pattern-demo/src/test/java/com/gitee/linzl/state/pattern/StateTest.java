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
		WorkContext work = new WorkContext();

		work.setHour(9);
		work.doProcess();

		work.setHour(13);
		work.doProcess();

		work.setHour(16);
		work.doProcess();

		work.setHour(20);
		work.doProcess();
	}
}
