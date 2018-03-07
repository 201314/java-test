package day06.decoration;

public class FirstSoldier implements Soldier {
	private Soldier soldier;
	
	public FirstSoldier(Soldier soldier){
		this.soldier=soldier;//构造方法是为了接收基本实现的类的参数
	}
	
	public void fire() {
		System.out.println("小日本你别想逃");
		soldier.fire();
	}

	public void run() {
		System.out.println("谁想和中国抗争，只有死路一条");
	}

}
