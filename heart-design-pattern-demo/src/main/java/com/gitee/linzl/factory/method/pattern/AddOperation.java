package com.gitee.linzl.factory.method.pattern;

//加法操作类
public class AddOperation extends Operation {

	// 重写父类方法，实现多态
	@Override
	public double caculateResult() {
		double result = getFirstNum() + getSecondNum();
		return result;
	}
}
