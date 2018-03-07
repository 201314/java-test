package day06.decoration;

public class ScoutSoldier implements Soldier {
	private Soldier soldier;

	public ScoutSoldier(Soldier soldier) {
		this.soldier = soldier;
	}

	public void fire() {
		this.soldier.fire();
	}

	public void run() {
		this.soldier.run();
		System.out.println("使用侦查装备侦查");
	}

}
