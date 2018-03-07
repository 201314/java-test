package day03.factory;

public class Test {
	public Test(){
		
	}
	public static void main(String[] args) {
		Person p=PersonFactory.createInstance();
		p.test();//因为test()是父类中的方法，而此方法被子类重写了，所以调用的是被重写的子类里的test()方法
		
		Child child=PersonFactory.createInstance();
		child.test("我是子类,");
		child.test();
	}
}
