package day09.classloader;

public class Test {
	private Test instance;

	public void run(Object obj) {
		System.out.println("输出Test");
		this.instance = (Test) obj;
	}

	public void say() {
		System.out.println("hello world");
	}

}
