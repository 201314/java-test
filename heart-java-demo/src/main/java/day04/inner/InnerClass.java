package day04.inner;

/**
 * 普通内部类
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年1月10日
 */
public class InnerClass {
	private String name = "LZL";

	public String println() {
		return "who are you";
	}

	/**
	 * 普通内部类@description
	 * 
	 * @author linzl
	 * @email 2225010489@qq.com
	 * @date 2018年6月18日
	 */
	private class InnerClassChild {
		public void print() {
			System.out.println("wo shi " + InnerClass.this.println());
			System.out.println(InnerClass.this.name);
		}
	}

	public InnerClassChild getInstance() {
		return new InnerClassChild();
	}

	private static String staticName = "static_name";

	public static String printlnStatic() {
		return "static who are you";
	}

	/**
	 * 静态内部类@description
	 * 
	 * @author linzl
	 * @email 2225010489@qq.com
	 * @date 2018年1月10日
	 */
	private static class StaticInnerClassChild {
		public void print() {
			System.out.println("wo shi " + InnerClass.printlnStatic());
			System.out.println(InnerClass.staticName);
		}
	}

	/**
	 * 也可以在此创建StaticInnerClassChild的实例
	 * 
	 * @return
	 */
	public StaticInnerClassChild getStaticInstance() {
		return new StaticInnerClassChild();
	}

	public interface Person {
		public void test();
	}

	public void anonymity(Person per) {
		per.test();
		System.out.println("向匿名内部类打个招呼吧");
	}

	public static void main(String[] args) {
		// 普通内部类
		InnerClass i = new InnerClass();
		InnerClass.InnerClassChild ii = i.new InnerClassChild();
		ii.print();

		// 静态内部类
		InnerClass.StaticInnerClassChild staticII = new InnerClass.StaticInnerClassChild();
		staticII.print();

		// 匿名内部类
		InnerClass aic = new InnerClass();
		aic.anonymity(() -> {// 匿名内部类，三大框架使用
			System.out.println("我是匿名内部类");
		});
	}
}
