package com.gitee.linzl.proxy.dynamic.pattern;

import com.gitee.linzl.proxy.statics.pattern.RealSubject;
import com.gitee.linzl.proxy.statics.pattern.Subject;

public class Test {
	public static void main(String[] args) {
		// 保存生成的代理类
		System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
		// 具体主题角色，也就是被代理类
		Subject subject = new RealSubject();
		// 代理实例的handler
		JDKDynamicProxy handler = new JDKDynamicProxy();
		Subject proxy = (Subject) handler.getInstance(subject);
		// 执行具体主题角色方法
		proxy.request();
	}
}
