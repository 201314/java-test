package day06;

import java.lang.reflect.Method;

public class Methodof {
	public static void main(String[] args) throws Exception {
		Class myClass=Class.forName("day06.Reflect");//根据类型的名称，得到对应的类型
		Object instance=myClass.newInstance();//创建指定类型的实例，自动调用无参构造方法
//		Method method=myClass.getMethod("printh", String.class);//得到指定类型的指定方法
//		//System.out.println(int.class);
//		method.invoke(instance,"30");//通过实例和传参执行指定方法
		
		Reflect ref=(Reflect)instance;
		
		Method method1=myClass.getMethod("setName",String.class);//得到指定类型的指定方法
		//Method method2=myClass.getMethod("say");//得到指定类型的指定方法
		
		method1.invoke(instance,"hello");
		
		System.out.println(ref.getName());	
		//System.out.println(instance.getClass().getName());
		
		ref.say();
		//method2.invoke(instance);
		System.out.println(myClass.getSimpleName());
	}
}
