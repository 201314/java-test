package com.gitee.linzl.intermediary.pattern;

public class China extends Country {

	public China(UnitedNations un) {
		super(un);
	}

	@Override
	public void notifyMessage(String message) {
		System.out.println("中国收到的信息：" + message);
	}

	@Override
	public void sendMessage(String message) {
		un.declare(message, this);
	}

}