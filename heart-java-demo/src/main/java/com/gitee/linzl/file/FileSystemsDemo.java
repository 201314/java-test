package com.gitee.linzl.file;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.Set;

public class FileSystemsDemo {
	public static void getSeparator() throws Exception {
		// 关闭文件系统关联的所有东西，但是注意，默认文件系统不能关闭
		// FileSystems.getDefault().close();
		FileSystemProvider provider = FileSystems.getDefault().provider();
		System.out.println("provider:" + provider.getScheme());
		System.out.println("isReadOnly:" + FileSystems.getDefault().isReadOnly());
		System.out.println("isOpen:" + FileSystems.getDefault().isOpen());
		System.out.println("Separator:" + FileSystems.getDefault().getSeparator());
	}

	/**
	 * 获取磁盘的根目录
	 */
	public static void getRootDirectories() {
		Iterable<Path> iter = FileSystems.getDefault().getRootDirectories();
		iter.forEach((path) -> {
			System.out.println(path.toFile().getPath());
		});
	}

	public static void getFileStores() {
		Iterable<FileStore> iter = FileSystems.getDefault().getFileStores();
		iter.forEach((fileStore) -> {
			try {
				// 如果磁盘没有重命名，即为原来的“本地磁盘”,是不会打印出名字来的
				System.out.println(fileStore.name() + ":" + fileStore.getTotalSpace());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public static void supportedFileAttributeViews() {
		Set<String> set = FileSystems.getDefault().supportedFileAttributeViews();
		set.forEach((str) -> {
			System.out.println(str);
		});
	}

	public static void getPath() {
		Path path = FileSystems.getDefault().getPath("D://trawe_store", "一张图片.txt");
		System.out.println(path.toFile().getPath());
	}

	public static void getPathMatcher() {
		PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("*.java");
		// 过滤文件规则使用
	}

	public static void getUserPrincipalLookupService() throws IOException, InterruptedException {
		// 获取用户权限
		UserPrincipalLookupService service = FileSystems.getDefault().getUserPrincipalLookupService();
		// 查询对应的用户，设置文件所有者
		Files.setOwner(Paths.get("D://trawe_store//112222.txt"), service.lookupPrincipalByName("SYSTEM"));
	}

	public void watchFile() throws IOException, InterruptedException {
		// 获取文件系统的WatchService对象
		WatchService service = FileSystems.getDefault().newWatchService();
		// 为C:盘根路径注册监听
		Paths.get("C:/").register(service, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.ENTRY_DELETE);
		while (true) {
			// 获取下一个文件改动事件
			WatchKey key = service.take(); //
			for (WatchEvent<?> event : key.pollEvents()) {
				System.out.println(event.context() + " 文件发生了 " + event.kind() + "事件！");
			}
			// 重设WatchKey
			boolean valid = key.reset();
			// 如果重设失败，退出监听
			if (!valid) {
				break;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		getSeparator();
	}
}
