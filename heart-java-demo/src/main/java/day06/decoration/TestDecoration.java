package day06.decoration;

public class TestDecoration {
	public static void main(String[] args) {
		System.out.println("\t����ʵ���ࡪ����������������������������������");
		Soldier soldier=new SoldierImpl();
		soldier.run();
		soldier.fire();
		
		System.out.println("\n\tһ��װ�Ρ�����������������������������������");
		soldier=new FirstSoldier(new SoldierImpl());
		soldier.run();
		soldier.fire();
		
		System.out.println("\n\t����װ�Ρ�����������������������������������");
		soldier=new SecondSoldier(new SoldierImpl());
		soldier.run();
		soldier.fire();
		
		System.out.println("\n\t����װ�Ρ�����������������������������������");
		soldier=new FirstSoldier(new SecondSoldier(new SoldierImpl()));
		soldier.run();
		soldier.fire();
	}
}
