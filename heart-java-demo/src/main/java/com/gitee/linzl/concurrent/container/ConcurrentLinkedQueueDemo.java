package com.gitee.linzl.concurrent.container;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueDemo {

	public static void main(String[] args) throws InterruptedException {
		List<String> a = new ArrayList<String>();
		a.add("a");
		a.add("b");
		a.add("c");
		ConcurrentLinkedQueue<String> list = new ConcurrentLinkedQueue<String>(a);
		Thread t = new Thread(new Runnable() {
			int count = -1;

			@Override
			public void run() {
				while (count < 10) {
					list.add(String.valueOf(count++));
				}
			}
		});
		t.setDaemon(true);
		t.start();
		Thread.sleep(3);
		for (String s : list) {
			// 发现每次的hascode不一样，因为在动态添加元素，元素发生变化。
			System.out.println(list.hashCode());
			System.out.println(s);
		}
	}
}