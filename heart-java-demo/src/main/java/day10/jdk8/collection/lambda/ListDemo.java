package day10.jdk8.collection.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

public class ListDemo {
	List<String> list = null;

	@Before
	public void init() {
		list = new ArrayList<>();
		list.add("1");
		list.add("1");
		list.add("2");
		list.add("3");
	}

	@Test
	public void listFilter() {
		Objects.requireNonNull(list);
		List<String> list2 = list.stream().filter(s -> s != "1").collect(Collectors.toList());
		System.out.println(list2.toString());
	}

	@Test
	public void listMap() {
		List<String> list2 = list.stream().map(string -> {
			// 修改数据
			return "stream().map()处理之后：" + string;
		}).collect(Collectors.toList());

		list2.stream().forEach(string -> {
			System.out.println(string);
		});
	}

	@Test
	public void listMapToInt() {
		IntStream intStream = list.stream().mapToInt(string -> Integer.parseInt(string));
		System.out.println(intStream.sum());
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
		list.stream().forEach(string -> {
			System.out.println(string);
		});

		// 这是以前的写法
		list.forEach(string -> {
			System.out.println(string);
		});
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
