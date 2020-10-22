package com.gitee.linzl.thread.volatiles;

import java.util.concurrent.TimeUnit;

public class RunThread implements Runnable {
    private boolean isRunning = true;

    private void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        System.out.println("进入run方法..");
        while (isRunning == true) {
            // Do any thing 停不下来..
        }
        System.out.println("线程停止");
    }

    public static void main(String[] args) throws InterruptedException {
		RunThread rt = new RunThread();
		Thread t = new Thread(rt);
		t.start();
        TimeUnit.SECONDS.sleep(1L);
        // 这个是修改了main线程的isRunning,如果isRunning加了volatile修饰，则rt线程会强制从main线程从读取isRunning
        rt.setRunning(false);
        System.out.println("isRunning的值已经被设置了false");
    }
}
