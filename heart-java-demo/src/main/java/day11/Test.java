package day11;

/**
 * 子类调用父类的静态变量，不会导致子类初始化
 * 
 * 对于静态字段，只有直接定义这个字段的类才会被初始化
 **/
class SuperClass {
	static {
		System.out.println("superclass init");
	}
	public static int value = 123;
}

class SubClass extends SuperClass {
	static {
		System.out.println("subclass init");
	}
}

public class Test {
	public static void main(String[] args) {
		System.out.println(SubClass.value);
	}
}