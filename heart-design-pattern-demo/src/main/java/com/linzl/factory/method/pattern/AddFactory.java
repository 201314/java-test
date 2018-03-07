package com.linzl.factory.method.pattern;

public class AddFactory implements IFactory {

	@Override
	public Operation createOperation() {
		return new AddOperation();
	}

}
