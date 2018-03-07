package day06.decoration;

public class SecondSoldier implements Soldier {
	private Soldier soldier;
	public SecondSoldier(Soldier soldier){
		this.soldier=soldier;
	}
	public void fire() {
		System.out.println("�ɵ�����");
	}
	public void run() {
		System.out.println("���Ǳ���ѡ��Ͷ���ɣ�����");
		soldier.run();
	}

}
