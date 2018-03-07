package day06.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
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
	private static List<File> fileList = new ArrayList<File>();

	/**
	 * 创建指定路径文件，路径不存在自动创建
	 * 
	 * @param path
	 *            指定的路径
	 * @throws IOException
	 */
	public static void createDoc(String path) throws IOException {
		File file = new File(path);
		createFile(file, false);
	}

	public static void createDoc(File file) throws IOException {
		createFile(file, false);
	}

	/**
	 * 创建指定路径文件夹，路径不存在自动创建
	 * 
	 * @param path
	 *            指定的路径
	 * @throws IOException
	 */
	public static void createDir(String path) throws IOException {
		File file = new File(path);
		createFile(file, true);
	}

	public static void createDir(File file) throws IOException {
		createFile(file, true);
	}

	private static void createFile(File file, boolean dirFlag) throws IOException {
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
	 * 递归得到所有目录
	 * 
	 * @return
	 */
	public static List<File> recursionAllDir(String path) {
		return recursionFile(new File(path), "true");
	}

	public static List<File> recursionAllDir(File file) {
		return recursionFile(file, "true");
	}

	/**
	 * 递归得到所有文档
	 * 
	 * @return
	 */
	public static List<File> recursionAllDoc(String path) {
		return recursionFile(new File(path), "false");
	}

	public static List<File> recursionAllDoc(File file) {
		return recursionFile(file, "false");
	}

	/**
	 * 递归得到所有目录及文档
	 * 
	 * @return
	 */
	public static List<File> recursionAllFile(String path) {
		return recursionFile(new File(path), null);
	}

	public static List<File> recursionAllFile(File file) {
		return recursionFile(file, null);
	}

	/**
	 * 递归得到所有目录下的文件
	 * 
	 * @param file
	 * @param flag
	 *            true:表示结果只要目录对象 false:表示结果只要文件对象 null:表示所有递归结果
	 * @param reverse
	 *            true:表示按照递归到最后一层才保存数据
	 * @return
	 */

	private static List<File> recursionFile(File file, String flag) {
		File[] files = file.listFiles();
		if (files == null) {
			return null;
		}
		for (File param : files) {
			if (param != null) {
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
		}
		return fileList;
	}

	/**
	 * 删除目录下所有直接子文件
	 * 
	 * @param path
	 *            目录路径
	 */
	public static void deleteDirectDoc(String path) {
		deleteDirectDoc(new File(path));
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
	public static void recursionDeleteDoc(String path) {
		recursionDeleteDoc(new File(path));
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
			} else {
				param.delete();
			}
		}
	}

	/**
	 * 删除目录下的文件及其子目录的文件
	 */
	public static void recursionDeleteFile(String path) {
		recursionDeleteFile(new File(path));
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
	 * @param path
	 *            需要重命名的文件的路径
	 * @param newName
	 *            新的文件名称(不用带后缀)
	 * @throws Exception
	 */
	public static void reName(String path, String newName) throws Exception {
		File file = new File(path);
		reName(file, newName);
	}

	/**
	 * 重命名文件名称
	 * 
	 * @param file
	 *            需要重命名的文件
	 * @param newName
	 *            新的文件名称(不用带后缀)
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
	 * 按字节数读取文件,常用于读二进制文件，如图片、声音、影像、Office等文件 InputStream/OutputStream
	 * 
	 * @param path
	 *            文件路径
	 * @return
	 * @throws IOException
	 */
	public static byte[] readFileByBytes(String path) throws IOException {
		return readFileByBytes(new File(path));
	}

	/**
	 * 按字节数读取文件,常用于读二进制文件，如图片、声音、影像等文件 InputStream/OutputStream
	 * 
	 * @param file
	 *            读取的文件对象
	 * @return
	 * @throws IOException
	 */
	public static byte[] readFileByBytes(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		byte[] bytes = new byte[is.available()];

		BufferedInputStream bin = new BufferedInputStream(is);
		bin.read(bytes);
		try {
			is.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException io) {
				} finally {
					is = null;
				}
			}
		}
		return bytes;
	}

	/**
	 * 按字符数读取文件,常用于读文本，数字等类型的文本文件 Reader/Writer 如读word\pdf等使用ISO-8859-1
	 * 
	 * @param path
	 *            文本文件路径
	 * @param charset
	 *            指定文件读取的编码,不指定默认为GBK
	 * @return
	 * @throws IOException
	 */
	public static String readFileByChars(String path, String charset) throws IOException {
		return readFileByChars(new File(path), charset);
	}

	/**
	 * 按字符数读取文件,常用于读文本，数字等类型的文本文件 Reader/Writer 如读word\pdf等使用ISO-8859-1
	 * 
	 * @param file
	 *            需要读取的文件对象
	 * @param charset
	 *            指定文件读取的编码,不指定默认为GBK
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
	 * @param path
	 *            文件路径
	 * @param charset
	 *            指定文件读取的编码,不指定默认为GBK
	 * @return
	 * @throws Exception
	 */
	public static String readFileByLines(String path, String charset) throws Exception {
		File file = new File(path);
		return readFileByLines(file, charset);
	}

	/**
	 * 按行读取
	 * 
	 * @param file
	 *            需要读取的文件对象
	 * @param charset
	 *            指定文件读取的编码,不指定默认为GBK
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
	 * 写文件 FileOutputStream
	 * 
	 * @param path
	 *            写入文件的路径
	 * @param content
	 *            写入文件的内容
	 * @throws IOException
	 */
	public static void writeByFileOutputStream(String path, byte[] content) throws IOException {
		File file = new File(path);
		OutputStream os = new FileOutputStream(file);
		BufferedOutputStream bos = new BufferedOutputStream(os);
		bos.write(content);
		bos.flush();

		bos.close();
		os.close();
	}

	/**
	 * 写文件Writer(只能写入字符流)
	 * 
	 * @param path
	 *            写入文件的路径
	 * @param content
	 *            写入文件的内容
	 * @throws IOException
	 */
	public static void writeByFileWriter(String path, String content) throws IOException {
		File file = new File(path);
		OutputStream fos = new FileOutputStream(file);
		Writer wt = new OutputStreamWriter(fos, "UTF-8");
		BufferedWriter bw = new BufferedWriter(wt);
		bw.write(content);
		bw.flush();

		bw.close();
		wt.close();
	}

	/**
	 * 读取文件最后几行
	 * 
	 * @param path
	 *            文件路径
	 * @param numRead
	 *            读取行数
	 */
	public static void readLastLine(String path, int numRead) {
		File file = new File(path);
		RandomAccessFile raf = null;
		try {
			// "r" 表示 read,只读 "w"表示可写
			raf = new RandomAccessFile(file, "rw");
			// 文件长度
			long length = raf.length();
			long pos = length - 1;// 文本的最后一位为结束符，所以要减去1

			int count = 0;
			while (pos > 0) {
				pos--;
				// 从最后一个字节，往前读取
				raf.seek(pos);

				if (raf.readByte() == '\n') {
					count++;
				}

				if (count == numRead) {
					break;
				}
			}

			// 开头第一个字节
			if (pos == 0) {
				raf.seek(pos);
				// System.out.println(raf.readLine());
			}

			byte[] bytes = new byte[(int) (length - 1 - pos)];// 文本的最后一位为结束符，所以要减去1
			raf.read(bytes, 0, bytes.length);
			System.out.println(new String(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (Exception e2) {
				} finally {
					raf = null;
				}
			}
		}
	}

	/**
	 * 复制文件
	 * 
	 * @param source
	 *            源文件
	 * @param target
	 *            复制文件
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
	 * 文件末尾追加内容
	 * 
	 * @throws IOException
	 */
	public static void appendFileEnd(String filePath, InputStream is) throws IOException {
		OutputStream os = new FileOutputStream(filePath, true);
		byte[] b = new byte[1024 * 5];
		int length = 0;
		while ((length = is.read(b)) > 0) {
			os.write(b, 0, length);
			os.flush();
		}
		os.close();
	}

	/**
	 * 删除该路径下所有名称匹配的文件，包括路径下文件夹中的文件
	 * 
	 * @param path
	 *            删除路径
	 * @param pattern
	 *            匹配文件名的正则表达式
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
	 * @param path
	 *            搜索路径
	 * @param pattern
	 *            匹配文件名的正则表达式
	 * @return
	 * @throws Exception
	 */
	public List<File> findMatchFileName(String path, String pattern) {
		List<File> docList = recursionAllDoc(path);
		for (File file : new ArrayList<File>(docList)) {// 复制一份，List不能安全删除，除非使用Iterator
			if (file.getName().indexOf(pattern) < 0) {
				docList.remove(file);
			}
		}

		// 或者
		// Iterator<File> iter = docList.iterator();
		// if (iter != null) {
		// while (iter.hasNext()) {
		// File file = iter.next();
		// if (file.getName().indexOf(pattern) < 0) {
		// iter.remove();
		// }
		// }
		// }
		return docList;

	}

	public static void main(String[] args) throws Exception {
		// 创建文件夹和文件
		FileUtil util = new FileUtil();
		// util.createDir("F:/first\\second\\third\\forth");
		// util.createDoc("F:/first\\second\\third\\forth/hello.txt");

		// 递归出所有目录
		// List list = util.recursionAllDir("F:/迅雷下载");

		// 递归出所有文档
		// List list = util.recursionAllDoc("F:/迅雷下载");

		// 递归出所有文件
		// List list = util.recursionAllFile("F:/迅雷下载");
		// for (Object file : list) {
		// System.out.println(file);
		// }

		// 删除指定文件
		// util.deleteDoc("F:\\first\\second - 副本\\hello - 副本.txt");

		// 删除目录下所有直接子文件
		// util.deleteDirectDoc("F:\\first\\second - 副本");

		// 删除目录下及其子目录下所有文件
		// util.recursionDeleteFile("F:\\first\\second - 副本");

		// 重命名文件
		// util.reName("F:\\first\\second\\third\\测试重命名3.doc", "测试重命名");

		// 按字节读取文件
		// byte[] byteContent = util.readFileByBytes("D:\\1.jpg", "");
		// util.writeByFileOutputStream("D:\\temp\\2.jpg", byteContent);
		//
		// // 按字符读取文件
		// String charContent = util.readFileByChars("D:\\test.txt", "utf-8");
		// System.out.println(charContent);
		// util.writeByFileWriter("D:\\test.xml", charContent);
		//
		// // 按行读取文件
		// String lineContent = util.readFileByLines("D:\\test.xml", "");
		// util.writeByFileOutputStream("D:\\temp\\rename.xml",
		// lineContent.getBytes());
		// 最后几行
		// util.readLastLine("F:\\log4j.xml", 131);
		// 复制文件
		// File source = new File("D:\\test.doc");
		// File target = new File("D:\\temp\\test.doc");
		// util.copyFile(source, target);

		// List<File> list = util.findMatchFileName("F:\\first\\second", "副本");
		// for (File file : list) {
		// System.out.println(file);
		// }
	}
}
