package day07.twoMethods;

public class Odd extends Thread {
	public void run() {
		for (int i = 1; i < 100; i += 2) {
			System.out.println("我启动了1");
			try {
				sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
