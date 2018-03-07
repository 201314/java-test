package day07.thread.synchronous;

public class TestThread {
	public static void main(String[] args) {
		Thread tr01 = new Thread01();
		Thread tr02 = new Thread02();

		tr01.start();
		tr02.start();
	}

}
