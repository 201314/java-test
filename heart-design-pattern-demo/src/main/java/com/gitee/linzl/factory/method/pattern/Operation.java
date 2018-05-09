package com.gitee.linzl.factory.method.pattern;

//一个简单的操作类，计算器类
public abstract class Operation {
	private double firstNum;

	private double secondNum;

	private double result = 0.0;

	public double getFirstNum() {
		return firstNum;
	}

	public void setFirstNum(double firstNum) {
		this.firstNum = firstNum;
	}

	public double getSecondNum() {
		return secondNum;
	}

	public void setSecondNum(double secondNum) {
		this.secondNum = secondNum;
	}

	// 返回计算操作结果
	public double caculateResult() {
		return result;
	}
}
