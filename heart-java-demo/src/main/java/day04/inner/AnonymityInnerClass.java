package day04.inner;

/**
 * �����ڲ���
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018��1��10��
 */
public class AnonymityInnerClass {
	public void anonymity(Person per) {
		per.test();
		System.out.println("�������ڲ������к���");
	}

	public static void main(String[] args) {
		AnonymityInnerClass aic = new AnonymityInnerClass();
		aic.anonymity(new Person() {// �����ڲ��࣬������ʹ��
			public void test() {
				System.out.println("���������ڲ���");
			}
		});
	}
}
