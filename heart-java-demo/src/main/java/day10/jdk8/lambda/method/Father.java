package day10.jdk8.lambda.method;

public class Father {
	public void greet() {
		System.out.println("Hello, i am function in father!");
	}

	public static void main(String[] args) {
		new Child().greet();
	}
}

class Child extends Father {
	public void greet() {
		Runnable runnable = super::greet;
		new Thread(runnable).start();
	}

}