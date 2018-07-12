package com.gitee.linzl.lambda.collection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

/**
 * 1.stream不存储数据
 * 
 * 2.stream不改变源数据
 * 
 * 3.stream的延迟执行特性
 */
public class ListDemo {
	List<Student> stuList = null;

	@Before
	public void init() {
		Random random = new Random();
		stuList = new ArrayList<Student>() {
			{
				for (int i = 0; i < 100; i++) {
					add(new Student("student" + i, random.nextInt(50) + 50));
				}
			}
		};
	}

	@Test
	public void listForEach() {
		// 两种写法有何不同
		stuList.stream().forEach(stu -> {
			System.out.println(stu);
		});

		stuList.stream().forEach(stu -> {
			System.out.println(stu);
		});
	}

	@Test
	/**
	 * 对于stream的聚合、消费或收集操作只能进行一次，再次操作会报错
	 */
	public void listTest() {
		Stream<String> stream = Stream.generate(() -> "user").limit(20);
		stream.forEach(System.out::println);
		// stream.forEach(System.out::println);
	}

	@Test
	public void listFilter() {
		Objects.requireNonNull(stuList);
		List<String> studentList = stuList.stream().filter(stu -> stu.getScore() > 85)
				.sorted(Comparator.comparing(Student::getScore).reversed())

				.map(Student::getName).collect(Collectors.toList());
		System.out.println(studentList);
	}

	@Test
	public void listMap() {
		List<String> list2 = stuList.stream().map(string -> {
			// 修改数据
			return "stream().map()处理之后：" + string;
		}).collect(Collectors.toList());
		System.out.println(list2);

		Map<String, Student> map = stuList.stream().collect(Collectors.toMap(Student::getName, Function.identity()));
		System.out.println(map);
	}

	@Test
	public void listMapToInt() {
		// IntStream intStream = list.stream().mapToInt(string ->
		// Integer.parseInt(string));
		// System.out.println(intStream.sum());
	}

	@Test
	public void listFlatMap() {
	}

	@Test
	public void listFlatMapToInt() {
	}

	@Test
	public void listDistinct() {
	}

	@Test
	public void listSorted() {
	}

	@Test
	public void listSortedComparator() {
	}

	@Test
	public void listPeek() {
	}

	@Test
	public void listLimit() {
	}

	@Test
	public void listSkip() {
	}

	@Test
	public void listForEachOrdered() {

	}

	@Test
	public void listToArray() {

	}

	@Test
	public void listToArrayFunction() {

	}

	@Test
	public void listReduce() {

	}

	@Test
	public void listReduceOptional() {

	}

	@Test
	public void listReduceU() {

	}

	@Test
	public void listCollect() {

	}

	@Test
	public void listMinAndMax() {

	}
}
