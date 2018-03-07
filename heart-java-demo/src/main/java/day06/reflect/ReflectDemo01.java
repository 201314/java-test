package day06.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectDemo01 {
	
	public static void main(String[] args) throws Exception {
		Person per1=new Person("lzl",23);
		System.out.println(per1);
		
		System.out.println(per1.getClass());
		System.out.println();
		
		Class cl=Class.forName("day06.reflect.Person");
		System.out.println(cl.getName());
		System.out.println(cl.getModifiers());
		System.out.println("构造方法的访问权限："+Modifier.toString(cl.getModifiers()));
		System.out.println(cl.getClass());
		
		Field field[]=cl.getDeclaredFields();//返回的是私有的属性Declared
		//Field field[]=cl.getFields();		   //返回的是公开的属性
		for (Field field2 : field) {
			System.out.println("声明类型:"+field2.getType()+"-->名称"+field2.getName());
		}
		
		Method method[]=cl.getDeclaredMethods();//返回的是所有方法
		for (Method method1 : method) {
			System.out.println("访问权限："+Modifier.toString(method1.getModifiers())+"方法名:"+method1.getName());
		}
	}

}
