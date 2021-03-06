package day08.reflex.util;

import day08.reflex.Husband;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.ProtectionDomain;

/**
 * TODO 参考 org.springframework.util.ReflectionUtils 写一个工具类
 * <p>
 * TODO WildcardType \ GenericArrayType \ TypeVariable \ ParameterizedType 了解
 *
 * @author linzl
 * @description
 * @email 2225010489@qq.com
 * @date 2019年2月25日
 */
public class ClassUtil {

    public static void main(String[] args) throws Exception {
        // 加载类文件，并初始化，true表示需要初始化 loading（装载），linking（连接）和initializing（实例化）
        String str = Husband.class.getName();
        Class demo = Class.forName(str);
        // 实例化，Husband必须要有无参的构造方法
        Husband husband = (Husband) demo.newInstance();

        // 从子类到父类，返回所有公共内部类
        Class[] innerPublicClass = demo.getClasses();
        for (int i = 0; i < innerPublicClass.length; i++) {
            System.out.println("从子类到父类，返回所有公共内部类名称-->" + innerPublicClass[i]);
        }
        // 返回该类 声明的所有implements接口 PS: A implements B,C 则返回 B,C
        Class[] directTypeClass = demo.getInterfaces();
        for (int i = 0; i < directTypeClass.length; i++) {
            System.out.println("类声明的所有implements接口名称-->" + directTypeClass[i].getName());
        }

        // 返回该类声明extends的父类 PS: A extends B,返回B
        Class parentClass = demo.getSuperclass();
        System.out.println("类的父类名称-->" + parentClass.getName());

        // 返回该类中声明的内部类
        Class[] excludesExtendsMember = demo.getDeclaredClasses();
        for (int i = 0; i < excludesExtendsMember.length; i++) {
            System.out.println("返回该类中声明的内部类名称-->" + excludesExtendsMember[i].getName());
        }
        //
        Class targetF = demo.getDeclaringClass();
        Class targetS = demo.getEnclosingClass();

        // 获取类加载器
        ClassLoader cl = demo.getClassLoader();
        System.out.println("ClassLoader-->" + cl); // AppClassLoader
        System.out.println("ClassLoader parent-->" + cl.getParent()); // ExtClassLoader
        System.out.println(cl.getParent().getParent()); // Bootstrap ClassLoader比较特殊，因为它不是java class所以Extension
        // ClassLoader的getParent方法返回的是NULL。

        // 返回对应的类，接口，基本类型等等的包+类名称
        demo.getName();
        // 返回类名称
        demo.getSimpleName();

        // 返回类所在的包目录
        Package p = demo.getPackage();
        System.out.println("返回类所在的包目录-->" + p.getName());
        // 返回受保护的属性
        ProtectionDomain domain = demo.getProtectionDomain();

        // 返回该类的直接实现类和 返回该类的所有接口,类型不确定
        Type[] types = demo.getGenericInterfaces();
        // 返回该类的直接父类,类型不确定
        Type type = demo.getGenericSuperclass();

        demo.getTypeParameters();
        // System.out.println(Integer.TYPE); int类型
        // System.out.println(Integer.class); Integer类型

        // 判断该类是否为数组类型
        demo.isArray();
        // 判断是否为枚举类型
        demo.isEnum();
        // 判断是为***的实例
        demo.isInstance("");
        // 判断是否为接口
        demo.isInterface();
        // 判断是否为本地类
        demo.isLocalClass();
        // 判断是否该类是否为内部类
        demo.isMemberClass();
        // 判断是否为基本类型
        demo.isPrimitive();
        // 判断是否为复杂类型
        demo.isSynthetic();
    }

    /**
     * 获取接口上的泛型T
     *
     * @param o     接口
     * @param index 泛型索引
     */
    public static Class<?> getInterfaceT(Object o, int index) {
        Type[] types = o.getClass().getGenericInterfaces();
        ParameterizedType parameterizedType = (ParameterizedType) types[index];
        Type type = parameterizedType.getActualTypeArguments()[index];
        return checkType(type, index);

    }


    /**
     * 获取类上的泛型T
     *
     * @param o     接口
     * @param index 泛型索引
     */
    public static Class<?> getClassT(Object o, int index) {
        Type type = o.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type actType = parameterizedType.getActualTypeArguments()[index];
            return checkType(actType, index);
        }
        String className = type == null ? "null" : type.getClass().getName();
        throw new IllegalArgumentException("Expected a Class, ParameterizedType"
                + ", but <" + type + "> is of type " + className);
    }

    private static Class<?> checkType(Type type, int index) {
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        }
        // 获取类上的泛型T
        if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            Type t = pt.getActualTypeArguments()[index];
            return checkType(t, index);
        }

        String className = type == null ? "null" : type.getClass().getName();
        throw new IllegalArgumentException("Expected a Class, ParameterizedType"
                + ", but <" + type + "> is of type " + className);
    }
}
