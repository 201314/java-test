package day09.proxy;

import org.junit.Test;

import day09.proxy.dynamic.CglibProxy;
import day09.proxy.dynamic.JDKDynamicProxy;
import day09.proxy.subject.RealSubject;
import day09.proxy.subject.StaticSubjectProxy;
import day09.proxy.subject.Subject;

public class TestProxy {
	/**
	 * 静态代理测试
	 */
	@Test
	public void staticProxyTest() {
		Subject subject = new StaticSubjectProxy(new RealSubject());
		subject.request();
	}

	/**
	 * jdk动态代理测试，只能针对实现了接口的类进行代理
	 */
	@Test
	public void dynamicProxyTest() {
		// 具体主题角色，也就是被代理类
		Subject subject = new RealSubject();
		// 代理实例的handler
		JDKDynamicProxy handler = new JDKDynamicProxy();
		Subject proxy = (Subject) handler.getInstance(subject);
		// 执行具体主题角色方法
		proxy.request();
	}

	/**
	 * cglib动态代理测试，针对类进行动态代理，不能针对final 类
	 */
	@Test
	public void cglibProxyTest() {
		RealSubject realSubject = new RealSubject();
		CglibProxy proxy = new CglibProxy();
		Subject subject = (Subject) proxy.getInstance(realSubject);
		// 执行具体主题角色方法
		subject.request();
	}

}
