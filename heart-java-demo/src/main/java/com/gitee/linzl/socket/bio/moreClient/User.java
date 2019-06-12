package com.gitee.linzl.socket.bio.moreClient;

import java.io.Serializable;

//Socket传输必须要实现序列化
public class User implements Serializable {
	private String name;
	private Integer age;

	public User(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public Integer getAge() {
		return age;
	}

}
