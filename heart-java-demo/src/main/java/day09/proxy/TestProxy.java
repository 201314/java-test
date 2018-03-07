package day09.proxy;

public class TestProxy {
	/**
	 * 静态代理测试
	 */
	public static void staticProxyTest() {
		Subject subject = new StaticSubjectProxy(new RealSubject());
		subject.request();
	}

	/**
	 * jdk动态代理测试，只能针对实现了接口的类进行代理
	 */
	public static void dynamicProxyTest() {
		// 具体主题角色，也就是被代理类
		Subject subject = new RealSubject();
		// 代理实例的handler
		DynamicSubjectProxy handler = new DynamicSubjectProxy();
		Subject proxy = (Subject) handler.getInstance(subject);
		// 执行具体主题角色方法
		proxy.request();
	}

	/**
	 * cglib动态代理测试，针对类进行动态代理，不能针对final 类
	 */
	public static void cglibProxyTest() {
		CglibSubjectProxy proxy = new CglibSubjectProxy();
		Subject subject = (Subject) proxy.getInstance(new RealSubject());
		subject.request();
	}

	public static void main(String[] args) {
		// TestProxy.staticProxyTest();
		// TestProxy.dynamicProxyTest();
		TestProxy.cglibProxyTest();
	}

}
