package com.gitee.linzl.composite.pattern;

/**
 * 组合中的对象声明，在适当情况下，实现所有类共有 接口的默认行为。声明一个抽象类用于访问和管理Component的子部件
 * 
 * @author GDCC i心灵鸡汤you email:2225010489@qq.com 2013-4-23 下午08:31:41
 */
public abstract class Component {
	private String name;

	public Component(String name) {
		this.name = name;
	}

	public abstract void add(Component c);

	public abstract void remove(Component c);

	public abstract void display(int depth);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
