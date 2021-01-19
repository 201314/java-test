package com.gitee.linzl.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ReflectPermission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 参照spring 的 ReflectionUtils
 * <p>
 * 含有 Declared 的方法就是获取当前类所有属性或方法，否则只获取当前类及其父类的公共属性或方法
 *
 * @author linzl
 * @description
 * @email 2225010489@qq.com
 * @date 2019年2月26日
 */
public class ReflectionUtils {
    public static Field[] findDeclaredFields(Class<?> clazz) {
        return findDeclaredFields(clazz, false);
    }

    public static Field[] findDeclaredFields(Class<?> clazz, boolean recursion) {
        Field[] result = clazz.getDeclaredFields();
        if (recursion) {
            Field[] superFields = clazz.getSuperclass().getDeclaredFields();
            // 数组扩容
            result = Arrays.copyOf(result, result.length + superFields.length);
            System.arraycopy(superFields, 0, result, result.length, superFields.length);
        }
        return result;
    }

    public static Field[] findDeclaredFields(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        List<Field> list = new ArrayList<>();

        Field[] result = findDeclaredFields(clazz);
        for (Field field : result) {
            if (Objects.isNull(annotationClass)
                    || field.isAnnotationPresent(annotationClass)) {
                field.setAccessible(true);
                list.add(field);
            }
        }
        return list.toArray(new Field[0]);
    }

    public static Field findDeclaredField(Class<?> clazz, String name) {
        return findDeclaredField(clazz, name, null);
    }

    public static Field findDeclaredField(Class<?> clazz, String name, Class<? extends Annotation> annotationClass) {
        Field[] fields = findDeclaredFields(clazz);
        for (Field field : fields) {
            if (field.getName().equals(name)
                    && (Objects.isNull(annotationClass)
                    || field.isAnnotationPresent(annotationClass))) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }


    /**
     * 给属性赋值
     *
     * @param field
     * @param target
     * @param value
     */
    public static void setField(Field field, Object target, Object value) {
        try {
            field.setAccessible(true);
            field.set(target, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取属性值
     *
     * @param field
     * @param target
     * @return
     */
    public static Object getField(Field field, Object target) {
        try {
            field.setAccessible(true);
            return field.get(target);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Method[] findDeclaredMethods(Class<?> clazz) {
        return findDeclaredMethods(clazz, false);
    }

    public static Method[] findDeclaredMethods(Class<?> clazz, boolean recursion) {
        Method[] result = clazz.getDeclaredMethods();
        if (recursion) {
            Method[] superMethods = clazz.getSuperclass().getDeclaredMethods();
            // 数组扩容
            result = Arrays.copyOf(result, result.length + superMethods.length);
            System.arraycopy(superMethods, 0, result, result.length, superMethods.length);
        }
        return result;
    }

    public static Method[] findDeclaredMethods(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        List<Method> list = new ArrayList<>();

        Method[] methods = findDeclaredMethods(clazz);
        for (Method method : methods) {
            if (Objects.nonNull(annotationClass) && method.isAnnotationPresent(annotationClass)) {
                list.add(method);
            }
        }

        return list.toArray(new Method[0]);
    }

    public static Method findDeclaredMethod(Class<?> clazz, String name) {
        return findDeclaredMethod(clazz, name, new Class[0]);
    }

    public static Method findDeclaredMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
        Method[] methods = findDeclaredMethods(clazz);
        for (Method method : methods) {
            if (method.getName().equals(name)
                    && (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
                return method;
            }
        }
        return null;
    }

    public static Method findDeclaredMethod(Class<?> clazz, String name, Class<? extends Annotation> annotationClass) {
        return findDeclaredMethod(clazz, name, annotationClass, null);
    }

    public static Method findDeclaredMethod(Class<?> clazz, String name, Class<? extends Annotation> annotationClass,
                                            Class<?>... paramTypes) {
        Method[] methods = findDeclaredMethods(clazz);
        for (Method method : methods) {
            if (method.getName().equals(name)
                    && method.isAnnotationPresent(annotationClass)
                    && (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
                return method;
            }
        }
        return null;
    }

    public static Object invokeMethod(Method method, Object target, Object... args) {
        try {
            return method.invoke(target, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Checks whether can control member accessible.
     *
     * @return If can control member accessible, it return {@literal true}
     * @since 3.5.0
     */
    public static boolean canControlMemberAccessible() {
        try {
            SecurityManager securityManager = System.getSecurityManager();
            if (null != securityManager) {
                securityManager.checkPermission(new ReflectPermission("suppressAccessChecks"));
            }
        } catch (SecurityException e) {
            return false;
        }
        return true;
    }

}
