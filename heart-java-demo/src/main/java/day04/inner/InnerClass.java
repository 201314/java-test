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

	// 普通内部类
	public class InnerClassChild {
		public void print() {
			System.out.println("wo shi " + InnerClass.this.println());
			System.out.println(InnerClass.this.name);
		}
	}

	// 也可以在此创建InnerClassChild的实例
	public InnerClassChild getInstance() {
		return new InnerClassChild();
	}

	public static void main(String[] args) {
		InnerClass i = new InnerClass();
		System.out.println(i.println());
		InnerClass.InnerClassChild ii = i.new InnerClassChild();
		ii.print();
	}

}
