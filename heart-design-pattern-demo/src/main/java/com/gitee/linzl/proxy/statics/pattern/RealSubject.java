package com.gitee.linzl.proxy.statics.pattern;

/**
 * 具体主题角色
 * 
 * @author linzl
 * 
 */
public class RealSubject implements Subject {
	@Override
	public String request() {
		System.out.println("javaProxy具体处理业务");
		return "我是javaProxy返回值";
	}
}
