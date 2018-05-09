package com.gitee.linzl.factory.method.pattern;

public class SubFactory implements IFactory {
	@Override
	public Operation createOperation() {
		return new SubOperation();
	}
}
