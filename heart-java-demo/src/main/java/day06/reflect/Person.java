package day06.reflect;

public class Person {
	public String password = "123466";
	protected String sex = "男";
	private String name;
	private int age;

	public Person() {
	};

	private void say(String name) {
		System.out.println("我叫：" + name);
	}

	private Person(String name, int age, String sex) {
		System.out.println("名称：" + name + "年龄:" + age + "性别:" + sex);
	}

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String print() {
		return "我的密码是:" + this.password;
	}

	public void updatePassword(String password) {
		this.password = password;
	}

	public String toString() {
		return "姓名:" + this.name + "-->年龄:" + this.age;
	}

}
