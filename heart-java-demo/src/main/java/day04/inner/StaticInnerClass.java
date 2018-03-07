package day04.inner;

/**
 * 静态内部类
 * 
 * @description
 * 
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年1月10日
 */
public class StaticInnerClass {
	private static String name = "LZL";

	public static String println() {
		return "who are you";
	}

	/**
	 * 静态内部类@description
	 * 
	 * @author linzl
	 * @email 2225010489@qq.com
	 * @date 2018年1月10日
	 */
	public static class InnerClassChild {
		public void print() {
			System.out.println("wo shi " + StaticInnerClass.println());
			System.out.println(StaticInnerClass.name);
		}
	}

	/**
	 * 也可以在此创建InnerClassChild的实例
	 * 
	 * @return
	 */
	public InnerClassChild getInstance() {
		return new InnerClassChild();
	}

	public static void main(String[] args) {
		StaticInnerClass.InnerClassChild ii = new StaticInnerClass.InnerClassChild();
		ii.print();
	}

}
