package com.linzl.observer.pattern;

//在看NBA的同事
public class NBAObserver extends Observer {

	public NBAObserver(String name, Subject sub) {
		super(name, sub);
	}

	@Override
	public void update() {
		System.out.println(getName() + ":" + getSub().getMessageState() + ",赶紧关掉NBA直播");
	}

}
