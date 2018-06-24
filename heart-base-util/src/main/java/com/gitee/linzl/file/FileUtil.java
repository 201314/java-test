package com.gitee.linzl.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.FileUtils;

import com.adobe.xmp.impl.Base64;

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
		List<File> filesList = new ArrayList<>();

		if (dir.isDirectory()) {
			File[] fileArr = dir.listFiles();
			if (fileArr != null) {
				for (File file : fileArr) {
					if (file.isFile()) {
						filesList.add(file);
					} else {
						filesList.addAll(getAllFiles(file));
					}
				}
			}
		}
		return filesList;
	}

	/**
	 * 获取指定目录下的直接文件
	 * 
	 * @param dirPath
	 * @return
	 */
	public static List<File> getDirFiles(File dir) {
		List<File> files = new ArrayList<>();
		if (dir.isDirectory()) {
			File[] fileArr = dir.listFiles();
			for (File file : fileArr) {
				if (file.isFile()) {
					files.add(file);
				}
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

		ArrayList<File> files = new ArrayList<>();
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
	public static String read(File file) throws Exception {
		if (!file.isFile()) {
			throw new Exception("file不是文件");
		}

		String result = "";
		try (FileInputStream fs = new FileInputStream(file)) {
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
	 * @throws Exception
	 */
	public static boolean write(File file, String fileContent) throws Exception {
		if (!file.isFile()) {
			throw new Exception("file不是文件");
		}

		boolean result = false;
		try (OutputStream fs = new FileOutputStream(file)) {
			byte[] b = fileContent.getBytes();
			fs.write(b);
			result = true;
		}
		return result;
	}

	/**
	 * 追加内容到指定文件
	 * 
	 * @param file
	 * @param fileContent
	 * @return
	 * @throws IOException
	 */
	public static boolean append(File file, String fileContent) throws IOException {
		boolean result = false;
		if (file.exists() && file.isFile()) {
			try (RandomAccessFile rFile = new RandomAccessFile(file, "rw");) {
				byte[] b = fileContent.getBytes();
				long originLen = file.length();
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
		int totalLength = String.valueOf(count).length();

		ExecutorService executor = (ExecutorService) Executors.newCachedThreadPool();

		List<String> parts = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			int currentLength = String.valueOf(i + 1).length();
			String indexOfFile = String.format("%0" + (totalLength - currentLength) + "d", 0);
			String partFileName = String.format("%s.%s.part", file.getName(), indexOfFile);
			executor.submit(new SplitRunnable(byteSize, i * byteSize, partFileName, file));
			parts.add(partFileName);
		}
		return parts;
	}

	/**
	 * TODO，考虑加一个接口传入，让外部实现如何保存分片信息
	 * 
	 * @param file
	 *            要分片的文件
	 * @param byteSize
	 *            分片大小
	 * @return 返回分片后，每片序号对应的MD5
	 * @throws IOException
	 */
	public void splitBySize2(File file, int byteSize) throws IOException {
		long fileSize = file.length();
		int number = (int) (fileSize / byteSize);
		number = fileSize % byteSize == 0 ? number : number + 1;// 分割后文件的数目

		ExecutorService executor = (ExecutorService) Executors.newCachedThreadPool();
		List<Boolean> completeList = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			final int index = i;
			Future<Boolean> future = executor.submit(() -> {
				boolean flag = false;
				try (RandomAccessFile rFile = new RandomAccessFile(file, "r")) {
					int offset = index * byteSize;
					byte[] bytes = null;
					if (fileSize - offset >= byteSize) {
						bytes = new byte[byteSize];
					} else {// 最后一片可能小于byteSize
						bytes = new byte[(int) (fileSize - offset)];
					}

					rFile.seek(index * byteSize);// 移动指针到每“段”开头
					int s = rFile.read(bytes);

					flag = true;
					FileUtils.writeByteArrayToFile(new File("D:\\trawe_store\\app", String.valueOf(index) + ".txt"),
							Base64.encode(bytes));
					// 要把base64\MD5\分片大小\offset\index分片序号\File 传递给外部接口，允许其实现如何保存分片数据信息
				} catch (IOException e) {
					flag = false;
				}
				return flag;
			});

			try {
				completeList.add(future.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

		int totalCompleteNumber = 0;
		Iterator<Boolean> iter = completeList.iterator();
		while (iter.hasNext()) {
			boolean flag = iter.next();
			if (flag) {
				++totalCompleteNumber;
			}
		}

		if (totalCompleteNumber == number) {
			// 发布分片结束事件
		}
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
		ArrayList<File> partFiles = getDirFiles(dirPath, partFileSuffix);
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
		ArrayList<File> partFiles = getDirFiles(dirPath, partFileSuffix);
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
}