package com.gitee.linzl.classloader;

import java.util.Set;
import java.util.TreeSet;

public class Test {

	public static void main(String[] args) {
		Set<String> set = new TreeSet<>();
		set.add(null);
		set.add("hello");
		set.add(null);
		System.out.println(set);
	}

}
