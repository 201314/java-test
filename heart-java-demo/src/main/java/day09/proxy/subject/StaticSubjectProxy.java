package day09.proxy.subject;

/**
 * 代理类主要负责为委托类预处理消息、过滤消息、把消息转发给委托类，以及事后处理消息等
 * 优点：业务类只需要关注业务逻辑本身，保证了业务类的重用性。这是代理的共有优点。 缺点：
 * 1）代理对象的一个接口只服务于一种类型的对象，如果要代理的方法很多，势必要为每一种方法都进行代理，静态代理在程序规模稍大时就无法胜任了。
 * 2）如果接口增加一个方法，除了所有实现类需要实现这个方法外，所有代理类也需要实现此方法。增加了代码维护的复杂度。
 * 
 * @author linzl
 * 
 */
public class StaticSubjectProxy implements Subject {
	// 具体需要代理哪个实现类
	private Subject subject;

	// 默认代理者
	public StaticSubjectProxy() {
		this.subject = new RealSubject();
	}

	public StaticSubjectProxy(Subject subject) {
		this.subject = subject;
	}

	@Override
	public void request() {
		before();
		subject.request();
		after();
	}

	private void before() {
		System.out.println("代理前预处理");
	}

	private void after() {
		System.out.println("代理完善后处理");
	}
}
