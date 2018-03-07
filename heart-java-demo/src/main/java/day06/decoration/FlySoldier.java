package day06.decoration;

public class FlySoldier implements Soldier {
	private Soldier soldier;

	public FlySoldier(Soldier soldier) {
		this.soldier = soldier;
	}

	public void fire() {
		System.out.println("使用飞行器装配，可以飞行");
		this.soldier.fire();
	}

	public void run() {
		this.soldier.run();

	}

}
