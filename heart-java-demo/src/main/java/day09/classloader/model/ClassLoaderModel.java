package day09.classloader.model;

public class ClassLoaderModel {
	private ClassLoaderModel instance;

	public void run(Object obj) {
		System.out.println("输出Test");
		this.instance = (ClassLoaderModel) obj;
		System.out.println("强转完毕Test");
	}

	public void say() {
		System.out.println("hello world");
	}

}
