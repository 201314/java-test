package com.gitee.linzl.observer.pattern;

import java.util.ArrayList;
import java.util.List;

//前台 通知主题，一有消息通知 观察者Observer
public class ReceptionSubject implements Subject {
	// 主题通知者的 状态
	private String state;
	// 通知 哪些 观察者
	List<Observer> oberverList = new ArrayList<Observer>();

	// 增加观察者
	public void addOberverList(Observer obs) {
		oberverList.add(obs);
	}

	// 删除观察者
	public void removeOberverList(Observer obs) {
		oberverList.remove(obs);
	}

	// 通知所有 观察者 更新 自己
	public void message() {
		for (Observer obs : oberverList)
			obs.update();
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String getMessageState() {
		return state;
	}

}
