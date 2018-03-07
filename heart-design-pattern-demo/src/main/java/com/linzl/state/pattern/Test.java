package com.linzl.state.pattern;

public class Test {
	public static void main(String[] args) {
		Work work=new Work();

		work.setHour(9);
		work.writeProgram();
		
		work.setHour(13);
		work.writeProgram();
		
		work.setHour(16);
		work.writeProgram();
		
		work.setHour(20);
		work.writeProgram();
	}
}
