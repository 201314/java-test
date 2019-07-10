package com.gitee.linzl.concurrent.locks;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo2 {
	public static void main(String[] args) {
		// 获取当前线程
		final Thread currentThread = Thread.currentThread();

		// 在park之前先进行一次unpark
		LockSupport.unpark(currentThread);

		System.out.println("开始阻塞！");
		// 由于在park之前进行了一次unpark，所以会低调本次的park操作。因而不会阻塞在此处
		LockSupport.park(currentThread);
		System.out.println("结束阻塞！");
	}
}