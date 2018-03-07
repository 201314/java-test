package com.linzl.commond.pattern;

public class BuyMeal extends Command {

	@Override
	public void executeCommand() {
		cook.cookMeal();
	}

}
