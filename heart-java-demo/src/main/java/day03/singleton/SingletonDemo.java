package day03.singleton;

public class SingletonDemo {
	private SingletonDemo(){
		
	}
	public static SingletonDemo createInstance(){
		return new SingletonDemo();
	}
	public void test(){
		System.out.println("这是一个单例模式，即只能实例化一次");
	}
	public static void main(String args[]){
		SingletonDemo single=SingletonDemo.createInstance();
		single.test();
	}
}
