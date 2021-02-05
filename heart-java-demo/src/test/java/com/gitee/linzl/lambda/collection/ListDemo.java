package com.gitee.linzl.lambda.collection;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
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
public class ListDemo {
    List<Student> stuList = null;

    @Before
    public void init() {
        Random random = new Random();
        stuList = new ArrayList<Student>() {
            {
                add(new Student("student1", 200));
                add(new Student("student2", 110));
                add(new Student("student3", 220));
                add(new Student("student4", 420));
                add(new Student("student5", 206));
                add(new Student("student6", 290));
                add(new Student("student7", 9200));
                add(new Student("student8", 1200));
            }
        };
    }

    @Test
    public void listForEach() {
        // 两种写法有何不同
        stuList.forEach(stu -> System.out.println(stu));

        stuList.stream().forEach(stu -> System.out.println(stu));
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

        String result = stuList.stream().map(Student::getName).collect(Collectors.joining(","));
        System.out.println(result);
    }

    @Test
    public void listMapToInt() {
        IntStream intStream = stuList.stream().mapToInt(stu -> stu.getScore());
        System.out.println(intStream.sum());
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
        DoubleSummaryStatistics dss = stuList.stream().collect(Collectors.summarizingDouble(Student::getScore));
        // 求平均值
        System.out.println(dss.getAverage());
        // 求最大值
        System.out.println(dss.getMax());
        // 求和
        System.out.println(dss.getSum());
    }

    @Test
    public void listMinAndMax() {
        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("22");
        list.add("33");
        list.add("44");
        list.add("55");

        list.stream().forEach((str) -> {
            if (str.equals("33")) {
                System.out.println("结束11");
                return;
            }
            System.out.println("有没有搞错" + str);
        });

        list.stream().anyMatch(str -> str.equals("33"));
    }

    @Test
    public void distinct() {
        List<String> list = new ArrayList<>();
        list.add("1111");
        list.add("22");
        list.add("3333");
        list.add("44");
        list.add("55");
        list.add("1111");
        list.add("3333");
        List<String> result = list.stream().filter(distinctByKey(b -> b)).collect(Collectors.toList());
        System.out.println(result);
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>(10);
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * listA内容变为listA和listB都存在的对象,listB不变
     * 交集
     */
    public void retainAll() {
        List<String> listA = new ArrayList<>();
        listA.add("A");
        listA.add("B");
        listA.add("C");

        List<String> listB = new ArrayList<>();
        listB.add("B");
        listB.add("C");
        listB.add("D");
        listA.retainAll(listB);
        System.out.println(listA);
    }

    /**
     * listA中存在的listB的内容去重,listB不变
     * 差集
     */
    public void removeAll() {
        List<String> listA = new ArrayList<>();
        listA.add("A");
        listA.add("B");
        listA.add("C");

        List<String> listB = new ArrayList<>();
        listB.add("B");
        listB.add("C");
        listB.add("D");
        listA.removeAll(listB);
        System.out.println(listA);
    }

    /**
     * 为了去重，listA先取差集，然后追加全部的listB,listB不变
     * 并集
     */
    public void addAll() {
        List<String> listA = new ArrayList<>();
        listA.add("A");
        listA.add("B");
        listA.add("C");

        List<String> listB = new ArrayList<>();
        listB.add("B");
        listB.add("C");
        listB.add("D");
        listA.removeAll(listB);
        listA.addAll(listB);
        System.out.println(listA);
    }

    public void merge() {
        List<String> listA = new ArrayList<>();
        listA.add("A");
        listA.add("B");
        listA.add("C");

        List<String> listB = new ArrayList<>();
        listB.add("D");
        listB.add("E");
        listB.add("F");

        List<String> all =
                listA.stream().collect(() -> listB,
                        (list, items) -> list.add(items), (nextList, preList) -> nextList.addAll(preList));
        System.out.println(all);
    }
}
