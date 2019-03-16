package com.gitee.linzl.cls;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 参照spring 的 ReflectionUtils
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2019年2月26日
 */
public class ReflectionUtils {
	/**
	 * 从子类一直找到父类，返回所有的public属性
	 * 
	 * @param clazz
	 * @return
	 */
	public static Field[] findPubFields(Class<?> clazz) {
		return clazz.getFields();
	}

	/**
	 * 返回该类声明的所有属性
	 * 
	 * @param clazz
	 * @return
	 */
	public static Field[] findDeclaredFields(Class<?> clazz) {
		return findDeclaredFields(clazz, false);
	}

	/**
	 * 返回该类声明的所有属性
	 * 
	 * @param clazz
	 * @param includeSuper
	 *            是否包括父类的属性
	 * @return
	 */
	public static Field[] findDeclaredFields(Class<?> clazz, boolean includeSuper) {
		Field[] result = clazz.getDeclaredFields();
		if (includeSuper) {
			Field[] superFields = clazz.getSuperclass().getDeclaredFields();
			// 数组扩容
			result = Arrays.copyOf(result, result.length + superFields.length);
			System.arraycopy(superFields, 0, result, result.length, superFields.length);
		}
		return result;
	}

	public static Field findField(Class<?> clazz, String name) {
		return findField(clazz, name, null);
	}

	public static Field findField(Class<?> clazz, String name, Class<? extends Annotation> annotationClass) {
		Field[] fields = findDeclaredFields(clazz, true);
		for (Field field : fields) {
			if (field.getName().equals(name)
					&& (annotationClass == null || field.isAnnotationPresent(annotationClass))) {
				return field;
			}
		}
		return null;
	}

	public Field[] findFields(Class<?> clazz, Class<? extends Annotation> annotationClass) {
		return findFields(clazz, annotationClass, true);
	}

	public Field[] findFields(Class<?> clazz, Class<? extends Annotation> annotationClass, boolean includeSuper) {
		List<Field> list = new ArrayList<>();

		Class<?> searchType = clazz;
		while (searchType != null) {
			Field[] result = clazz.getDeclaredFields();
			for (Field field : result) {
				if (field.isAnnotationPresent(annotationClass)) {
					list.add(field);
				}
			}
			searchType = includeSuper ? clazz.getSuperclass() : null;
		}
		return list.toArray(new Field[0]);
	}

	public void setField(Field field, Object target, Object value) {
		try {
			field.setAccessible(true);
			field.set(target, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public Object getField(Field field, Object target) {
		try {
			field.setAccessible(true);
			return field.get(target);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 返回该类包括继承下来的所有公共public方法
	 * 
	 * @param clazz
	 */
	public Method[] findPubMethods(Class<?> clazz) {
		return clazz.getMethods();
	}

	public Method[] findDeclaredMethods(Class<?> clazz) {
		return findDeclaredMethods(clazz, false);
	}

	public static Method[] findDeclaredMethods(Class<?> clazz, boolean includeSuper) {
		Method[] result = clazz.getDeclaredMethods();
		if (includeSuper) {
			Method[] superMethods = clazz.getSuperclass().getDeclaredMethods();
			// 数组扩容
			result = Arrays.copyOf(result, result.length + superMethods.length);
			System.arraycopy(superMethods, 0, result, result.length, superMethods.length);
		}
		return result;
	}

	public static Method findMethod(Class<?> clazz, String name) {
		return findMethod(clazz, name, new Class[0]);
	}

	public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
		Method[] methods = findDeclaredMethods(clazz, true);
		for (Method method : methods) {
			if (method.getName().equals(name)
					&& (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
				return method;
			}
		}
		return null;
	}

	public static Method findMethod(Class<?> clazz, String name, Class<? extends Annotation> annotationClass) {
		return findMethod(clazz, name, annotationClass, new Class[0]);
	}

	public static Method findMethod(Class<?> clazz, String name, Class<? extends Annotation> annotationClass,
			Class<?>... paramTypes) {
		Method[] methods = findDeclaredMethods(clazz, true);
		for (Method method : methods) {
			if (method.getName().equals(name) && method.isAnnotationPresent(annotationClass)
					&& (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
				return method;
			}
		}
		return null;
	}

	public Method[] findMethod(Class<?> clazz, Class<? extends Annotation> annotationClass) {
		return findMethod(clazz, annotationClass, true);
	}

	public Method[] findMethod(Class<?> clazz, Class<? extends Annotation> annotationClass, boolean includeSuper) {
		List<Method> list = new ArrayList<>();

		Class<?> searchType = clazz;
		while (searchType != null) {
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method : methods) {
				if (method.isAnnotationPresent(annotationClass)) {
					list.add(method);
				}
			}
			searchType = includeSuper ? clazz.getSuperclass() : null;
		}
		return list.toArray(new Method[0]);
	}

	public static Object invokeMethod(Method method, Object target, Object... args) {
		try {
			return method.invoke(target, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
