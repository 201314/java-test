package day10.jdk8.collection.lambda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
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
		// List<String> list2 = list.stream().map(string -> {
		// // 修改数据
		// return "stream().map()处理之后：" + string;
		// }).collect(Collectors.toList());
		//
		// list2.stream().forEach(string -> {
		// System.out.println(string);
		// });
	}

	@Test
	public void listMapToInt() {
		// IntStream intStream = list.stream().mapToInt(string ->
		// Integer.parseInt(string));
		// System.out.println(intStream.sum());
	}

	@Test
	// TODO
	public void listFlatMap() {
	}

	@Test
	// TODO
	public void listFlatMapToInt() {
	}

	@Test
	// TODO
	public void listDistinct() {
	}

	@Test
	// TODO
	public void listSorted() {
	}

	@Test
	// TODO
	public void listSortedComparator() {
	}

	@Test
	// TODO
	public void listPeek() {
	}

	@Test
	// TODO
	public void listLimit() {
	}

	@Test
	// TODO
	public void listSkip() {
	}

	@Test
	public void listForEach() {
		// 两种写法有何不同
		// list.stream().forEach(string -> {
		// System.out.println(string);
		// });

		// 这是以前的写法
		// list.forEach(string -> {
		// System.out.println(string);
		// });
	}

	@Test
	// TODO
	public void listForEachOrdered() {

	}

	@Test
	// TODO
	public void listToArray() {

	}

	@Test
	// TODO
	public void listToArrayFunction() {

	}

	@Test
	// TODO
	public void listReduce() {

	}

	@Test
	// TODO
	public void listReduceOptional() {

	}

	@Test
	// TODO
	public void listReduceU() {

	}

	@Test
	// TODO
	public void listCollect() {

	}

	@Test
	// TODO
	public void listMinAndMax() {

	}
}
