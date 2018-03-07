package com.linzl.template.method.pattern;

public class Test {
	public static void main(String[] args) {
		System.out.println("学生A答题：");
		Paper studentA = new StudentA();
		studentA.questionA();
		studentA.questionB();
		studentA.questionC();

		System.out.println("------------");
		System.out.println("学生B答题：");
		Paper studentB = new StudentB();
		studentB.questionA();
		studentB.questionB();
		studentB.questionC();

	}
}
