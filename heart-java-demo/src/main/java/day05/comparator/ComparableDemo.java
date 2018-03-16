package day05.comparator;

/**
 * Comparable是通用的接口，用户可以实现它来完成自己特定的比较 和 Comparator 对比，比较固定，和一个具体类相绑定
 * 
 * @author linzl 最后修改时间：2014年9月21日
 */
public class ComparableDemo implements Comparable<Object> {// 相当于内比较器，自己和自己比
	private String name;
	private int age;

	public ComparableDemo(String name, int age) {
		this.name = name;
		this.age = age;
	}

	@Override
	public int compareTo(Object obj) {
		ComparableDemo second = (ComparableDemo) obj;
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