package day03.singleton;

public class SingletonDemo {
	private SingletonDemo(){
		
	}
	public static SingletonDemo createInstance(){
		return new SingletonDemo();
	}
	public void test(){
		System.out.println("����һ������ģʽ����ֻ��ʵ����һ��");
	}
	public static void main(String args[]){
		SingletonDemo single=SingletonDemo.createInstance();
		single.test();
	}
}
