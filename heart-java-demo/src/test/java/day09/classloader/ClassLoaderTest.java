package day09.classloader;

import org.junit.Test;

public class ClassLoaderTest {
	@Test
	public void printClassLoder() {
		// 打印java虚拟机的ClassLoader
		System.out.println(Thread.currentThread().getContextClassLoader());
		System.out.println(Thread.currentThread().getContextClassLoader().getParent());
		System.out.println(Thread.currentThread().getContextClassLoader().getParent().getParent());
	}

	@Test
	public void diyClassLoader() throws Exception {
		// 自定义ClassLoader
		ClassLoader myLoader = new MyClassLoader();
		Class<?> clazz = myLoader.loadClass("day09.classloader.ClassLoaderModel");
		Object obj = clazz.newInstance();
		// 打印自定义ClassLoader加载的Class对象
		System.out.println(obj.getClass());

		// 打印被加载的Class对象是由哪个ClassLoader加载的
		System.out.println(obj.getClass().getClassLoader());
	}

	@Test
	public void changeClassLoader() throws Exception {
		// 自定义ClassLoader
		ClassLoader myLoader = new MyClassLoader();
		Class<?> clazz = myLoader.loadClass("day09.classloader.ClassLoaderModel");
		Object obj = clazz.newInstance();
		// 打印自定义ClassLoader加载的Class对象
		System.out.println(obj.getClass());

		// 打印被加载的Class对象是由哪个ClassLoader加载的
		System.out.println(obj.getClass().getClassLoader());
		// 改变上下文的ClassLoader
		Thread.currentThread().setContextClassLoader(myLoader);

		// 获取当前上下文的ClassLoader
		System.out.println(Thread.currentThread().getContextClassLoader());

		// 改变当前上下文的ClassLoader可以改变在当前线程派生出的子线程的上下文ClassLoader
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Thread t2 = Thread.currentThread();
				System.out.println(t2.getName() + ":" + t2.getContextClassLoader());
			}
		});
		t.start();
		Thread.sleep(3000);

	}
}
