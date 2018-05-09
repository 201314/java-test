package com.gitee.linzl.intermediary.pattern;

public class American extends Country {

	public American(UnitedNations un) {
		super(un);
	}

	@Override
	public void notifyMessage(String message) {
		System.out.println("美国收到的信息：" + message);
	}

	@Override
	public void sendMessage(String message) {
		un.declare(message, this);
	}

}