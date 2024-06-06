package com.gitee.linzl.lambda.collection;

import com.gitee.linzl.lambda.constructor.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author linzhenlie
 * @date 2020-04-24
 */
public class MapDemo {
    public void test1() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "Java");
        map.put(2, "Kotlin");
        map.put(3, "React");
        map.put(4, "Python");
        map.put(5, "Go");
        // forEach 使用的 BiConsumer 消费函数
        map.forEach((k, v) -> System.out.println(k + ":" + v));
    }


    public static void listToMap() {
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setName("linzl");
        student.setScore(100);
        students.add(student);

        Student student2 = new Student();
        student2.setName("linzl2");
        student2.setScore(null);
        students.add(student2);

        // map的key重复 会报Duplicate key ,可以把Function.identity() 换成 c->c,(k1, k2)->k2)避免
        //Map<String, Student> res =  students.stream().collect(Collectors.toMap(Student::getName,Function.identity()));
        Map<String, Student> res = students.stream()
                .collect(Collectors.toMap(Student::getName, c -> c, (k1, k2) -> k2));

        // 如果key,value可能为null时需要判空
        Map<String, Integer> res2 = students.stream()
                .collect(Collectors.toMap(stu -> Optional.ofNullable(stu).map(Student::getName).orElse(""),
                        stu -> Optional.ofNullable(stu).map(Student::getScore).orElse(0), (key1, key2) -> key2));

        System.out.println(res2);
    }

    public static void sortByValue() {
        Map<String, Integer> map = new TreeMap<String, Integer>();
        map.put("a", 1);
        map.put("d", 2);
        map.put("b", 3);
        map.put("c", 0);

        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, (o1, o2) -> {
            return o1.getValue().compareTo(o2.getValue());
        });

        for (Map.Entry<String, Integer> e : list) {
            System.out.println(e.getKey() + ":" + e.getValue());
        }
    }

    public static void main(String[] args) {
        sortByValue();
    }
}
