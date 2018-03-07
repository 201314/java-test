package day06;

public class Reflect {
	//public Reflect(){};
	private String name;
	public Reflect(){};
	private Reflect(String userName,int age){
		System.out.println("姓名:"+userName+"年龄:"+age);
	}
	public void printh(String age){
		System.out.println("age:"+age);
		System.out.println("hello");
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void say(){
		System.out.println("hello world");
	}
	
	public class Child{
		private String name="who";
		public void print(){
			System.out.println("world");
		}
	}
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		Reflect ref1=new Reflect();
		System.out.println(ref1.getClass());
		
		Child child;
		try {
			Class myClass= Class.forName("day06.Reflect");
			Object instance=myClass.newInstance();
			System.out.println(instance);
			Reflect ref=(Reflect)instance;
			ref.printh("20");
			child=ref.new Child();
			child.print();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
