package com.gitee.linzl.concurrent.locks;

import java.util.concurrent.locks.LockSupport;


public class LockSupportDemo {

    public static void main(String[] args) {
        // 获取当前线程
        final Thread currentThread = Thread.currentThread();

        new Thread(() -> {
            try {
                System.out.println("子线程开始！");
                // 睡眠5秒，等待主线程调用park
                Thread.sleep(5000);
                System.out.println("子线程进行unpark操作！");
                // 可以提前先唤醒给定的currentThread线程
                LockSupport.unpark(currentThread);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        System.out.println("开始阻塞！");
        // 由于在park之前进行了一次unpark，所以会抵掉本次的park操作。因而不会阻塞在此处
        LockSupport.park(currentThread);
        System.out.println("结束阻塞！");
    }
}