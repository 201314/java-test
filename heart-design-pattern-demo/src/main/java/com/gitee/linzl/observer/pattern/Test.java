package com.gitee.linzl.observer.pattern;

public class Test {
	public static void main(String[] args) {
		ReceptionSubject recept = new ReceptionSubject();
		recept.setState("老板回来了");

		NBAObserver nba = new NBAObserver("张三", recept);
		nba.update();

		StockObserver stock = new StockObserver("李四", recept);
		stock.update();

	}
}
