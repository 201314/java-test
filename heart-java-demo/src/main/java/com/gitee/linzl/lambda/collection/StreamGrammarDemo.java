package com.gitee.linzl.lambda.collection;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 1.stream不存储数据
 * <p>
 * 2.stream不改变源数据
 * <p>
 * 3.stream的延迟执行特性
 */
public class StreamGrammarDemo {
    static List<Student> stuList = null;

    static {
        Random random = new Random();
        stuList = new ArrayList<Student>() {
            {
                for (int i = 0; i < 100; i++) {
                    add(new Student("student" + i, random.nextInt(50) + 50));
                }
            }
        };
    }


    /**
     * 对于stream的聚合、消费或收集操作只能进行一次，再次操作会报错
     */
    public static void listTest() {
        Stream<String> stream = Stream.generate(() -> "user").limit(20);
        stream.forEach(System.out::println);
    }

    public boolean filter(Student s) {
        System.out.println("begin compare");
        return s.getScore() > 85;
    }


    public void test() {
        // stream的操作是延迟执行的，在列出班上超过85分的学生姓名例子中，在collect方法执行之前，
        // filter、sorted、map方法还未执行，只有当collect方法执行时才会触发之前转换操作
        Stream<Student> stream = stuList.stream().filter(this::filter);
        System.out.println("split-------------------------------------");
        List<Student> studentList = stream.collect(Collectors.toList());
    }

    /**
     * 通过数组创建流
     */

    public static void testArrayStream() {
        // 1.通过Arrays.stream
        // 1.1基本类型
        int[] arr = new int[]{1, 2, 34, 5};
        IntStream intStream = Arrays.stream(arr);
        // 1.2引用类型
        Student[] studentArr = new Student[]{new Student("s1", 29), new Student("s2", 27)};
        Stream<Student> studentStream = Arrays.stream(studentArr);
        // 2.通过Stream.of
        Stream<Integer> stream1 = Stream.of(1, 2, 34, 5, 65);
        // 注意生成的是int[]的流
        Stream<int[]> stream2 = Stream.of(arr, arr);
        stream2.forEach(System.out::println);
    }

    /**
     * 通过集合创建流
     */

    public static void testCollectionStream() {
        List<String> strs = Arrays.asList("11212", "dfd", "2323", "dfhgf");
        // 创建普通流
        Stream<String> stream = strs.stream();
        // 创建并行流
        Stream<String> stream1 = strs.parallelStream();
    }


    public static void testEmptyStream() {
        // 创建一个空的stream
        Stream<Integer> stream = Stream.empty();
    }

    public static void testUnlimitStream() {
        // 创建无限流，通过limit提取指定大小
        Stream.generate(() -> "number" + new Random().nextInt()).limit(100).forEach(System.out::println);
        Stream.generate(() -> new Student("name", 10)).limit(20).forEach(System.out::println);
    }

    /**
     * 产生规律的数据
     */
    public static void testUnlimitStream1() {
        Stream.iterate(0, x -> x + 1).limit(10).forEach(System.out::println);
        Stream.iterate(0, x -> x).limit(10).forEach(System.out::println);

        // Stream.iterate(0,x->x).limit(10).forEach(System.out::println);与如下代码意思是一样的
        Stream.iterate(0, UnaryOperator.identity()).limit(10).forEach(System.out::println);
    }

    /* ===========================最常用=========================== */

    /**
     * map把一种类型的流转换为另外一种类型的流 将String数组中字母转换为大写
     */

    public static void testMap() {
        String[] arr = new String[]{"yes", "YES", "no", "NO"};
        Arrays.stream(arr).map(x -> x.toLowerCase()).forEach(System.out::println);
    }


    public static void testFilter() {
        Integer[] arr = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Arrays.stream(arr).filter(x -> x > 3 && x < 8).forEach(System.out::println);
    }

