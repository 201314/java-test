package day11.nio.files;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;

public class FileStoreDemo {

	public static void main(String[] args) {
		Path path = Paths.get("D:\\trawe_store\\trawe_store.zip");
		try {
			FileStore store = Files.getFileStore(path);
			System.out.println("盘符名称:" + store.name());
			System.out.println("所在盘的格式类型:" + store.type());
			System.out.println("所在盘是否为只读:" + store.isReadOnly());
			System.out.println("所在盘大小G:" + store.getTotalSpace() / (1024 * 1024 * 1024));
			System.out.println("未分配的空间G:" + store.getUnallocatedSpace() / (1024 * 1024 * 1024));
			System.out.println("剩余可用空间G:" + store.getUsableSpace() / (1024 * 1024 * 1024));
			System.out.println("支持的文件属性:" + store.supportsFileAttributeView(BasicFileAttributeView.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
