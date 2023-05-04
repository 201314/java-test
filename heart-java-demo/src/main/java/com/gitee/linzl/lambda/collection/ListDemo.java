package com.gitee.linzl.lambda.collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import com.gitee.linzl.lambda.constructor.Student;

/**
 * 1.stream不存储数据
 * <p>
 * 2.stream不改变源数据
 * <p>
 * 3.stream的延迟执行特性
 */
public class ListDemo {
    static List<Student> stuList;

    static {
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

    public static void listForEach() {
        // 两种写法有何不同
        stuList.forEach(stu -> {
            System.out.println(stu);
        });

        stuList.stream().forEach(stu -> {
            System.out.println(stu);
        });
    }


    /**
     * 对于stream的聚合、消费或收集操作只能进行一次，再次操作会报错
     */
    public static void listTest() {
        Stream<String> stream = Stream.generate(() -> "user").limit(20);
        stream.forEach(System.out::println);
        // stream.forEach(System.out::println);
    }


    public static void listFilter() {
        Objects.requireNonNull(stuList);
        List<String> studentList = stuList.stream().filter(stu -> stu.getScore() > 85)
                .sorted(Comparator.comparing(Student::getScore).reversed())

                .map(Student::getName).collect(Collectors.toList());
        System.out.println(studentList);
    }


    public static void listMap() {
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


    public static void listMapToInt() {
        IntStream intStream = stuList.stream().mapToInt(stu -> stu.getScore());
        System.out.println(intStream.sum());
    }


    public static void listFlatMap() {
    }


    public static void listFlatMapToInt() {
    }

    /**
     * 去重
     */
    public static void listDistinct() {
        List<String> list = new ArrayList<>();
        list.add("1111");
        list.add("22");
        list.add("3333");
        list.add("44");
        list.add("55");
        list.add("1111");
        list.add("3333");
        List<String> result = list.stream().filter(distinctByKey(b -> b)).collect(Collectors.toList());
        Set<String> set = new HashSet<>(list);
        List<String> result2 = new ArrayList<>(set);
        System.out.println("result:" + result);
        System.out.println("result2:" + result2);


        List<String> result3 = list.stream().distinct().collect(Collectors.toList());
        System.out.println("result3:" + result3);
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>(10);
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


    public static void listSorted() {
        List<Integer> studentList = new ArrayList<Integer>() {
            {
                add(100);
                add(97);
                add(96);
                add(95);
            }
        };
        // java.util.Comparator 是函数式接口
        Collections.sort(studentList, (s1, s2) -> Integer.compare(s1, s2));
        Collections.sort(studentList, Integer::compare);
        System.out.println(studentList);

        List<String> strLst = new ArrayList<String>() {
            {
                add("adfkjsdkfjdskjfkds");
                add("asdfasdfafgfgf");
                add("public static void main");
            }
        };
        // 类::实例方法
        // String::compareToIgnoreCase等同于(x,y)->x.compareToIgnoreCase(y)
        Collections.sort(strLst, String::compareToIgnoreCase);

        // (1)类::实例方法
        // (2)类::静态方法
        // (3)对象::实例方法
        // 方法引用还可以使用this::methodName及super::methodName表示该对象或者其父类对象中的方法

        // System.out::println 等同于(x)->System.out.println(x),
        // Math::pow 等同于(x,y)->Math.pow(x,y)
    }


    public static void listSortedComparator() {
    }


    public static void listPeek() {
    }


    public static void listLimit() {
    }


    public static void listSkip() {
    }


    public static void listForEachOrdered() {

    }


    public static void listToArray() {

    }


    public static void listToArrayFunction() {

    }


    public static void listReduce() {

    }


    public static void listReduceOptional() {

    }


    public static void listReduceU() {

    }


    public static void listCollect() {
        DoubleSummaryStatistics dss = stuList.stream().collect(Collectors.summarizingDouble(Student::getScore));
        // 求平均值
        System.out.println(dss.getAverage());
        // 求最大值
        System.out.println(dss.getMax());
        // 求和
        System.out.println(dss.getSum());
    }


    public static void listMinAndMax() {
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


    /**
     * listA内容变为listA和listB都存在的对象,listB不变
     * 交集
     */
    public static void retainAll() {
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
    public static void removeAll() {
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
     * 并集
     * 为了去重，listA先取差集，然后追加全部的listB,listB不变
     */
    public static void addAll() {
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

    public static void merge() {
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

    public static void main(String[] args) {
        listForEach();
        /*listTest();
        listFilter();
        listMap();
        listMapToInt();
        listFlatMap();
        listFlatMapToInt();
        listDistinct();
        listSorted();
        listSortedComparator();
        listPeek();
        listLimit();
        listSkip();
        listForEachOrdered();
        listToArray();
        listToArrayFunction();
        listReduce();
        listReduceOptional();
        listReduceU();
        listCollect();
        listMinAndMax();
        retainAll();
        removeAll();
        addAll();
        merge();*/
    }
}
