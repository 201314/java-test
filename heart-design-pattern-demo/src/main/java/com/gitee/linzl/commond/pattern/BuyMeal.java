package com.gitee.linzl.commond.pattern;

public class BuyMeal extends Command {

	@Override
	public void executeCommand() {
		cook.cookMeal();
	}

}
