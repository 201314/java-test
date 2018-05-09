package com.gitee.linzl.observer.pattern;

// 接收  主题 的通知
public abstract class Observer {
	private String name;
	private Subject sub;

	public Observer(String name, Subject sub) {
		this.name = name;
		this.sub = sub;
	}

	// 每个观察者 都有不同的 更新信息
	public void update() {
	}

	public String getName() {
		return name;
	}

	public Subject getSub() {
		return sub;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSub(Subject sub) {
		this.sub = sub;
	};

}
