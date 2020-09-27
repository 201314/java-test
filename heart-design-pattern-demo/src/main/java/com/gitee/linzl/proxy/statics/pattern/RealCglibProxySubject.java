package com.gitee.linzl.proxy.statics.pattern;

/**
 * 具体主题角色
 * 
 * @author linzl
 * 
 */
public class RealCglibProxySubject {
	public String request() {
		System.out.println("RealCglibProxySubject具体处理业务");
		return "我是RealCglibProxySubject返回值";
	}
}
