package com.gitee.linzl.genericity;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;


/**
 * 泛型方法中的泛型变量
 */
public class Demo2 {

    public <T1, T2 extends Integer, T3 extends Demo2I1 & Demo2I2> T3 m1(T1 t1, T2 t2, T3 t3, String s) {// @3
        return t3;
    }

    private static void print(TypeVariable pt) {
        System.out.println("变量类型名称:" + pt.getTypeName());
        System.out.println("变量名称:" + pt.getName());
        System.out.println("这个变量在哪声明的:" + pt.getGenericDeclaration());
        Type[] bounds = pt.getBounds();
        System.out.println("这个变量上边界数量:" + bounds.length);
        System.out.println("这个变量上边界清单:");
        for (Type bound : bounds) {
            System.out.println(bound.getTypeName());
        }
        System.out.println("--------------------");
    }

    public static void main(String[] args) {
        // 获取Demo2中声明的所有方法
        Method[] methods = Demo2.class.getDeclaredMethods();
        // 找到m1方法
        Method m1 = Arrays.stream(methods).filter(method -> method.getName().equals("m1")).findFirst().get();

        // 获取方法的泛型参数列表
        System.out.println("m1方法参数类型列表信息:----------");
        Type[] genericParameterTypes = m1.getGenericParameterTypes();
        for (Type genericParameterType : genericParameterTypes) {
            // 3个参数都是泛型变量类型的，对应java中的TypeVariable
            if (genericParameterType instanceof TypeVariable) {
                TypeVariable pt = (TypeVariable) genericParameterType;
                print(pt);
            } else if (genericParameterType instanceof Class) {
                Class pt = (Class) genericParameterType;
                System.out.println("参数类型名称:" + pt.getTypeName());
                System.out.println("参数类名:" + pt.getName());
            }
        }

        // 获取方法的返回值，也是一个泛型变量
        System.out.println("m1方法返回值类型信息:----------");
        Type genericReturnType = m1.getGenericReturnType();
        if (genericReturnType instanceof TypeVariable) {
            TypeVariable pt = (TypeVariable) genericReturnType;
            print(pt);
        }

        // 获取方法中声明的泛型参数列表
        System.out.println("m1方法中声明的泛型变量类型列表:----------");
        TypeVariable<Method>[] typeParameters = m1.getTypeParameters();
        for (TypeVariable<Method> pt : typeParameters) {
            print(pt);
        }
    }
}

interface Demo2I1 { // @1
}

interface Demo2I2 { // @2
}
