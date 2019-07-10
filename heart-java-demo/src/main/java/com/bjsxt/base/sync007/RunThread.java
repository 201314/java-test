package com.bjsxt.base.sync007;

public class RunThread extends Thread {
	private boolean isRunning = true;

	private void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public void run() {
		System.out.println("进入run方法..");
		while (isRunning == true) {
			// ..
		}
		System.out.println("线程停止");
	}

	public static void main(String[] args) throws InterruptedException {
		RunThread rt = new RunThread();
		rt.start();
		Thread.sleep(1000);
		rt.setRunning(false);// 这个是修改了main线程的isRunning,如果isRunning加了volatile修饰，则rt线程会强制从main线程从读取isRunning
		System.out.println("isRunning的值已经被设置了false");
	}
}
