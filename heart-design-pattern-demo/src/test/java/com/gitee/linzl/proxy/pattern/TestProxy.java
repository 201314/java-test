package com.gitee.linzl.proxy.pattern;

import org.junit.Test;

import com.gitee.linzl.proxy.dynamic.pattern.DynamicProxy;
import com.gitee.linzl.proxy.dynamic.pattern.TestAdvise;
import com.gitee.linzl.proxy.statics.pattern.RealCglibProxySubject;
import com.gitee.linzl.proxy.statics.pattern.RealSubject;
import com.gitee.linzl.proxy.statics.pattern.StaticSubjectProxy;
import com.gitee.linzl.proxy.statics.pattern.Subject;

public class TestProxy {
	/**
	 * 静态代理测试
	 */
	@Test
	public void staticProxyTest() {
		// 代理和真实对象的关系在编译时已经确定
		Subject subject = new StaticSubjectProxy();
		subject.find();
	}

	/**
	 * jdk动态代理测试，只能针对实现了接口的类进行代理
	 */
	@Test
	public void jdkProxyTest() {
		// 保存生成的代理类
		System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
		// 具体主题角色，也就是被代理类
		Subject subject = new RealSubject();

		DynamicProxy test = new DynamicProxy();
		Subject proxy = (Subject) test.process(subject, new TestAdvise());
		// 执行具体主题角色方法
		proxy.find();
	}

	/**
	 * cglib动态代理测试，针对类进行动态代理，不能针对final类
	 */
	@Test
	public void cglibProxyTest() {
		RealCglibProxySubject subject = new RealCglibProxySubject();

		DynamicProxy test = new DynamicProxy();
		RealCglibProxySubject proxy = (RealCglibProxySubject) test.process(subject, new TestAdvise());
		// 执行具体主题角色方法
		proxy.request();
	}
}
