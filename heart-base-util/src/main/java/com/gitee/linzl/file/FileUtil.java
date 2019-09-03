package com.gitee.linzl.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.gitee.linzl.file.event.ProgressListener;
import com.gitee.linzl.file.model.SplitFileRequest;
import com.gitee.linzl.file.progress.MergeRunnable;
import com.gitee.linzl.file.progress.SplitRunnable;

/**
 * 文件处理辅助类
 * 
 * @description
 * 
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年5月4日
 */
public class FileUtil {
	/***
	 * 递归获取指定目录下的所有的文件(不包括文件夹)
	 * 
	 * @param obj
	 * @return
	 */
	public static List<File> getAllFiles(File dir) {
		List<File> files = new ArrayList<>();

		if (dir.isDirectory()) {
			File[] fileArr = dir.listFiles();
			if (Objects.nonNull(fileArr)) {
				Arrays.stream(fileArr).forEach((file) -> {
					if (file.isFile()) {
						files.add(file);
					} else {
						files.addAll(getAllFiles(file));
					}
				});
			}
		}
		return Optional.ofNullable(files).orElseGet(ArrayList::new);
	}

	/**
	 * 获取指定目录下的直接文件
	 * 
	 * @param dirPath
	 * @return
	 */
	public static List<File> getDirFiles(File dir) {
		List<File> files = null;
		if (dir.isDirectory()) {
			File[] fileArr = dir.listFiles();
			if (Objects.nonNull(fileArr)) {
				files = Arrays.stream(fileArr).filter(file -> file.isFile()).collect(Collectors.toList());
			}
		}
		return Optional.ofNullable(files).orElseGet(ArrayList::new);
	}

	/**
	 * 获取指定目录下特定文件后缀名的文件列表(不包括子文件夹)
	 * 
	 * @param dirPath 目录路径
	 * @param suffix  文件后缀
	 * @return
	 */
	public static List<File> getDirFiles(String dirPath, final String suffix) {
		File path = new File(dirPath);
		File[] fileArr = path.listFiles((File dir, String name) -> {
			String lowerName = name.toLowerCase();
			String lowerSuffix = suffix.toLowerCase();
			if (lowerName.endsWith(lowerSuffix)) {
				return true;
			}
			return false;
		});

		List<File> files = null;
		if (Objects.nonNull(fileArr)) {
			files = Arrays.stream(fileArr).filter(file -> file.isFile()).collect(Collectors.toList());
		}
		return Optional.ofNullable(files).orElseGet(ArrayList::new);
	}

	/**
	 * 读取文件内容
	 * 
	 * @param fileName 待读取的完整文件名
	 * @return 文件内容
	 * @throws IOException
	 */
	public byte[] read(File file) throws Exception {
		if (!file.isFile()) {
			throw new Exception("file不是文件");
		}

		byte[] bytes = null;
		try (InputStream fs = new BufferedInputStream(new FileInputStream(file))) {
			bytes = new byte[fs.available()];
			fs.read(bytes);
		}
		return bytes;
	}

