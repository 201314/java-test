package day10.jdk8.collection.lambda;

public class Student {
	private String name;
	private Integer score;

	public Student() {

	}

	public Student(String name, Integer score) {
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
