package com.gitee.linzl.state.pattern;

/**
 * 状态模式
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年5月9日
 */
public class Test {
	public static void main(String[] args) {
		Work work = new Work();

		work.setHour(9);
		work.writeProgram();

		work.setHour(13);
		work.writeProgram();

		work.setHour(16);
		work.writeProgram();

		work.setHour(20);
		work.writeProgram();
	}
}
