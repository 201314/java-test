package day05.comparator;

import java.util.Arrays;

public class ComparableDemo {

	public static void main(String[] args) {
		Student[] temp = { new Student("linzl", 25), new Student("linwc", 20), new Student("linys", 56),
				new Student("linsj", 47), new Student("linwb", 83), new Student("lins", 25) };
		Arrays.sort(temp);
		for (int i = 0; i < temp.length; i++) {
			Student stu = temp[i];
			System.out.println(stu.getName());
			System.out.println(stu.getAge());
			System.out.println();
		}
	}

}

/**
 * Comparable是通用的接口，用户可以实现它来完成自己特定的比较 和 Comparator 对比，比较固定，和一个具体类相绑定
 * 
 * @author linzl 最后修改时间：2014年9月21日
 */
class Student implements Comparable {
	private String name;
	private int age;

	public Student(String name, int age) {
		this.name = name;
		this.age = age;
	}

	@Override
	public int compareTo(Object obj) {
		Student second = (Student) obj;
		if (this.age > second.getAge()) {
			return -1;
		} else if (this.age < second.getAge()) {
			return 1;
		}
		return this.name.compareTo(second.getName());
	}

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

}