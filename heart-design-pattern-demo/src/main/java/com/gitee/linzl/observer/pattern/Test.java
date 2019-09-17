package com.gitee.linzl.observer.pattern;

/**
 * 在JAVA中可能通过继续Observable来实现
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2019年7月6日
 */
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
