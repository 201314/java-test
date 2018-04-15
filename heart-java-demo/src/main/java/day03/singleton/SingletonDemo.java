package day03.singleton;

public class SingletonDemo {
	private SingletonDemo() {

	}

	public static SingletonDemo getInstance() {
		return new SingletonDemo();
	}

	public void test() {
		System.out.println("单例模式");
	}

	public static void main(String args[]) {
		SingletonDemo single = SingletonDemo.getInstance();
		single.test();
	}
}
