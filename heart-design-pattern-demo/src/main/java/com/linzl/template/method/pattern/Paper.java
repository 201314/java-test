package com.linzl.template.method.pattern;

/**
 * 试卷类 供给学生答题，试卷题目都是不变的，应当放在父类，惟一变化的是答案，所以答案由子类实现
 */
public abstract class Paper {
	public void questionA() {
		System.out.println("第一题：1+1=" + getAnswerOfA());
	}

	public void questionB() {
		System.out.println("第二题：10/1=" + getAnswerOfB());
	}

	public void questionC() {
		System.out.println("第三题：10*20=" + getAnswerOfC());
	}

	public abstract String getAnswerOfA();

	public abstract String getAnswerOfB();

	public abstract String getAnswerOfC();
}
