package day08.reflex.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import day08.reflex.Husband;

public class FieldUtil {
	public static void main(String[] args) throws Exception {
		// 加载类文件，并初始化，true表示需要初始化 loading（装载），linking（连接）和initializing（实例化）
		String str = Husband.class.getName();
		Class demo = Class.forName(str);

		// 返回该类声明的属性
		Field[] excludesExtendsField = demo.getDeclaredFields();
		for (int i = 0; i < excludesExtendsField.length; i++) {
			System.out.println("返回该类声明的属性-->" + excludesExtendsField[i]);
			System.out.println(excludesExtendsField[i].getType());
			System.out.println(
					((ParameterizedType) excludesExtendsField[i].getGenericType()).getActualTypeArguments()[0]);
			// FieldMap filedMap = excludesExtendsField[i].getAnnotation(FieldMap.class);
			// System.out.println("属性注解列名-->"+filedMap.columnName());
			// System.out.println("属性注解列名-->"+filedMap.fieldClass().getName());
		}

		// 返回该类声明的指定名称的属性
		// Field assignField = demo.getDeclaredField("age");
		// System.out.println("返回该类声明的指定名称的属性--->"+assignField.getName());

		// 从子类到父类，返回指定名称的公共属性;如果都没有，则返回该类的直接接口的公共属性
		// Field assignPublicField = demo.getField("firstName");
		// System.out.println("从子类到父类，返回指定名称的公共属性;如果都没有，则返回该类的直接接口的公共属性-->"+assignPublicField.getName());

		// 从子类一直找到父类，返回所有的公共属性
		// Field[] allPublicFields = demo.getFields();
		// for (int i = 0; i < allPublicFields.length; i++) {
		// System.out.println("从子类一直找到父类，返回所有的公共属性-->"+allPublicFields[i]);
		// }
	}
}
