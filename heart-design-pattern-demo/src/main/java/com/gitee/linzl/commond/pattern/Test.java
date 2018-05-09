package com.gitee.linzl.commond.pattern;

public class Test {
	public static void main(String[] args) {
		Waiter waiter = new Waiter(new BuyFish());
		waiter.submitCommand();

		waiter = new Waiter(new BuyMeal());
		waiter.submitCommand();
	}
}
