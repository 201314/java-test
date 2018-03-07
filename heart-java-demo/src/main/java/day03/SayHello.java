package day03;

public class SayHello {
	public void sayHello(String name) {
		System.out.println(name + ",Hello");
	}

	public void sayHello() {
		System.out.println("Hello");
	}

	public static void main(String args[]) {
		SayHello say = new SayHello();
		say.sayHello();
		say.sayHello("中国");
	}
}
