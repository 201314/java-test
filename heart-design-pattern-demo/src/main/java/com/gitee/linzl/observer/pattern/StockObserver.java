package com.gitee.linzl.observer.pattern;

//在看股票的同事
public class StockObserver extends Observer {

	public StockObserver(String name, Subject sub) {
		super(name, sub);
	}

	@Override
	public void update() {
		System.out.println(getName() + ":" + getSub().getMessageState() + ",赶紧关掉股票软件");
	}

}
