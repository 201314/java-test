package com.gitee.linzl.factory.simple.pattern;

//减法操作类
public class SubOperation extends Operation {

	// 重写父类方法，实现多态
	@Override
	public double caculateResult() {
		double result = getFirstNum() - getSecondNum();
		return result;
	}
}
