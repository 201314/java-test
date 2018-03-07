package com.linzl.thread.threadLocal;

public class Test {
	public int i = 1;

	public void test() {
		synchronized (this) {
			i++;
		}
	}
}