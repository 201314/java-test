package day08;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class ConstructorUtil {
	public static void main(String[] args) throws Exception {
		// 加载类文件，并初始化，true表示需要初始化 loading（装载），linking（连接）和initializing（实例化）
		String str = Husband.class.getName();
		Class demo = Class.forName(str);

		// 获取该类public构造方法
		Constructor cs[] = demo.getConstructors();
		for (int i = 0; i < cs.length; i++) {
			System.out.println("获取该类public构造方法-->" + cs[i].getName());
		}

		// 获取该类指定参数类型的public构造方法
		Class[] constructorParam = new Class[] { String.class };
		Constructor constructor = demo.getConstructor(constructorParam);
		// hello为构造方法的参数值
		Husband test = (Husband) constructor.newInstance("有1个参数的构造方法");
		System.out.println("有参数的构造方法--》" + test.getName());

		// 返回该类所有构造方法
		Constructor[] declaredConstructor = demo.getDeclaredConstructors();
		for (int i = 0; i < declaredConstructor.length; i++) {
			System.out.println("返回该类所有构造方法-->" + declaredConstructor[i]);
		}

		// 返回指定参数的非私有构造方法
		Class[] constructorDeclaredParam = new Class[] { String.class, Integer.TYPE, String.class };
		Constructor paramConstructor = demo.getDeclaredConstructor(constructorDeclaredParam);
		System.out.println("构造方法类型-->" + Modifier.toString(paramConstructor.getModifiers()));
		Husband testSecond = (Husband) paramConstructor.newInstance("参数1为String", 6000, "第三个参数");
		System.out.println(testSecond.getName() + "-->" + testSecond.getSalary());
	}
}
