package day09.classloader;

import org.junit.Test;

/*
 * 对于任意一个类，由加载它的ClassLoader和它本身决定了在jvm虚拟机中的唯一性。
 * 也就是说比较2个类，只有它们都是由同一个ClassLoader加载，那么比较才有意义。
 * 否则，即使是同一个类文件，只要加载它们的ClassLoader不同，那么这2个类必定不相等。
 */
public class ClassLoaderTest2 {

	@Test
	/**
	 * 要测试时，必须先将ClassLoaderModel编译后的class放在对应目录，然后删除ClassLoaderModel再测试
	 */
	public void twoClassLoader() {
		try {
			String className = "day09.classloader.ClassLoaderModel";
			ClassLoader cl1 = new MyClassLoader();
			// 如果day09.classloader包下有ClassLoaderModel,会被AppClassLoader先加载到
			// 则以下测试
			Class<?> clazz1 = cl1.loadClass(className);
			Object obj1 = clazz1.newInstance();

			ClassLoader cl2 = new MyClassLoader2();
			Class<?> clazz2 = cl2.loadClass(className);
			Object obj2 = clazz2.newInstance();

			System.out.println("obj1==" + obj1.getClass().getClassLoader());
			System.out.println("obj2==" + obj2.getClass().getClassLoader());

			// 因为是2个不同的类加载器，所以强转出错
			clazz1.getMethod("run", Object.class).invoke(obj1, obj2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}