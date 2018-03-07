package day07.thread.multithread;

public class Odd extends Thread{
	public void run(){
		for (int i = 1; i < 100; i+=2) {
			System.out.println(i);
			try {
				sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
