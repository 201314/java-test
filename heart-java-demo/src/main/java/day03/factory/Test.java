package day03.factory;

public class Test {
	public Test(){
		
	}
	public static void main(String[] args) {
		Person p=PersonFactory.createInstance();
		p.test();//��Ϊtest()�Ǹ����еķ��������˷�����������д�ˣ����Ե��õ��Ǳ���д���������test()����
		
		Child child=PersonFactory.createInstance();
		child.test("��������,");
		child.test();
	}
}
