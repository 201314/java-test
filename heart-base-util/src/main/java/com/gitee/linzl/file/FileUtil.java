package com.gitee.linzl.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

			long pos = len - 1;
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

			byte[] bytes = new byte[(int) (len - pos)];
			raf.read(bytes);

			if (charset == null) {
				return new String(bytes);
			} else {
				return new String(bytes, charset);
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
		}
		return null;
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
	 * 
	 * @param file
	 *            要分片的文件
	 * @param byteSize
	 *            分片大小
	 * @throws IOException
	 */
	public void asynSplitFile(File file, int byteSize, ProgressListener listener) throws IOException {
		long fileSize = file.length();
		int number = (int) (fileSize / byteSize);
		number = fileSize % byteSize == 0 ? number : number + 1;// 分割后文件的数目

		ExecutorService executor = (ExecutorService) Executors.newCachedThreadPool();
		List<Boolean> completeList = new ArrayList<>();
		for (int index = 0; index < number; index++) {
			SplitFileRequest request = new SplitFileRequest();
			request.setFile(file);
			request.setPartNum(index);
			request.setPartSize(byteSize);
			request.setListener(listener);
			Future<Boolean> future = executor.submit(new SplitRunnable(request));

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
	public void asynMergeFiles(String dirPath, String partFileSuffix, int partFileSize, File mergeFile)
			throws IOException {
		ArrayList<File> partFiles = getDirFiles(dirPath, partFileSuffix);
		Collections.sort(partFiles, new FileComparator());
		ExecutorService executor = (ExecutorService) Executors.newCachedThreadPool();

		for (int index = 0, length = partFiles.size(); index < length; index++) {
			Future<Boolean> future = executor
					.submit(new MergeRunnable(index * partFileSize, mergeFile, partFiles.get(index)));
			try {
				if (future.get()) {// 表示已经合并成功，即可删除
					partFiles.get(index).deleteOnExit();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
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
	private class FileComparator implements Comparator<File> {
		public int compare(File o1, File o2) {
			return o1.getName().compareToIgnoreCase(o2.getName());
		}
	}
}