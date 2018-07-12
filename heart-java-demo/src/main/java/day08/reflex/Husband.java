package day08.reflex;

import java.util.Arrays;
import java.util.List;

public class Husband extends SecondAbstract implements SecondInterface {

	public class HusbandPublicInner {
		public void sayPublic(String name) {
			System.out.println("HusbandPublicInner sayPublic");
		}
	}

	private class HusbandPrivateInner {
		public void sayPrivate() {
			System.out.println("HusbandPrivateInner sayPrivate");
		}
	}

	public Husband() {
		System.out.println("有0个参数的构造方法");
	}

	public Husband(String name) {
		this.name = name;
	}

	private Husband(String name, int salary) {
		this.name = name;
		this.salary = salary;
		System.out.println("有2个参数的构造方法");
	}

	protected Husband(String name, int salary, String demoThird) {
		this.name = name;
		this.salary = salary;
		System.out.println("有3个参数的构造方法");
	}

	private String sayThreeParam(String first, int second, double third) {
		return "sayThreeParam";
	}

	private List<String> list;

	private String sayPrivate() {
		return "";
	}

	protected String sayProtected() {
		return "";
	}

	public String sayPublic(String first, int second, double third) {
		return "sayPublic";
	}

	// 名字
	@FieldMap(columnName = "t_name", fieldClass = String.class)
	private String name;
	// 年龄
	private int age;
	// 体重
	private double weight;
	// 身高
	private float height;
	// 腰围
	private long waistline;
	// 爱好
	protected String[] interests;
	// 薪水
	public int salary;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public long getWaistline() {
		return waistline;
	}

	public void setWaistline(long waistline) {
		this.waistline = waistline;
	}

	public String[] getInterests() {
		return interests;
	}

	public void setInterests(String[] interests) {
		this.interests = interests;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Husband [name=" + name + ", age=" + age + ", weight=" + weight + ", height=" + height + ", waistline="
				+ waistline + ", interests=" + Arrays.toString(interests) + ", salary=" + salary + "]";
	}

	@Override
	public void firstOneMethod(String name) {

	}

	@Override
	public String firstTwoMethod() {
		return null;
	}

	@Override
	public String firstThreeMethod(String name, String height) {
		return null;
	}

	@Override
	public void secondOneMethod(String name) {

	}

	@Override
	public String secondTwoMethod() {
		return null;
	}

	@Override
	public String secondThreeMethod(String name, String height) {
		return null;
	}

}
