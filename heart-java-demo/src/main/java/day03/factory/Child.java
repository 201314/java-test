package day03.factory;

public class Child extends Person {
	//重写父类方法 
	public void test() {
		System.out.println("子类方法");
	}
	
	public void test(String name){
		System.out.println(name+"子类独有的方法");
	}

}
