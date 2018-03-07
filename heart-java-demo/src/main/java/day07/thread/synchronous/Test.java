package day07.thread.synchronous;

public class Test {
	private Integer count;

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public static class SimpleTask implements Runnable {
		private Test test;
		private int mycount;

		public SimpleTask(Test test) {
			this.test = test;
		}

		public void run() {
			synchronized (this.test.count) {
				for (int i = 0; i < 5; i++) {
					mycount = this.test.count;
					mycount++;
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					this.test.setCount(mycount);
					System.out.println(Thread.currentThread() + " " + this.test.getCount());
				}
			}
		}
	}

	public static void main(String[] args) {
		Test test = new Test();
		test.setCount(0);

		Runnable ra = new SimpleTask(test);
		Thread tr = new Thread(ra);
		Thread tr1 = new Thread(ra);

		tr1.start();
		tr.start();
	}
}
