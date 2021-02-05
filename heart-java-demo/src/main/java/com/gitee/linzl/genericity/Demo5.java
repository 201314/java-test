package com.gitee.linzl.genericity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Demo5<T1, T2> { // @1
    public void m1(Demo5<T1, T2> demo) { // @2
        // demo6Class对应的是Demo6的Class对象
        Class<? extends Demo5> demoClass = demo.getClass();
        // 获取Demo6的父类的详细类型信息，包含泛型信息
        Type genericSuperclass = demoClass.getGenericSuperclass();
        // 泛型类型用ParameterizedType接口表示，输出看一下是不是这个接口类型的
        System.out.println(genericSuperclass.getClass());
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) genericSuperclass;
            System.out.println("原始类型：" + pt.getRawType());
            System.out.println("所属的类型:" + pt.getOwnerType());
            Type[] actualTypeArguments = pt.getActualTypeArguments();
            for (Type actualTypeArgument : actualTypeArguments) {
                System.out.println(actualTypeArgument.getTypeName());
            }
        }
    }

    public static void main(String[] args) {
        Demo5<String, Integer> demo5 = new Demo5<String, Integer>() {
        };// @3
        demo5.m1(demo5);
    }
}