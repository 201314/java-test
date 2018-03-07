package day09.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 使用cglib动态代理 ,是针对类来实现代理的，原理是对指定的目标类生成一个子类，并覆盖其中方法实现增强，但因为采用的是继承，
 * 所以不能对final修饰的类进行代理。
 * 
 * @see 依赖于cblig.jar,asm.jar
 * @author linzl
 * 
 */
public class CglibSubjectProxy implements MethodInterceptor {
	/**
	 * 创建代理对象
	 * 
	 * @param target
	 * @return
	 */
	public Object getInstance(Object target) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		// 回调方法
		enhancer.setCallback(this);
		// 创建代理对象
		return enhancer.create();
	}

	/**
	 * 回调方法
	 */
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("CglibSubjectProxy事物开始");
		proxy.invokeSuper(obj, args);
		System.out.println("CglibSubjectProxy事物结束");
		return obj;
	}

}