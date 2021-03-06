package com.gitee.linzl.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class FilesDemo {
	public static void mkParentDir(File file) {
		Path dir = file.toPath();
//		Path dir = Paths.get("D:/data1/logging.properties");
		try {
			if (Files.exists(dir.getParent())) {
				// 要特别注意这种创建方式，父目录必须存在,否则报错
				Files.createDirectory(dir);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void mkdirs(File file) {
		Path dir = file.toPath();
		try {
			// 目录不存在，会先创建
			Files.createDirectories(dir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void touch(File file) {
		// 创建文件
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void mkTempDir(String dirPrefix) {
		try {
			// 生成临时目录，add为最后一个目录的前缀
			Path path = Files.createTempDirectory(dirPrefix);
			System.out.println(path.toFile().getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void copy(File source, File target) {
		Path sPath = source.toPath();
		if (source.isFile()) {
			// 覆盖已有文件
			Path tPath = target.toPath();
			try {
				if (target.isFile()) {// 直接覆盖
					Files.copy(sPath, tPath, StandardCopyOption.REPLACE_EXISTING);
				} else if (target.isDirectory()) {// 复制为同名文件
					Files.createDirectories(tPath);
					Files.copy(sPath, tPath.resolve(tPath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (source.isDirectory()) {// 复制目录

		}
	}

	public static void move(File source, File target) {
		Path moveSource = Paths.get("D:/data1/", "hello1.jpg");
		Path targetSource = Paths.get("D:/data1/", "hello2.jpg");
		try {
			Files.move(moveSource, targetSource, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void listFile() {
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

	public void delete(File file) {
		File[] filePath = file.listFiles();

		for (File file2 : filePath) {
			if (file2.isFile()) {// 是文件就打印出来
				file2.delete();
			} else { // 如果是文件夹，递归继续遍历
				delete(file2);
			}
		}
		file.delete();// 最后把自己也删除
	}

	/**
	 * 递归删除
	 * 
	 * @param file
	 */
	public static void deleteDirectory(File file) {
		// 文件夹必须是空的才能用删除
		Path path = file.toPath();
		try (Stream<Path> stream = Files.list(path);) {
			stream.forEach((f) -> {
				if (Files.isDirectory(f)) {
					deleteDirectory(f.toFile());
				} else {
					try {
						Files.deleteIfExists(f);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void getFileAttributeView() throws IOException {
		// 获取将要操作的文件
		Path testPath = Paths.get("D:\\trawe_store\\trawe_store.zip");
		// 获取访问基本属性的BasicFileAttributeView
		BasicFileAttributeView basicView = Files.getFileAttributeView(testPath, BasicFileAttributeView.class);
		// 获取访问基本属性的BasicFileAttributes
		BasicFileAttributes basicAttribs = basicView.readAttributes();
		// 访问文件的基本属性
		System.out.println("创建时间：" + new Date(basicAttribs.creationTime().toMillis()));
		System.out.println("最后访问时间：" + new Date(basicAttribs.lastAccessTime().toMillis()));
		System.out.println("最后修改时间：" + new Date(basicAttribs.lastModifiedTime().toMillis()));
		System.out.println("文件大小：" + basicAttribs.size());

		// 获取访问文件属主信息的FileOwnerAttributeView
		FileOwnerAttributeView ownerView = Files.getFileAttributeView(testPath, FileOwnerAttributeView.class);
		// 获取该文件所属的用户
		System.out.println("获取该文件所属的用户:" + ownerView.getOwner());
		// 获取系统中guest对应的用户
		UserPrincipal user = FileSystems.getDefault().getUserPrincipalLookupService().lookupPrincipalByName("guest");
		// 修改用户
		ownerView.setOwner(user);
		// 获取访问自定义属性的FileOwnerAttributeView
		UserDefinedFileAttributeView userView = Files.getFileAttributeView(testPath,
				UserDefinedFileAttributeView.class);
		List<String> attrNames = userView.list();
		// 遍历所有的自定义属性
		for (String name : attrNames) {
			ByteBuffer buf = ByteBuffer.allocate(userView.size(name));
			userView.read(name, buf);
			buf.flip();
			String value = Charset.defaultCharset().decode(buf).toString();
			System.out.println(name + "--->" + value);
		}
		// 添加一个自定义属性
		userView.write("发行者", Charset.defaultCharset().encode("疯狂Java联盟"));
		// 获取访问DOS属性的DosFileAttributeView
		DosFileAttributeView dosView = Files.getFileAttributeView(testPath, DosFileAttributeView.class);
		// 将文件设置隐藏、只读
		dosView.setHidden(true);
		dosView.setReadOnly(true);
	}

	public static void getFileStore() {
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

	public static void isTest() {
		Path path = Paths.get("D:\\trawe_store\\trawe_store.zip");
		try {
			System.out.println("是否为目录:" + Files.isDirectory(path));
			System.out.println("是否可执行:" + Files.isExecutable(path));
			System.out.println("是否隐藏:" + Files.isHidden(path));
			System.out.println("是否可读:" + Files.isReadable(path));
			System.out.println("是否为常规文件:" + Files.isRegularFile(path));
			System.out.println("是否为同一文件:" + Files.isSameFile(path, path));
			System.out.println("是否为快捷方式:" + Files.isSymbolicLink(path));
			System.out.println("是否可写:" + Files.isWritable(path));
			System.out.println("文件大小:" + Files.size(path));
			System.out.println("最后修改时间:" + Files.getLastModifiedTime(path));
			UserPrincipal principal = Files.getOwner(path);
			System.out.println("获取该文件所属的用户:" + principal.getName());

			// 只有在linux环境下，获取文件的权限信息
			Set<PosixFilePermission> set = Files.getPosixFilePermissions(path);
			set.stream().forEach((posixFilePermission) -> {
				System.out.println(posixFilePermission.name());
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void lines() {
		Path path = Paths.get("D:\\trawe_store\\第2个接口.txt");
		try {
			Files.lines(path).map((string) -> string.trim()).forEach((str) -> {
				System.out.println("行内容:" + str);
			});

			// 等价于
			Files.lines(path).map(String::trim).forEach((str) -> {
				System.out.println("行内容:" + str);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void list() {
		Path dir = Paths.get("D:\\trawe_store");
		try (Stream<Path> stream = Files.list(dir);) {
			stream.forEach((path) -> {
				if (Files.isDirectory(path)) {
					System.out.println("目录:" + path.toFile());
				} else {
					System.out.println(path.toFile());
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void listFiles(File file) {// 遍历路径,并打印出路径名称
		File[] filePath = file.listFiles();

		for (File file2 : filePath) {
			if (file2.isFile()) {
				// 是文件就打印出来
				System.out.println(file2);
			} else { // 如果是文件夹，递归继续遍历
				System.out.println(file2 + "------------");
				listFiles(file2);
			}
		}
	}

	public static void newBuffer() throws IOException {
		Path path1 = Paths.get("D:\\trawe_store\\第2个接口.txt");
		Path path2 = Paths.get("D:\\trawe_store\\第2个接口---newBuffer.txt");
		try (BufferedReader reader = Files.newBufferedReader(path1);
				BufferedWriter writer = Files.newBufferedWriter(path2);) {
			char[] cbuf = new char[100];
			while (reader.read(cbuf, 0, cbuf.length) > 0) {
				writer.write(cbuf);
			}
			writer.flush();
		}
	}

	public static void newByte() throws IOException {
		Path path1 = Paths.get("D:\\trawe_store\\第2个接口.txt");
		Path path2 = Paths.get("D:\\trawe_store\\第2个接口---newByte.txt");

		try (SeekableByteChannel reader = Files.newByteChannel(path1);

				SeekableByteChannel writer = Files.newByteChannel(path2, StandardOpenOption.CREATE,
						StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);) {

			ByteBuffer dst = ByteBuffer.allocate(100);
			while (reader.read(dst) > 0) {
				dst.flip();
				writer.write(dst);
				dst.clear();
			}
		}
	}

	public static void newDirectory() throws IOException {
		// 等同于Files.list()
		Path path1 = Paths.get("D:\\trawe_store");
		try (DirectoryStream<Path> reader = Files.newDirectoryStream(path1);) {
			reader.forEach((path) -> {
				System.out.println(path.toFile());
			});
		}
	}

	public static void newStream() throws IOException {
		Path path1 = Paths.get("D:\\trawe_store\\第2个接口.txt");
		Path path2 = Paths.get("D:\\trawe_store\\第2个接口---newStream.txt");

		try (InputStream reader = Files.newInputStream(path1);

				OutputStream writer = Files.newOutputStream(path2);) {

			byte[] b = new byte[512];
			while (reader.read(b, 0, b.length) > 0) {
				writer.write(b);
			}
			writer.flush();
		}
	}
}
