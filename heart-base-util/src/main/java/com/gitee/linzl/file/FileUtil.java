package com.gitee.linzl.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.FileUtils;

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

	/**
	 * 当前目录路径
	 */
	public static String currentWorkDir = System.getProperty("user.dir") + "\\";

	/**
	 * 左填充
	 * 
	 * @param str
	 * @param length
	 * @param ch
	 * @return
	 */
	public static String leftPad(String str, int length, char ch) {
		if (str.length() >= length) {
			return str;
		}
		char[] chs = new char[length];
		Arrays.fill(chs, ch);
		char[] src = str.toCharArray();
		System.arraycopy(src, 0, chs, length - src.length, src.length);
		return new String(chs);

	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 *            待删除的完整文件名
	 * @return
	 */
	public static boolean delete(String fileName) {
		boolean result = true;
		File f = new File(fileName);
		if (f.exists()) {
			result = f.delete();
		}
		return result;
	}

	/***
	 * 递归获取指定目录下的所有的文件（不包括文件夹）
	 * 
	 * @param obj
	 * @return
	 */
	public static ArrayList<File> getAllFiles(String dirPath) {
		File dir = new File(dirPath);

		ArrayList<File> files = new ArrayList<File>();

		if (dir.isDirectory()) {
			File[] fileArr = dir.listFiles();
			for (int i = 0; i < fileArr.length; i++) {
				File f = fileArr[i];
				if (f.isFile()) {
					files.add(f);
				} else {
					files.addAll(getAllFiles(f.getPath()));
				}
			}
		}
		return files;
	}

	/**
	 * 获取指定目录下的所有文件(不包括子文件夹)
	 * 
	 * @param dirPath
	 * @return
	 */
	public static ArrayList<File> getDirFiles(String dirPath) {
		File path = new File(dirPath);
		File[] fileArr = path.listFiles();
		ArrayList<File> files = new ArrayList<File>();

		for (File f : fileArr) {
			if (f.isFile()) {
				files.add(f);
			}
		}
		return files;
	}

	/**
	 * 获取指定目录下特定文件后缀名的文件列表(不包括子文件夹)
	 * 
	 * @param dirPath
	 *            目录路径
	 * @param suffix
	 *            文件后缀
	 * @return
	 */
	public static ArrayList<File> getDirFiles(String dirPath, final String suffix) {
		File path = new File(dirPath);
		File[] fileArr = path.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String lowerName = name.toLowerCase();
				String lowerSuffix = suffix.toLowerCase();
				if (lowerName.endsWith(lowerSuffix)) {
					return true;
				}
				return false;
			}

		});
		ArrayList<File> files = new ArrayList<File>();

		for (File f : fileArr) {
			if (f.isFile()) {
				files.add(f);
			}
		}
		return files;
	}

	/**
	 * 读取文件内容
	 * 
	 * @param fileName
	 *            待读取的完整文件名
	 * @return 文件内容
	 * @throws IOException
	 */
	public static String read(String fileName) throws IOException {
		File f = new File(fileName);
		String result = null;
		try (FileInputStream fs = new FileInputStream(f)) {
			byte[] b = new byte[fs.available()];
			fs.read(b);
			result = new String(b);
		}
		return result;
	}

	/**
	 * 写文件
	 * 
	 * @param fileName
	 *            目标文件名
	 * @param fileContent
	 *            写入的内容
	 * @return
	 * @throws IOException
	 */
	public static boolean write(String fileName, String fileContent) throws IOException {
		boolean result = false;
		File f = new File(fileName);
		try (FileOutputStream fs = new FileOutputStream(f)) {
			byte[] b = fileContent.getBytes();
			fs.write(b);
			result = true;
		}
		return result;
	}

	/**
	 * 追加内容到指定文件
	 * 
	 * @param fileName
	 * @param fileContent
	 * @return
	 * @throws IOException
	 */
	public static boolean append(String fileName, String fileContent) throws IOException {
		boolean result = false;
		File f = new File(fileName);
		if (f.exists()) {
			try (RandomAccessFile rFile = new RandomAccessFile(f, "rw");) {
				byte[] b = fileContent.getBytes();
				long originLen = f.length();
				rFile.setLength(originLen + b.length);
				rFile.seek(originLen);
				rFile.write(b);
				result = true;
			}
		}
		return result;
	}

	/**
	 * 拆分文件
	 * 
	 * @param fileName
	 *            待拆分的完整文件名
	 * @param byteSize
	 *            按多少字节大小拆分
	 * @return 拆分后的文件名列表
	 * @throws IOException
	 */
	public List<String> splitBySize(File file, int byteSize) throws IOException {

		int count = (int) Math.ceil(file.length() / (double) byteSize);
		int countLen = String.valueOf(count).length();

		ExecutorService executor = (ExecutorService) Executors.newCachedThreadPool();

		List<String> parts = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			String partFileName = String.format("%s.%s.part", file.getName(),
					leftPad(String.valueOf(i + 1), countLen, '0'));
			executor.submit(new SplitRunnable(byteSize, i * byteSize, partFileName, file));
			parts.add(partFileName);
		}
		return parts;
	}

	/**
	 * 异步合并文件,合并完成会删除分片文件
	 * 
	 * @param dirPath
	 *            拆分文件所在目录名
	 * @param partFileSuffix
	 *            拆分文件后缀名
	 * @param partFileSize
	 *            拆分文件的字节数大小
	 * @param mergeFileName
	 *            合并后的文件名
	 * @throws IOException
	 */
	public void asynMergeFiles(String dirPath, String partFileSuffix, int partFileSize, String mergeFileName)
			throws IOException {
		ArrayList<File> partFiles = FileUtil.getDirFiles(dirPath, partFileSuffix);
		Collections.sort(partFiles, new FileComparator());

		ExecutorService executor = (ExecutorService) Executors.newCachedThreadPool();

		for (int i = 0, length = partFiles.size(); i < length; i++) {
			Future<Boolean> future = executor
					.submit(new MergeRunnable(i * partFileSize, mergeFileName, partFiles.get(i)));
			try {
				if (future.get()) {// 表示已经合并成功，即可删除
					partFiles.get(i).deleteOnExit();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 同步合并文件,合并完成会删除分片文件
	 * 
	 * @param dirPath
	 *            拆分文件所在目录名
	 * @param partFileSuffix
	 *            拆分文件后缀名
	 * @param partFileSize
	 *            拆分文件的字节数大小
	 * @param mergeFileName
	 *            合并后的文件名
	 * @throws IOException
	 */
	public void synMergeFiles(String dirPath, String partFileSuffix, int partFileSize, String mergeFileName)
			throws IOException {
		ArrayList<File> partFiles = FileUtil.getDirFiles(dirPath, partFileSuffix);
		Collections.sort(partFiles, new FileComparator());

		ExecutorService executor = (ExecutorService) Executors.newCachedThreadPool();

		List<MergeRunnable> runList = new ArrayList<>();
		for (int i = 0, length = partFiles.size(); i < length; i++) {
			runList.add(new MergeRunnable(i * partFileSize, mergeFileName, partFiles.get(i)));
		}
		try {
			executor.invokeAll(runList);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
		for (int i = 0, length = partFiles.size(); i < length; i++) {
			partFiles.get(i).deleteOnExit();
		}
	}

	public void synMergeFiles2(String dirPath, String partFileSuffix, int partFileSize, String mergeFileName)
			throws IOException {
		ArrayList<File> partFiles = FileUtil.getDirFiles(dirPath, partFileSuffix);
		Collections.sort(partFiles, new FileComparator());

		ExecutorService executor = (ExecutorService) Executors.newCachedThreadPool();

		List<MergeRunnable> runList = new ArrayList<>();
		for (int i = 1; i < 7; i = i + 2) {
			runList.add(new MergeRunnable(i * partFileSize, mergeFileName,
					FileUtils.readFileToByteArray(partFiles.get(i))));
		}

		for (int i = 0; i < 7; i = i + 2) {
			runList.add(new MergeRunnable(i * partFileSize, mergeFileName,
					FileUtils.readFileToByteArray(partFiles.get(i))));
		}
		try {
			executor.invokeAll(runList);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
	}

	/**
	 * 根据文件名，比较文件
	 * 
	 * @author yjmyzz@126.com
	 *
	 */
	private class FileComparator implements Comparator<File> {
		public int compare(File o1, File o2) {
			return o1.getName().compareToIgnoreCase(o2.getName());
		}
	}

	/**
	 * 分割处理Runnable
	 * 
	 * @author yjmyzz@126.com
	 *
	 */
	private class SplitRunnable implements Runnable {
		int byteSize;
		String partFileName;
		File originFile;
		int startPos;

		public SplitRunnable(int byteSize, int startPos, String partFileName, File originFile) {
			this.startPos = startPos;
			this.byteSize = byteSize;
			this.partFileName = partFileName;
			this.originFile = originFile;
		}

		public void run() {
			try (RandomAccessFile rFile = new RandomAccessFile(originFile, "r");
					OutputStream os = new FileOutputStream(new File(originFile.getParentFile(), partFileName));) {
				byte[] b = new byte[byteSize];
				rFile.seek(startPos);// 移动指针到每“段”开头
				int s = rFile.read(b);
				os.write(b, 0, s);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @description 合并文件
	 * @author linzl
	 * @email 2225010489@qq.com
	 * @date 2018年4月24日
	 */
	private class MergeRunnable implements Callable<Boolean> {
		long startPos;
		String mergeFileName;
		byte[] partFileByte;

		public MergeRunnable(long startPos, String fullMergeFileName, File partFile) {
			this.startPos = startPos;
			this.mergeFileName = fullMergeFileName;
			try {
				this.partFileByte = FileUtils.readFileToByteArray(partFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public MergeRunnable(long startPos, String fullMergeFileName, byte[] partFileByte) {
			this.startPos = startPos;
			this.mergeFileName = fullMergeFileName;
			this.partFileByte = partFileByte;
		}

		public Boolean call() {
			try (RandomAccessFile rFile = new RandomAccessFile(mergeFileName, "rw");) {
				rFile.seek(startPos);
				rFile.write(partFileByte);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
	}

	/**
	 * 对象要实现Serializable接口 <br/>
	 * 将字节转换为对象
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object byteToObj(byte[] bytes) {
		Object obj = null;
		try (ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
				ObjectInputStream oi = new ObjectInputStream(bi);) {
			obj = oi.readObject();
		} catch (Exception e) {
			System.out.println("translation" + e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 对象obj要实现Serializable接口 <br/>
	 * 将对象转换为字节
	 * 
	 * @param obj
	 * @return
	 */
	public static byte[] objToByte(Object obj) {
		byte[] bytes = null;
		try (ByteArrayOutputStream bo = new ByteArrayOutputStream();
				ObjectOutputStream oo = new ObjectOutputStream(bo);) {
			oo.writeObject(obj);
			bytes = bo.toByteArray();
		} catch (Exception e) {
			System.out.println("translation" + e.getMessage());
			e.printStackTrace();
		}
		return bytes;
	}

}