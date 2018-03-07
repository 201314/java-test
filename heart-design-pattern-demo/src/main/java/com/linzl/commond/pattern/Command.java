package com.linzl.commond.pattern;

public abstract class Command {
	Cook cook;

	public Command() {
		cook = new Cook();
	}

	public void executeCommand() {
	}

	public void cancelCommand() {
	}

}
