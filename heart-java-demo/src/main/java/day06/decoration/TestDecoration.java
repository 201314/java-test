package day06.decoration;

public class TestDecoration {
	public static void main(String[] args) {
		System.out.println("\t児云糞�崟燹�！！！！！！！！！！！！！！！！！");
		Soldier soldier=new SoldierImpl();
		soldier.run();
		soldier.fire();
		
		System.out.println("\n\t匯蚊廾蔑！！！！！！！！！！！！！！！！！！");
		soldier=new FirstSoldier(new SoldierImpl());
		soldier.run();
		soldier.fire();
		
		System.out.println("\n\t曾蚊廾蔑！！！！！！！！！！！！！！！！！！");
		soldier=new SecondSoldier(new SoldierImpl());
		soldier.run();
		soldier.fire();
		
		System.out.println("\n\t眉蚊廾蔑！！！！！！！！！！！！！！！！！！");
		soldier=new FirstSoldier(new SecondSoldier(new SoldierImpl()));
		soldier.run();
		soldier.fire();
	}
}
