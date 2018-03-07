package com.linzl.builder.pattern;

public class Test {
	public static void main(String[] args) {
		// 创建 一个胖子
		System.out.println("----创建一个胖子----");
		PersonBuilder fat = new PersonFatBuilder(null);
		PersonDirector directorFat = new PersonDirector(fat);
		directorFat.createPerson();

		// 创建 一个瘦猴
		System.out.println("----创建一个瘦猴----");
		PersonBuilder thin = new PersonThinBuilder(null);
		PersonDirector directorThin = new PersonDirector(thin);
		directorThin.createPerson();
	}
}
