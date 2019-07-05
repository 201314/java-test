package day08.reflex.util;

import java.lang.reflect.Method;

import day08.reflex.Husband;

public class MethodUtil {
	public static void main(String[] args) throws Exception {
		// 加载类文件，并初始化，true表示需要初始化 loading（装载），linking（连接）和initializing（实例化）
		String str = Husband.class.getName();
		Class demo = Class.forName(str);

		// 返回该类声明的所有方法，不包括继承的方法
//		Method[] excludesExtendsMethod = demo.getDeclaredMethods();
//		for (int i = 0; i < excludesExtendsMethod.length; i++) {
//			System.out.println("返回该类声明的所有方法-->" + excludesExtendsMethod[i]);
//		}

		// 返回该类声明的指定类型的方法
		Class[] methodParam = new Class[] { String.class, Integer.TYPE, Double.TYPE };
		Method assignMethod = demo.getDeclaredMethod("sayThreeParam", methodParam);
		System.out.println("返回该类声明的指定类型的方法--->" + assignMethod.getName());

		// 返回该类包括继承下来的指定名称的公共方法
		Class[] assignPublicParam = new Class[] { String.class };
		Method assignPublicMethod = demo.getMethod("FirstAbstractMethod", assignPublicParam);
		System.out.println("返回该类包括继承下来的指定名称的公共方法--->" + assignPublicMethod.getName());
		// 返回该类包括继承下来的所有公共方法
//		Method[] allPublicMethods = demo.getMethods();
//		for (int i = 0; i < allPublicMethods.length; i++) {
//			System.out.println("返回该类包括继承下来的所有公共方法-->" + allPublicMethods[i]);
//		}

		System.out.println(assignPublicMethod.getGenericReturnType().getTypeName());
	}
}
