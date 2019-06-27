package com.gitee.linzl.proxy.statics.pattern;

/**
 * 具体主题角色
 * 
 * @author linzl
 * 
 */
public class RealSubject implements Subject {
	@Override
	public void request() {
		System.out.println("RealSubject具体处理业务");
	}
}
