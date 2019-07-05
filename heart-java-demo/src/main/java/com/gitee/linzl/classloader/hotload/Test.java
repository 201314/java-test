package com.gitee.linzl.classloader.hotload;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Objects;

public class Test {
	// 要加载的类的classpath
	public static final String DIY_CLASS_PATH = "D:\\fighting-java-test-workspaces\\heart-java-demo";

	public static void main(String[] args) throws IOException {
		WatchService watcher = FileSystems.getDefault().newWatchService();
		Paths.get(DIY_CLASS_PATH).register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

		// 该路径是可以从class字节文件中获取的，具体查看如何使用JAVA解析class字节码
		String packageName = "com.gitee.linzl.classloader.hotload.";

		// 启动一条线程不断刷新重新加载实现热加载的类
		new Thread(() -> {
			while (true) {
				String className = null;
				// 尝试获取监控池的变化，如果没有则一直等待
				try {
					WatchKey watchKey = watcher.take();
					for (WatchEvent<?> event : watchKey.pollEvents()) {
						if (StandardWatchEventKinds.ENTRY_CREATE == event.kind()) {
							className = event.context().toString();
							System.out.println("创建：[" + DIY_CLASS_PATH + className + "]");
						} else if (StandardWatchEventKinds.ENTRY_MODIFY == event.kind()) {
							className = event.context().toString();
							System.out.println("修改：[" + DIY_CLASS_PATH + className + "]");
						} else if (StandardWatchEventKinds.ENTRY_DELETE == event.kind()) {
							System.out.println("删除：[" + DIY_CLASS_PATH + className + "]");
						}
					}
					watchKey.reset();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (Objects.nonNull(className) && className.endsWith(".class")) {
					MyClassLoader myClassLoader = new MyClassLoader(DIY_CLASS_PATH);

					Class<?> loadClass = null;
					BaseManager manager = null;
					try {
						loadClass = myClassLoader
								.loadClass(packageName + className.substring(0, className.lastIndexOf(".class")));
						manager = (BaseManager) loadClass.getConstructor(new Class[] {}).newInstance(new Object[] {});
					} catch (Exception e) {
						e.printStackTrace();
					}

					manager.logic();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
