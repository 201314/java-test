package day07.twoMethods;

public class Even extends Thread{
	public void run(){
		for (int i = 0; i < 100; i+=2) {
			System.out.println("我启动了2");
			try {
				sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
