package day06.decoration;

public class NightVisionSoldier implements Soldier {
	private Soldier soldier;

	public NightVisionSoldier(Soldier soldier) {
		this.soldier = soldier;
	}

	public void fire() {
		System.out.println("夜视装备可以夜视");
		this.soldier.fire();
	}

	public void run() {
		this.soldier.run();
	}

}
