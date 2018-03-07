package day06.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectDemo02 {
	public static void main(String[] args) throws Exception {
		Class cl=Class.forName("day06.reflect.Person");
		Object obj=cl.newInstance();
		Person per2=(Person)obj;
		Method method1=cl.getMethod("updatePassword", String.class);
	    method1.invoke(per2, "asdf");
	    System.out.println(per2.print());
	    //System.out.println(obj.getClass());
	  	     
	    Constructor[] cont1=cl.getDeclaredConstructors();//返回所有构造方法
	    for (Constructor constructor : cont1) {
			System.out.println("构造方法的访问权限："+Modifier.toString(constructor.getModifiers())+"方法名称:"+constructor.getName());
		}
	    
	    Constructor cont2=cl.getDeclaredConstructor(String.class,int.class,String.class);
	    cont2.setAccessible(true);
	    Person per=(Person)cont2.newInstance("林伟强",22,"男");
	    
	}

}
