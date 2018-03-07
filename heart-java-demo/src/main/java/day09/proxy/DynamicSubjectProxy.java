package day09.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理类主要负责为委托类预处理消息、过滤消息、把消息转发给委托类，以及事后处理消息等,动态代理的好处：同属一个接口的具体主题角色，只需要一个代理类即可。
 * JDK的动态代理依靠接口实现，如果有些类并没有实现接口，则不能使用JDK代理，这就要使用cglib动态代理了
 * 
 * @author linzl
 * 
 */
public class DynamicSubjectProxy implements InvocationHandler {
	// 被代理对象
	private Subject subject;

	/**
	 * 创建代理对象
	 * 
	 * @param target
	 * @return
	 */
	public Object getInstance(Subject subject) {
		this.subject = subject;
		// 代理实例的handler
		InvocationHandler handler = new DynamicSubjectProxy();
		// 动态代理
		return Proxy.newProxyInstance(subject.getClass().getClassLoader(), subject.getClass().getInterfaces(), handler);
	}

	/**
	 * 委托处理方法, 回调方法
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// 预处理
		System.out.println("DynamicSubjectProxy预处理");
		// 直接调用被代理类的方法
		Object obj = method.invoke(subject, args);
		// 善后处理
		System.out.println("DynamicSubjectProxy善后处理");
		return obj;
	}

}
