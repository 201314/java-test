package com.gitee.linzl.classloader.hotload;

/**
 * BaseManager的子类，此类需要实现java类的热加载功能
 */
public class MyManager implements BaseManager {
	@Override
	public void logic() {
		System.out.println("==我在慕课网学习呢，我在慕课网上学习了Spring Boot热部署这门课程");
		System.out.println("==慕课网学习资源很丰富，师资很强大，学习的人很多");
	}
}
