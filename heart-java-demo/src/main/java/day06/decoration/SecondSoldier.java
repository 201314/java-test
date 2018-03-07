package day06.decoration;

public class SecondSoldier implements Soldier {
	private Soldier soldier;
	public SecondSoldier(Soldier soldier){
		this.soldier=soldier;
	}
	public void fire() {
		System.out.println("干掉岛国");
	}
	public void run() {
		System.out.println("你们别无选择，投降吧！！！");
		soldier.run();
	}

}
