package day06.reflect;

public class Person {
	public String password="123466";
	protected String sex="��";
	private String name;
	private int age;
	
	public Person(){};
	
	private void say(String name){
		System.out.println("�ҽУ�"+name);
	}
	
	private Person(String name,int age,String sex){
		System.out.println("���ƣ�"+name+"����:"+age+"�Ա�:"+sex);
	}
		
	public Person(String name,int age){
		this.name=name;
		this.age=age;
	}
	
	public String print(){
		return "�ҵ�������:"+this.password;
	}
	
	public void updatePassword(String password){
		this.password=password;
	}
	
	public String toString(){
		return "����:"+this.name+"-->����:"+this.age;		
	}
	
}
