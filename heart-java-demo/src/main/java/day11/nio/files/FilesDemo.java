package day11.nio.files;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

public class FilesDemo {

	public static void main(String[] args) {
		Path dir = Paths.get("D:/data1/logging.properties");

		try {
			// 目录不存在，会先创建
			Files.createDirectories(dir);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		boolean pathExists = Files.exists(dir);
		// 判断是否存在
		if (!pathExists) {
			try {// 创建目录,父目录必须存在,否则报错
				Files.createDirectory(dir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Path first = Paths.get("D:/data/IMG_20170910_144224_HDR.jpg");
		Path second = Paths.get("D:/data1/", "hello1.jpg");
		Path third = Paths.get("D:/data1");
		// Path second = Paths.get("D:/data1/hello.jpg");
		if (!Files.exists(second)) {
			try {
				// 覆盖已有文件
				Files.copy(first, second, StandardCopyOption.REPLACE_EXISTING);
				// 复制为同名文件
				Files.copy(first, third.resolve(first.getFileName()), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			// 生成临时目录，add为最后一个目录的前缀
			Path path = Files.createTempDirectory("add");
			System.out.println(path.toFile().getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		Path moveSource = Paths.get("D:/data1/", "hello1.jpg");
		Path targetSource = Paths.get("D:/data1/", "hello2.jpg");
		try {
			Files.move(moveSource, targetSource, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Path tempPath = Paths.get("C:\\Users\\Administrator\\AppData\\Local\\Temp");
		try {
			Files.walkFileTree(tempPath, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					System.out.println(file);
					file.toFile().deleteOnExit();
					// 如果文件占用，会报错
					// Files.delete(first);
					return FileVisitResult.CONTINUE;
				}

			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
