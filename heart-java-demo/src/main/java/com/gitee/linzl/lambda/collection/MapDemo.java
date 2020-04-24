package com.gitee.linzl.lambda.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author linzhenlie
 * @date 2020-04-24
 */
public class MapDemo {

	public void listToMap() {
		List<Student> students = new ArrayList<>();
		Student student = new Student();
		student.setName("linzl");
		student.setScore(100);
		students.add(student);

		Student student2 = new Student();
		student2.setName("linzl2");
		student2.setScore(null);
		students.add(student2);

		// map的key重复 会报Duplicate key ,可以使用以下Function.identity() 换成 c->c,(k1, k2)->k2)避免
		// Map<String, Student> res =
		// students.stream().collect(Collectors.toMap(Student::getName,
		// Function.identity()));
		Map<String, Student> res = students.stream()
				.collect(Collectors.toMap(Student::getName, c -> c, (k1, k2) -> k2));
		;
		
		// 如果key,value可能为null时需要判空
		Map<String, Integer> res2 = students.stream()
				.collect(Collectors.toMap(stu -> Optional.ofNullable(stu).map(Student::getName).orElse(""),
						stu -> Optional.ofNullable(stu).map(Student::getScore).orElse(0), (key1, key2) -> key2));

		System.out.println(res2);
	}

	public static void main(String[] args) {
		 
	}
}
