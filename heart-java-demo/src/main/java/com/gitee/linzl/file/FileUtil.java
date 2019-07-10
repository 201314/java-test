package com.gitee.linzl.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 认识文本和文本文件 java的文本(char)是16位无符号整数，是字符的unicode编码(双字节编码) 文件是byte byte
 * byte……的数据序列 文本文件是文本(char)序列按照某种编码(gbk,utf-8,utf-16be)序列化为byte的存储结果
 * 
 * @author linzl
 * 
 */
public class FileUtil {
	private static final String DEFAULT_CHARSET = "GBK";

	public static void createFile(File file, boolean dirFlag) throws IOException {
		if (file != null && !file.exists()) {
			// 如果该路径是目录，则创建文件夹，将所有不带后缀的路径全部认为是创建文件夹
			if (dirFlag) {
				file.mkdirs();
			} else {
				// 如果该路径是文件，则先判断其父文件夹存在否，不存在先创建
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				file.createNewFile();
			}
		}
	}

	/**
	 * 递归得到所有目录下的文件
	 * 
	 * @param file
	 * @param flag    true:表示结果只要目录对象 false:表示结果只要文件对象 null:表示所有递归结果
	 * @param reverse true:表示按照递归到最后一层才保存数据
	 * @return
	 */
	public static List<File> recursionFile(File file, String flag) {
		File[] files = file.listFiles();
		if (files == null) {
			return null;
		}

		List<File> fileList = new ArrayList<File>();
		for (File param : files) {
			// 递归出所有目录
			if (param.isDirectory() && "true".equals(flag)) {
				fileList.add(param);
				// 递归出所有文件
			} else if (param.isFile() && "false".equals(flag)) {
				fileList.add(param);
			} else if (flag == null) {// 递归出所有
				fileList.add(param);
			}
			recursionFile(param, flag);
		}
		return fileList;
	}

	/**
	 * 删除目录下所有直接子文件
	 * 
	 * @param file
	 */
	public static void deleteDirectDoc(File file) {
		if (file == null || !file.isDirectory()) {
			return;
		}
		File[] childFiles = file.listFiles();
		for (int i = 0; i < childFiles.length; i++) {
			childFiles[i].delete();
		}
	}

	/**
	 * 删除目录下的文件及其子目录的文件
	 */
	public static void recursionDeleteDoc(File file) {
		if (file == null || !file.isDirectory()) {
			return;
		}
		// 列出目录下所有文件夹和文件的名称
		File[] files = file.listFiles();
		if (files == null) {
			return;
		}
		for (File param : files) {
			if (param.isDirectory()) {
				recursionDeleteDoc(param);
			}
			param.delete();
		}
	}

	/**
	 * 删除目录下所有文件,包括该目录
	 */
	public static void recursionDeleteFile(File file) {
		// 列出目录下所有文件夹和文件的名称
		File[] files = file.listFiles();
		if (files == null) {
			return;
		}
		for (File param : files) {
			if (param.isDirectory()) {
				recursionDeleteFile(param);
			}
			param.delete();
		}
		file.delete();
	}

	/**
	 * 重命名文件名称
	 * 
	 * @param file    需要重命名的文件
	 * @param newName 新的文件名称(不用带后缀)
	 * @throws Exception
	 */
	public static void reName(File file, String newName) throws Exception {
		if (file.exists()) {
			// 文件的后缀
			String filePath = file.getPath();
			String suffix = filePath.substring(filePath.lastIndexOf("."));
			file.renameTo(new File(file.getParentFile().getPath(), newName + suffix));
		} else {
			throw new Exception("该" + file.getPath() + "路径不存在");
		}
	}

	/**
	 * 按字符数读取文件,常用于读文本，数字等类型的文本文件 Reader/Writer 如读word\pdf等使用ISO-8859-1
	 * 
	 * @param file    需要读取的文件对象
	 * @param charset 指定文件读取的编码,不指定默认为GBK
	 * @return
	 * @throws IOException
	 */
	public static String readFileByChars(File file, String charset) throws IOException {
		InputStream is = new FileInputStream(file);
		charset = (charset == null || charset.trim().length() <= 0) ? DEFAULT_CHARSET : charset;
		Reader reader = new InputStreamReader(is, charset);
		BufferedReader breader = new BufferedReader(reader);

		char[] cbuf = new char[is.available()];

		StringBuilder sb = new StringBuilder();
		while (breader.read(cbuf) > -1) {
			sb.append(cbuf);
		}
		try {
			breader.close();
			reader.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			if (breader != null) {
				try {
					breader.close();
				} catch (IOException io) {
				} finally {
					breader = null;
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException io) {
				} finally {
					reader = null;
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 按行读取
	 * 
	 * @param file    需要读取的文件对象
	 * @param charset 指定文件读取的编码,不指定默认为GBK
	 * @return
	 * @throws Exception
	 */
	public static String readFileByLines(File file, String charset) throws Exception {
		if (!file.exists()) {
			throw new Exception(file.getPath() + "不存在该文件");
		}
		InputStream is = new FileInputStream(file);
		charset = (charset == null || charset.trim().length() <= 0) ? DEFAULT_CHARSET : charset;
		Reader reader = new InputStreamReader(is, charset);
		BufferedReader br = new BufferedReader(reader);

		StringBuilder sb = new StringBuilder();
		String temp = "";
		while ((temp = br.readLine()) != null) {
			sb.append(temp);
			// 换行符
			sb.append(System.getProperty("line.separator"));
		}

		try {
			br.close();
			reader.close();
			is.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException io) {
				} finally {
					br = null;
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException io) {
				} finally {
					reader = null;
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException io) {
				} finally {
					is = null;
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 复制文件
	 * 
	 * @param source 源文件
	 * @param target 复制文件
	 * @throws IOException
	 */
	public static void copyFile(File source, File target) throws IOException {
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(source));
		BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(target));

		byte[] sourceB = new byte[1024 * 5];
		int length = 0;
		while ((length = is.read(sourceB)) > -1) {
			System.out.println(new String(sourceB));
			fos.write(sourceB, 0, length);
			fos.flush();// 使用了Buffer类，必须强制刷新
		}
		fos.close();
		is.close();
	}

	/**
	 * 删除该路径下所有名称匹配的文件，包括路径下文件夹中的文件
	 * 
	 * @param path    删除路径
	 * @param pattern 匹配文件名的正则表达式
	 * @throws Exception
	 */
	public void deleteDoc(String path, String pattern) throws Exception {
		File file = new File(path);
		if (file.exists()) {
			List<File> list = (List<File>) findMatchFileName(path, pattern);
			for (File dest : list) {
				deleteDirectDoc(dest);
			}
		} else {
			throw new Exception(path + "路径不存在");
		}
	}

	/**
	 * 递归搜索目录下文件名与pattern匹配的所有文件
	 * 
	 * @param path    搜索路径
	 * @param pattern 匹配文件名的正则表达式
	 * @return
	 * @throws Exception
	 */
	public List<File> findMatchFileName(String path, String pattern) {
		List<File> docList = recursionFile(new File(path), null);
//		for (File file : new ArrayList<File>(docList)) {// 复制一份，List不能安全删除，除非使用Iterator
//			if (file.getName().indexOf(pattern) < 0) {
//				docList.remove(file);
//			}
//		}

		// 或者
		Iterator<File> iter = docList.iterator();
		if (iter != null) {
			while (iter.hasNext()) {
				File file = iter.next();
				if (file.getName().indexOf(pattern) < 0) {
					iter.remove();
				}
			}
		}
		return docList;
	}
}
