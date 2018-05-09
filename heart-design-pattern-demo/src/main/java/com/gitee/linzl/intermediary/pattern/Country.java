package com.gitee.linzl.intermediary.pattern;

//联合国成员国
public abstract class Country {
	UnitedNations un;

	// 告诉成员国，中介是谁？-->联合国安理会
	public Country(UnitedNations un) {
		this.un = un;
	}

	// 发送信息
	public abstract void sendMessage(String message);

	// 接收信息
	public abstract void notifyMessage(String message);
}
