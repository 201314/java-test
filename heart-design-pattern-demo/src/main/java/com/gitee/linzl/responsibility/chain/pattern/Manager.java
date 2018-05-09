package com.gitee.linzl.responsibility.chain.pattern;

public abstract class Manager {
	// 请假
	public final int LEAVE = 1;
	// 加薪
	public final int SALARY = 2;

	// 当前管理者的上级
	Manager superManager;

	// 处理申请
	public abstract boolean handleRequest(Apply apply);

	public Manager getSuperManager() {
		return superManager;
	}

	public void setSuperManager(Manager superManager) {
		this.superManager = superManager;
	}

}
