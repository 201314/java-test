package com.gitee.linzl.lambda.grammar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestLambdaParam {
    public static void noMethodParam() {
        // java.lang.Runnable是函数式接口
        new Thread(() -> System.out.println("hello, i am thread!")).start();

        // 等同于
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello, i am thread!");
            }
        }).start();
    }

    public static void hasMethodSingleParam() {
        // 当只有一个参数的时候，括号可以省略
        FunctionalInterfaceTest.testOneParam((x) -> System.out.println(x));
        FunctionalInterfaceTest.testOneParam(x -> System.out.println(x));
    }

    public static void hasMethodMoreParam() {
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
        System.out.println(studentList);
    }

    public static void main(String[] args) {
        noMethodParam();
        hasMethodSingleParam();
        hasMethodMoreParam();
    }
}