    /**
     * flapMap：拆解流
     */
    public static void testFlapMap1() {
        String[] arr1 = {"a", "b", "c", "d"};
        String[] arr2 = {"e", "f", "c", "d"};
        String[] arr3 = {"h", "j", "c", "d"};
        // Stream.of(arr1, arr2, arr3).flatMap(x ->
        // Arrays.stream(x)).forEach(System.out::println);
        Stream.of(arr1, arr2, arr3).flatMap(Arrays::stream).forEach(System.out::println);
    }

    static String[] arr1 = {"abc", "a", "bc", "abcd"};

    /**
     * Comparator.comparing是一个键提取的功能 以下两个语句表示相同意义
     */
    public static void testSorted1() {
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
    }

    /**
     * 倒序 reversed(),java8泛型推导的问题，所以如果comparing里面是非方法引用的lambda表达式就没办法直接使用reversed()
     * Comparator.reverseOrder():也是用于翻转顺序，用于比较对象（Stream里面的类型必须是可比较的） Comparator.
     * naturalOrder()：返回一个自然排序比较器，用于比较对象（Stream里面的类型必须是可比较的）
     */

    public static void testSorted2() {
        Arrays.stream(arr1).sorted(Comparator.comparing(String::length).reversed()).forEach(System.out::println);
        Arrays.stream(arr1).sorted(Comparator.reverseOrder()).forEach(System.out::println);
        Arrays.stream(arr1).sorted(Comparator.naturalOrder()).forEach(System.out::println);
    }

    /**
     * thenComparing 先按照首字母排序 之后按照String的长度排序
     */
    public void testSorted3() {
        Arrays.stream(arr1).sorted(Comparator.comparing(this::com1).thenComparing(String::length))
                .forEach(System.out::println);
    }

    public char com1(String x) {
        return x.charAt(0);
    }

    /**
     * limit，限制从流中获得前n个数据
     */
    public static void testLimit() {
        Stream.iterate(1, x -> x + 2).limit(10).forEach(System.out::println);
    }

    /**
     * skip，跳过前n个数据
     */
    public static void testSkip() {
        // Stream.of(arr1).skip(2).limit(2).forEach(System.out::println);
        Stream.iterate(1, x -> x + 2).skip(1).limit(5).forEach(System.out::println);
    }

    /**
     * 可以把两个stream合并成一个stream（合并的stream类型必须相同） 只能两两合并
     */

    public static void testConcat() {
        String[] arr1 = new String[]{"a", "b", "c", "d"};
        String[] arr2 = new String[]{"d", "e", "f", "g"};
        Stream<String> stream1 = Stream.of(arr1);
        Stream<String> stream2 = Stream.of(arr2);
        Stream.concat(stream1, stream2).distinct().forEach(System.out::println);
    }

    /**
     * 用于组合流中的元素，如求和，求积，求最大值等
     */

    public static void testReduce() {
        Integer[] pers = new Integer[]{10, 20, 35, 30};
        Stream.of(pers).reduce(Integer::sum);
        // 等价
        Stream.of(pers).mapToInt(Integer::intValue).sum();
        Stream.of(pers).mapToInt((val) -> {
            return val.intValue();
        }).sum();

        List<Student> userList = new ArrayList<>();
        Student first = new Student("hello", 12);
        first.setMoney(new BigDecimal(12.03));
        userList.add(first);

        Student second = new Student("who", 34);
        second.setMoney(new BigDecimal(48.03));
        userList.add(second);
        // java 8 stream version
        BigDecimal result2 = userList.stream()
                // 将user对象的mongey取出来map为Bigdecimal
                .map(Student::getMoney)
                // 使用reduce聚合函数,实现累加器
                //第一个参数是我们给出的初值，第二个参数是累加器
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("result2 = " + result2);
    }

    public static void main(String[] args) {
        StreamGrammarDemo streamGrammarDemo = new StreamGrammarDemo();
        listTest();
        streamGrammarDemo.test();
        testArrayStream();
        testCollectionStream();
        testEmptyStream();
        testUnlimitStream();
        testUnlimitStream1();
        testMap();
        testFilter();
        testFlapMap1();
        testSorted1();
        testSorted2();
        streamGrammarDemo.testSorted3();
        testSkip();
        testConcat();
        testReduce();
    }
}
