package com.gitee.linzl.concurrent.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {
	private String name;
	private int age;

	public AtomicReferenceDemo(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public static void main(String[] args) {
		AtomicReference<AtomicReferenceDemo> atomic = new AtomicReference<>(new AtomicReferenceDemo("xiaodao", 23));
		System.out.println(atomic.get());
		boolean result = atomic.compareAndSet(atomic.get(), new AtomicReferenceDemo("bbb", 90));
		System.out.println(result);
	}
}
