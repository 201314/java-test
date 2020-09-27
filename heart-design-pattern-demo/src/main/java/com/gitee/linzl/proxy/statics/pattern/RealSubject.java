package com.gitee.linzl.proxy.statics.pattern;

/**
 * 具体主题角色
 * 
 * @author linzl
 * 
 */
public class RealSubject implements Subject {
	@Override
	public String find() {
		System.out.println("RealSubject find具体处理业务");
		return "我是RealSubject find返回值";
	}

	@Override
	public String insert() {
		System.out.println("RealSubject insert具体处理业务");
		return "我是RealSubject insert返回值";
	}
}
