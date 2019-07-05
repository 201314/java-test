package com.gitee.linzl.proxy.dynamic.pattern;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

import com.gitee.linzl.proxy.statics.pattern.RealSubject;
import com.gitee.linzl.proxy.statics.pattern.Subject;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * JAVA:代理类主要负责为委托类预处理消息、过滤消息、把消息转发给委托类，以及事后处理消息等,
 * 动态代理的好处：同属一个接口的具体主题角色，只需要一个代理类即可。
 * 
 * JDK的动态代理依靠接口实现，如果有些类并没有实现接口，则不能使用JDK代理，这就要使用cglib动态代理了
 * 
 * Cglib: 使用cglib动态代理 ,是针对类来实现代理的，原理是对指定的目标类生成一个子类，并覆盖其中方法实现增强，但因为采用的是继承，
 * 所以不能对final修饰的类进行代理。
 * 
 * @author linzl
 */
public class DynamicProxy {
	public Object process(Object obj, Advise adv) {
		if (obj.getClass().isInterface() || Proxy.class.isAssignableFrom(obj.getClass())) {
			return javaProxy(obj, adv);
		}
		return cglibProxy(obj, adv);
	}

	private Object javaProxy(Object obj, Advise adv) {
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),
				new InvocationHandler() {

					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						adv.before(method, args, obj);

						Object val = null;
						Exception exception = null;
						try {
							// 直接调用被代理类的方法
							val = method.invoke(obj, args);
						} catch (Exception e) {
							exception = e;
						} finally {
							adv.after(method, args, obj);
						}

						adv.afterReturnValue(val, method, args, obj);

						if (Objects.nonNull(exception)) {
							adv.afterThrowException(method, args, obj, exception);
						}
						return val;
					}
				});
	}

	private Object cglibProxy(Object obj, Advise adv) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(obj.getClass());
		// 回调方法
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				adv.before(method, args, obj);

				Object val = null;
				Exception exception = null;
				try {
					// 直接调用被代理类的方法
					val = proxy.invokeSuper(obj, args);
				} catch (Exception e) {
					exception = e;
				} finally {
					adv.after(method, args, obj);
				}

				adv.afterReturnValue(val, method, args, obj);

				if (Objects.nonNull(exception)) {
					adv.afterThrowException(method, args, obj, exception);
				}
				return val;
			}
		});
		return enhancer.create();
	}

	public static void main(String[] args) {
		// 保存生成的代理类 ,直接搜索名字 $Proxy开头的class
		System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

		DynamicProxy test = new DynamicProxy();
		Subject proxy = (Subject) test.process(new RealSubject(), new TestAdvise());
		proxy.request();
	}
}
