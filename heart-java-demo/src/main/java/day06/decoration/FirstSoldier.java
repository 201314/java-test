package day06.decoration;

public class FirstSoldier implements Soldier {
	private Soldier soldier;
	
	public FirstSoldier(Soldier soldier){
		this.soldier=soldier;//���췽����Ϊ�˽��ջ���ʵ�ֵ���Ĳ���
	}
	
	public void fire() {
		System.out.println("С�ձ��������");
		soldier.fire();
	}

	public void run() {
		System.out.println("˭����й�������ֻ����·һ��");
	}

}
