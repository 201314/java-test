package day05.comparator;

public class User implements Comparable{
	private int age;
	private String name;
	public User(int age,String name){
		this.age=age;
		this.name=name;
	}
	public int compareTo(Object obj) {
		User user=(User)obj;
		if(this.age>user.age){
			return -1;
		}else {
			return 1;
		}
	}
	public String toString(){
		return "名字:"+this.name+"-->年龄:"+this.age;
	}

}
