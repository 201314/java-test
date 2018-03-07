package com.linzl.commond.pattern;

public class BuyFish extends Command {

	@Override
	public void executeCommand() {
		cook.cookFish();
	}

}