	/**
	 * 读取文件的行数
	 * 
	 * @param file
	 * @return
	 */
	public static int readLineNumber(File file) {
		try (LineNumberReader reader = new LineNumberReader(new FileReader(file));) {
			if (Objects.nonNull(file) && file.exists()) {
				long fileLength = file.length();
				reader.skip(fileLength);
				return reader.getLineNumber();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 读取文件最后多少行
	 * 
	 * @param file
	 * @param charset
	 * @param count
	 * @return
	 * @throws Exception
	 */
	public static String readLastLine(File file, Charset charset, int count) throws Exception {
		if (!file.exists() || file.isDirectory() || !file.canRead()) {
			throw new Exception("文件不存在or目标为文件夹or文件不可读");
		}

		try (RandomAccessFile raf = new RandomAccessFile(file, "r");) {
			long len = raf.length();
			if (len == 0L) {
				throw new Exception("文件为空");
			}

			long pos = len - 1;// 文本的最后一位为结束符，所以要减去1
			while (count > 0 && pos > 0) {
				pos--;
				raf.seek(pos);
				// 得到 count 行数据
				if (raf.read() == '\n' || raf.read() == '\r') {
					count--;
				}
			}
			if (pos == 0) {
				raf.seek(0);
			}

			byte[] bytes = new byte[(int) (len - 1 - pos)];// 文本的最后一位为结束符，所以要减去1
			raf.read(bytes);

			if (Objects.isNull(charset)) {
				return new String(bytes);
			}
			return new String(bytes, charset);
		}
	}

	/**
	 * 写文件
	 * 
	 * @param file        目标文件
	 * @param fileContent 写入的内容
	 * @return
	 * @throws Exception
	 */
	public static boolean write(File file, byte[] fileContent) throws Exception {
		return write(file, fileContent, false);
	}

	/**
	 * 写文件
	 * 
	 * @param file        目标文件
	 * @param fileContent 写入的内容
	 * @param append      是否追加在文件末尾
	 * @return
	 * @throws Exception
	 */
	public static boolean write(File file, byte[] fileContent, boolean append) throws Exception {
		if (!file.exists() || !file.isFile()) {
			throw new Exception("file不是文件");
		}

		boolean result = false;
		try (OutputStream fs = new BufferedOutputStream(new FileOutputStream(file, append))) {
			fs.write(fileContent);
			result = true;
		}
		return result;
	}

	/**
	 * 将内容写进文件头,文件内容大的时候不建议这么做
	 * 
	 * @param file
	 * @param fileContent
	 * @return
	 */
	public static boolean writeHead(File file, byte[] fileContent) throws IOException {
		boolean result = false;
		if (file.exists() && file.isFile()) {
			long originLen = file.length();
			byte[] readFile = new byte[(int) originLen];
			try (RandomAccessFile rFile = new RandomAccessFile(file, "rw")) {
				rFile.read(readFile);// 读取所有文件内容
				rFile.seek(0);
				rFile.write(fileContent);
				rFile.write(readFile);
				result = true;
			}
		}
		return result;
	}

	/**
	 * 
	 * @param file     要分片的文件
	 * @param byteSize 分片大小
	 * @throws IOException
	 */
	public static void asynSplitFile(File file, int byteSize, ProgressListener listener) throws IOException {
		long fileSize = file.length();
		int number = (int) (fileSize / byteSize);
		number = (fileSize % byteSize == 0 ? number : number + 1);// 分割后文件的数目

		ExecutorService executor = (ExecutorService) Executors.newCachedThreadPool();
		SplitFileRequest request = null;
		List<Future<Boolean>> completeList = new ArrayList<>();
		for (int index = 0; index < number; index++) {
			request = new SplitFileRequest();
			request.setFile(file);
			request.setPartNum(index);
			request.setPartSize(byteSize);
			request.setListener(listener);
			Future<Boolean> future = executor.submit(new SplitRunnable(request));
			completeList.add(future);
		}

		int totalCompleteNumber = 0;
		Iterator<Future<Boolean>> iter = completeList.iterator();
		while (iter.hasNext()) {
			boolean flag = false;
			try {
				flag = iter.next().get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			if (flag) {
				++totalCompleteNumber;
			}
		}
		completeList.clear();
		if (totalCompleteNumber == number) {
			// 发布分片结束事件
		}
	}

	/**
	 * 异步合并文件,合并完成会删除分片文件
	 * 
	 * @param dirPath        拆分文件所在目录名
	 * @param partFileSuffix 拆分文件后缀名
	 * @param partFileSize   拆分文件的字节数大小
	 * @param mergeFileName  合并后的文件名
	 * @throws IOException
	 */
	public static void asynMergeFiles(String dirPath, String partFileSuffix, int partFileSize, File mergeFile)
			throws IOException {
		List<File> partFiles = getDirFiles(dirPath, partFileSuffix);
		Collections.sort(partFiles, new FileComparator());
		ExecutorService executor = (ExecutorService) Executors.newCachedThreadPool();

		Map<Integer, Future<Boolean>> completeMap = new HashMap<>();
		for (int index = 0, length = partFiles.size(); index < length; index++) {
			Future<Boolean> future = executor
					.submit(new MergeRunnable(index * partFileSize, mergeFile, partFiles.get(index)));
			completeMap.put(index, future);
		}
		Set<Integer> set = completeMap.keySet();
		Iterator<Integer> iter = set.iterator();
		Integer index = 0;
		try {
			while (iter.hasNext()) {
				index = iter.next();
				if (completeMap.get(index).get()) {// 表示已经合并成功，即可删除
					partFiles.get(index).deleteOnExit();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			partFiles.clear();
			completeMap.clear();
		}
	}

	/**
	 * 根据文件名，比较文件
	 * 
	 * @description
	 * @author linzl
	 * @email 2225010489@qq.com
	 * @date 2018年6月26日
	 */
	private static class FileComparator implements Comparator<File> {
		public int compare(File o1, File o2) {
			return o1.getName().compareToIgnoreCase(o2.getName());
		}
	}

	public static long fileSize(File file) {
		try {
			return fileSize(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static long fileSize(FileInputStream file) {
		try {
			FileChannel fc = file.getChannel();
			return fc.size();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void main(String[] args) throws IOException {
		writeHead(new File("D://CZ9700000630000020190117103159.txt"), "我是中国人".getBytes());
	}
}