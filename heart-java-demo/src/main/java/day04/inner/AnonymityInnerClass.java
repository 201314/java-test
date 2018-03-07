package day04.inner;

/**
 * 匿名内部类
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年1月10日
 */
public class AnonymityInnerClass {
	public void anonymity(Person per) {
		per.test();
		System.out.println("向匿名内部类打个招呼吧");
	}

	public static void main(String[] args) {
		AnonymityInnerClass aic = new AnonymityInnerClass();
		aic.anonymity(new Person() {// 匿名内部类，三大框架使用
			public void test() {
				System.out.println("我是匿名内部类");
			}
		});
	}
}
