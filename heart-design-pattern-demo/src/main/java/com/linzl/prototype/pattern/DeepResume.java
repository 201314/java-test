package com.linzl.prototype.pattern;

//此类实现了 Cloneable 接口，以指示 Object.clone() 方法可以合法地对该类实例进行按字段复制。 

//如果在没有实现 Cloneable 接口的实例上调用 Object 的 clone 方法，则会导致抛出 CloneNotSupportedException 异常。 

//按照惯例，实现此接口的类应该使用公共方法重写 Object.clone（它是受保护的）。

//假设每份简历只有一段工作经历
public class DeepResume implements Cloneable {
	private String name;
	private int age;

	private DeepWorkExperience work;

	public DeepResume(String name, int age, DeepWorkExperience work) {
		this.name = name;
		this.age = age;
		this.work = work;
	}

	public void displayResume() {
		System.out.println("\n姓名：" + name);
		System.out.println("年龄：" + age);
		System.out.println("工作时间：" + work.getTime());
		System.out.println("公司：" + work.getCompany());
	}

	// 深复制，将引用对象的变量指向复制过来的新对象
	// 两个类都必须实现 Cloneable
	public Object deepClone() {
		DeepResume resume = null;
		try {
			resume = (DeepResume) super.clone();

			// 深复制的关键
			// 将引用对象的变量指向复制过来的新对象
			resume.work = (DeepWorkExperience) this.work.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return resume;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public DeepWorkExperience getWork() {
		return work;
	}

	public void setWork(DeepWorkExperience work) {
		this.work = work;
	}
}
