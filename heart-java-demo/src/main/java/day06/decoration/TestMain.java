package day06.decoration;

public class TestMain {
	public static void main(String[] args) {
		System.out.println("基本的：");
		Soldier soldier = new SoldierImpl();
		soldier.fire();
		soldier.run();

		System.out.println("\n飞行器:");
		soldier = new FlySoldier(new SoldierImpl());
		soldier.fire();
		soldier.run();

		System.out.println("\n侦查:");
		soldier = new ScoutSoldier(new SoldierImpl());
		soldier.fire();
		soldier.run();

		System.out.println("\n夜视:");
		soldier = new NightVisionSoldier(new SoldierImpl());
		soldier.fire();
		soldier.run();

		System.out.println("\n所有装备组合:");
		soldier = new NightVisionSoldier(new ScoutSoldier(new FlySoldier(new SoldierImpl())));
		soldier.fire();
		soldier.run();
	}

}
