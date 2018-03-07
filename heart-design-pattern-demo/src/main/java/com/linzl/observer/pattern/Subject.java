package com.linzl.observer.pattern;

//主题通知者
public interface Subject {

	// 增加观察者
	public void addOberverList(Observer obs);

	// 删除观察者
	public void removeOberverList(Observer obs);

	// 通知所有 观察者 更新 自己
	public void message();

	// 返回 通知者状态
	public String getMessageState();

}
