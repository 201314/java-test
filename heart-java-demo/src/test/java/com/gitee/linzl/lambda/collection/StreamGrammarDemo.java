package com.gitee.linzl.lambda.collection;

import com.gitee.linzl.lambda.constructor.Student;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 1.stream不存储数据
 * 
 * 2.stream不改变源数据
 * 
 * 3.stream的延迟执行特性
 */
public class StreamGrammarDemo {
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
		// 创建无限流，通过limit提取指定大小
		Stream.generate(() -> new Student("name", 10)).limit(20).forEach(System.out::println);
		//生成100个随机数并由此创建出Stream实例
		Stream.generate(() -> (int) (Math.random() * 100)).limit(100).forEach(System.out::println);
	}

	public boolean filter(com.gitee.linzl.lambda.constructor.Student s) {
		System.out.println("begin compare");
		return s.getScore() > 85;
	}

	@Test
	public void test() {
		// stream的操作是延迟执行的，在列出班上超过85分的学生姓名例子中，在collect方法执行之前，
		// filter、sorted、map方法还未执行，只有当collect方法执行时才会触发之前转换操作
		Stream<Student> stream = stuList.stream().filter(this::filter);
		System.out.println("split-------------------------------------");
		List<com.gitee.linzl.lambda.constructor.Student> studentList = stream.collect(Collectors.toList());
	}

	/**
	 * 通过数组创建流
	 */
	@Test
	public void testArrayStream() {
		// 1.通过Arrays.stream
		// 1.1基本类型
		int[] arr = new int[] { 1, 2, 34, 5 };
		IntStream intStream = Arrays.stream(arr);
		// 1.2引用类型
		Student[] studentArr = new Student[] { new Student("s1", 29), new Student("s2", 27) };
		Stream<Student> studentStream = Arrays.stream(studentArr);
		// 2.通过Stream.of
		Stream<Integer> stream1 = Stream.of(1, 2, 34, 5, 65);
		// 注意生成的是int[]的流
		Stream<int[]> stream2 = Stream.of(arr, arr);
		stream2.forEach(System.out::println);

		IntStream stream3 = IntStream.of(new int[] {1, 2, 3});
		//[1,3)
		IntStream stream4 = IntStream.range(1, 3);
		//[1,3]
		IntStream stream5 = IntStream.rangeClosed(1, 3);
	}

	/**
	 * 通过集合创建流
	 */
	@Test
	public void testCollectionStream() {
		List<String> strs = Arrays.asList("11212", "dfd", "2323", "dfhgf");
		// 创建普通流
		Stream<String> stream = strs.stream();
		// 创建并行流
		Stream<String> stream1 = strs.parallelStream();
	}

	@Test
	public void testEmptyStream() {
		// 创建一个空的stream
		Stream<Integer> stream = Stream.empty();
	}

	/**
	 * 产生规律的数据
	 */
	@Test
	public void testUnlimitStream1() {
		// 0 2 4 6 8 10  limit，限制从流中获得前n个数据
		Stream.iterate(0, x -> x + 2).limit(6).forEach(System.out::println);
		// skip，跳过前n个数据
		Stream.iterate(0, x -> x).skip(1).limit(10).forEach(System.out::println);

		// Stream.iterate(0,x->x).limit(10).forEach(System.out::println);与如下代码意思是一样的
		Stream.iterate(0, UnaryOperator.identity()).limit(10).forEach(System.out::println);
	}

	/* ===========================最常用=========================== */
	/**
	 * map把一种类型的流转换为另外一种类型的流 将String数组中字母转换为大写
	 */
	@Test
	public void testMap() {
		String[] arr = new String[] { "yes", "YES", "no", "NO" };
		Arrays.stream(arr).map(x -> x.toLowerCase()).forEach(System.out::println);
	}

	@Test
	public void testFilter() {
		Integer[] arr = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		Arrays.stream(arr).filter(x -> x > 3 && x < 8).forEach(System.out::println);

		List<String> languages = Arrays.asList("Java", "html5", "JavaScript", "C++", "hibernate", "PHP");

		//开头是J的语言
		filter(languages, (name) -> name.startsWith("J"));
		//5结尾的
		filter(languages, (name) -> name.endsWith("5"));
		//所有的语言
		filter(languages, (name) -> true);
		//一个都不显示
		filter(languages, (name) -> false);
		//显示名字长度大于4
		filter(languages, (name) -> name.length() > 4);
		System.out.println("-----------------------");
		//名字以J开头并且长度大于4的
		Predicate<String> c1 = (name) -> name.startsWith("J");
		Predicate<String> c2 = (name) -> name.length() > 4;
		filter(languages, c1.and(c2));

		//名字不是以J开头
		Predicate<String> c3 = (name) -> name.startsWith("J");
		filter(languages, c3.negate());

		//名字以J开头或者长度小于4的
		Predicate<String> c4 = (name) -> name.startsWith("J");
		Predicate<String> c5 = (name) -> name.length() < 4;
		filter(languages, c4.or(c5));

		//名字为Java的
		filter(languages, Predicate.isEqual("Java"));

		//判断俩个字符串是否相等
		boolean test = Predicate.isEqual("hello").test("world");
		System.out.println(test);
	}

	/**
	 * flapMap：拆解流
	 */
	@Test
	public void testFlapMap1() {
		String[] arr1 = { "a", "b", "c", "d" };
		String[] arr2 = { "e", "f", "c", "d" };
		String[] arr3 = { "h", "j", "c", "d" };
		// Stream.of(arr1, arr2, arr3).flatMap(x ->
		// Arrays.stream(x)).forEach(System.out::println);
		Stream.of(arr1, arr2, arr3).flatMap(Arrays::stream).forEach(System.out::println);
	}

	String[] arr1 = { "abc", "a", "bc", "abcd" };

	/**
	 * Comparator.comparing
	 * 倒序reversed(),java8泛型推导的问题，所以如果comparing里面是非方法引用的lambda表达式就没办法直接使用reversed()
	 * Comparator.reverseOrder():也是用于翻转顺序，用于比较对象（Stream里面的类型必须是可比较的）
	 * Comparator.naturalOrder():返回一个自然排序比较器，用于比较对象（Stream里面的类型必须是可比较的）
	 */
	@Test
	public void testSorted1() {
		/**
		 * 按照字符长度排序
		 */
		Arrays.stream(arr1).sorted((x, y) -> {
			if (x.length() > y.length()) {
				return 1;
			}
			if (x.length() < y.length()) {
				return -1;
			}
			return 0;
		}).forEach(System.out::println);
		Arrays.stream(arr1).sorted(Comparator.comparing(String::length)).forEach(System.out::println);
		Arrays.stream(arr1).sorted(Comparator.comparing(String::length).reversed()).forEach(System.out::println);
		Arrays.stream(arr1).sorted(Comparator.reverseOrder()).forEach(System.out::println);
		Arrays.stream(arr1).sorted(Comparator.naturalOrder()).forEach(System.out::println);
	}

	/**
	 * thenComparing 先按照首字母排序 之后按照String的长度排序
	 */
	@Test
	public void testSorted3() {
		Arrays.stream(arr1).sorted(Comparator.comparing(this::com1).thenComparing(String::length))
				.forEach(System.out::println);
	}

	public char com1(String x) {
		return x.charAt(0);
	}

	/**
	 * 可以把两个stream合并成一个stream（合并的stream类型必须相同） 只能两两合并
	 */
	@Test
	public void testConcat() {
		String[] arr1 = new String[] { "a", "b", "c", "d" };
		String[] arr2 = new String[] { "d", "e", "f", "g" };
		Stream<String> stream1 = Stream.of(arr1);
		Stream<String> stream2 = Stream.of(arr2);
		Stream.concat(stream1, stream2).distinct().forEach(System.out::println);
	}

	/**
	 * 用于组合流中的元素，如求和，求积，求最大值等
	 */
	@Test
	public void testReduce() {
		Integer[] pers = new Integer[] { 10, 20, 35, 30 };
		Stream.of(pers).reduce(Integer::sum);
		// 等价
		Stream.of(pers).mapToInt(Integer::intValue).sum();
		Stream.of(pers).mapToInt((val) -> {
			return val.intValue();
		}).sum();

		List<Student> userList = new ArrayList<>();
		Student first = new Student("hello",12);
		first.setMoney(new BigDecimal(12.03));
		userList.add(first);

		Student second = new Student("who",34);
		second.setMoney(new BigDecimal(48.03));
		userList.add(second);
		 // java 8 stream version
        BigDecimal result2 = userList.stream()
                // 将user对象的mongey取出来map为Bigdecimal
                .map(Student::getMoney)
                // 使用reduce聚合函数,实现累加器
                //第一个参数是我们给出的初值，第二个参数是累加器
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        System.out.println("result2 = "+result2);
	}

	public static void filter(List<String> languages, Predicate<String> condition) {
		for (String name : languages) {
			if (condition.test(name)) {
				System.out.println(name + " ");
			}
		}
	}
}
