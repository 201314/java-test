package com.linzl.facade.pattern;

public class Test {
	public static void main(String[] args) {
		Fund jijin=new Fund();
		
		//基金经理人的投资方案
		System.out.println("-----投资方案1:-----");
		jijin.firstMethod();
		
		System.out.println("-----投资方案2:-----");
		jijin.secondMethod();
	
		System.out.println("-----投资方案3:-----");
		jijin.thirdMethod();
	}
}
