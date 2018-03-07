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
		System.out.println("���췽���ķ���Ȩ�ޣ�"+Modifier.toString(cl.getModifiers()));
		System.out.println(cl.getClass());
		
		Field field[]=cl.getDeclaredFields();//���ص���˽�е�����Declared
		//Field field[]=cl.getFields();		   //���ص��ǹ���������
		for (Field field2 : field) {
			System.out.println("��������:"+field2.getType()+"-->����"+field2.getName());
		}
		
		Method method[]=cl.getDeclaredMethods();//���ص������з���
		for (Method method1 : method) {
			System.out.println("����Ȩ�ޣ�"+Modifier.toString(method1.getModifiers())+"������:"+method1.getName());
		}
	}

}
