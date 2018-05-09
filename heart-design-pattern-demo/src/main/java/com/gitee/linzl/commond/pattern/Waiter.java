package com.gitee.linzl.commond.pattern;

//服务员
public class Waiter {
	Command command;

	public Waiter(Command command) {
		this.command = command;
	}

	// 将 顾客 写的菜单提交给厨师
	public void submitCommand() {
		command.executeCommand();
	}

	// 取消菜单
	public void cancelCommand() {
	}
}
