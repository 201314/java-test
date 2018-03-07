package day07.twoMethods;

public class Test {
	public static void main(String[] args) {
		Thread odd=new Odd();
		Thread even=new Even();
		odd.start();
		even.start();
		
		Runnable runOdd=new Odd01();
		Thread thOdd=new Thread(runOdd);
		
		Runnable runEven=new Even01();
		Thread thEven=new Thread(runEven);
		
		thOdd.start();
		thEven.start();
	}
}
