package day07.thread.synchronous;

public class CommunicationThread extends Thread {
	private Object obj = new Object();

	public void run() {
		synchronized (obj) {
			for (int i = 0; i < 5; i++) {
				System.out.println(i);
				try {
					obj.notifyAll();
					obj.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
