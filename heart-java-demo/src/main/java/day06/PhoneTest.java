package day06;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class PhoneTest {
	public static void main(String[] args) throws Exception {
		Class myClass=Class.forName("day06.Reflect");//根据类型的名称，得到对应的类型
		Constructor consturctor1[]=myClass.getConstructors();//返回类声明的所有构造方法
		Constructor constructor=myClass.getDeclaredConstructor(String.class ,int.class );//返回类声明的私有的构造方法
		constructor.setAccessible(true);//对私有的构造方法进行可视化操作
		Reflect ref=(Reflect)constructor.newInstance("LZL",23);
		System.out.println(constructor.toString());
		System.out.println(constructor.getModifiers());
		System.out.println(Modifier.toString(constructor.getModifiers()));
		System.out.println(constructor.getName());
		
		System.out.println("------------------------");
		Field filed[]=myClass.getDeclaredFields();
		for (Field field : filed) {
			System.out.println(field.getName());
			System.out.println(field.getType());
		}
	}
}
