package com.gitee.linzl.lambda.method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestUseMethod {
    public static void testUseMethod1() {
        List<String> strLst = new ArrayList<String>() {
            {
                add("adfkjsdkfjdskjfkds");
                add("asdfasdfafgfgf");
                add("public static void main");
            }
        };
        // 类::实例方法
        // String::compareToIgnoreCase)等同于(x,y)->x.compareToIgnoreCase(y)
        Collections.sort(strLst, String::compareToIgnoreCase);
        // (1)类::实例方法
        // (2)类::静态方法
        // (3)对象::实例方法
        // 方法引用还可以使用this::methodName及super::methodName表示该对象或者其父类对象中的方法

        // System.out::println 等同于(x)->System.out.println(x),
        // Math::pow 等同于(x,y)->Math.pow(x,y)
        System.out.println(strLst);
    }

    public static void main(String[] args) {
        testUseMethod1();
    }
}
