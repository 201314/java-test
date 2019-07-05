package com.gitee.linzl.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * 反射是在运行状态把Java类中的各种成分映射成相应的Java类，可以动态的获取所有的属性以及动态调用任意一个方法，强调的是运行状态。        
 * 内省(IntroSpector)是Java 语言对 Bean 类属性、事件的一种缺省处理方法。
 * 
 * JavaBean是一种特殊的类，主要用于传递数据信息，这种类中的方法主要用于访问私有的字段，且方法名符合某种命名规则。
 * 
 * 如果在两个模块之间传递信息，可以将信息封装进JavaBean中，这种对象称为“值对象”(Value
 * Object)，或“VO”。方法比较少。这些信息储存在类的私有变量中，通过set()、get()获得。
 * 
 * 内省机制是通过反射来实现的，BeanInfo用来暴露一个bean的属性、方法和事件，以后我们就可以操纵该JavaBean的属性。
 * 
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年10月14日
 */
public class IntrospectorDemo {
	public static void main(String[] args) {
		User user = new User();
		user.setAge(11);
		user.setAddress("中国广州天河");
		user.setCPU("我是中国芯");
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(user.getClass());
			methodDescriptor(user, beanInfo);
			propertyDescriptor(user, beanInfo);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
	}

	private static void propertyDescriptor(User user, BeanInfo beanInfo) {
		PropertyDescriptor[] property = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor propertyDescriptor : property) {
			try {
				Object obj = propertyDescriptor.getReadMethod().invoke(user);
				System.out.println("属性类型：" + propertyDescriptor.getPropertyType().getSimpleName() + ",属性名："
						+ propertyDescriptor.getName() + ",属性值：" + obj);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
			System.out.print("读操作方法：" + propertyDescriptor.getReadMethod().getName());
			if (Objects.nonNull(propertyDescriptor.getWriteMethod())) {
				System.out.print(",写操作方法：" + propertyDescriptor.getWriteMethod().getName());
			}
			System.out.println();
		}
	}

	private static void methodDescriptor(User user, BeanInfo beanInfo) {
		MethodDescriptor[] method = beanInfo.getMethodDescriptors();
		for (MethodDescriptor methodDescriptor : method) {
			System.out.println("方法名:" + methodDescriptor.getName() + ",方法:" + methodDescriptor.getMethod());
			if ("getAddress".equals(methodDescriptor.getName())) {
				try {
					String address = (String) methodDescriptor.getMethod().invoke(user);
					System.out.println("地址为:" + address);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			// System.out.println("描述:" + methodDescriptor.getParameterDescriptors());
		}
	}
}
