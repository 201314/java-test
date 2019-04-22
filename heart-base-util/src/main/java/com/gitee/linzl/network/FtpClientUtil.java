package com.gitee.linzl.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FTP上传下载文件,不做加解密处理
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年8月27日
 */
public class FtpClientUtil {
	private static final Logger log = LoggerFactory.getLogger(FtpClientUtil.class);
	// ftp服务器地址
	private String ip;
	// ftp服务器端口号默认为21
	private Integer port;
	private static final Integer DEFAULT_PORT = 21;
	// ftp登录账号
	private String username;
	// ftp登录密码
	private String password;

	public enum RegexEnum {
		START_REGEX, // 以什么为开头

		END_REGEX, // 以什么为结束

		MATCH_REGEX, // 以正则去匹配

		EQUAL_REGEX, // 完全相等

		INGORE_REGEX;// 忽略匹配规则
	}

	private FTPClient ftpClient = null;

	public FtpClientUtil(String ip, String username, String password) {
		this(ip, DEFAULT_PORT, username, password);
	}

	public FtpClientUtil(String ip, int port, String username, String password) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	/**
	 * 初始化ftp服务器
	 */
	public void init() {
		ftpClient = new FTPClient();
		// FTPClientConfig ftpClientConfig = new
		// FTPClientConfig(FTPClientConfig.SYST_NT);
		// ftpClientConfig.setServerTimeZoneId(TimeZone.getDefault().getID());
		// this.ftpClient.configure(ftpClientConfig);

		ftpClient.setControlEncoding("UTF-8");
		ftpClient.setCharset(Charset.forName("UTF-8"));// 解决中文乱码问题
		try {
			ftpClient.connect(ip, port); // 连接ftp服务器
			int replyCode = ftpClient.getReplyCode(); // 是否成功登录服务器
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				ftpClient.disconnect();
				log.debug("connect failed...ftp服务器:{},端口:{}", this.ip, this.port);
			} else {
				ftpClient.login(username, password); // 登录ftp服务器
				ftpClient.setDefaultTimeout(2 * 1000);
				ftpClient.setConnectTimeout(2 * 1000);
				ftpClient.setDataTimeout(2 * 1000);
				ftpClient.setBufferSize(1024 * 2);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 改变目录路径
	 * 
	 * @param directory
	 * @return
	 */
	public boolean changeWorkingDirectory(String directory) {
		boolean flag = true;
		try {
			flag = ftpClient.changeWorkingDirectory(directory);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean mkdir(String ftpDir, File file) {
		return mkdir(new File(ftpDir, file.getPath()));
	}

	/**
	 * 递归创建目录
	 * 
	 * @param file
	 * @return
	 */
	public boolean mkdir(File file) {
		try {
			if (existFile(file.getPath())) {
				changeWorkingDirectory(file.getPath());
			} else {
				mkdir(file.getParentFile());// 递归创建目录
				ftpClient.makeDirectory(file.getPath());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 创建目录
	 * 
	 * @param dir
	 * @return
	 */
	public boolean mkdirParent(File file) {
		final File parent = file.getParentFile();
		if (parent == null) {
			return false;
		}
		return mkdir(parent);
	}

	/**
	 * 判断ftp服务器文件是否存在
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public boolean existFile(String path) throws IOException {
		boolean flag = false;
		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		if (Objects.nonNull(ftpFileArr) && ftpFileArr.length > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 获取目录下的文件名
	 * 
	 * @param ftpDir
	 *            获取哪个目录
	 * @param recursion
	 *            是否递归获取子目录
	 * @return
	 */
	public List<String> listFiles(String ftpDir, boolean recursion) {
		List<String> fileLists = new ArrayList<>();
		try {
			FTPFile[] files = ftpClient.listFiles(ftpDir);
			for (FTPFile ftpFile : files) {
				if (ftpFile.isFile()) {
					fileLists.add(ftpFile.getName());
				} else if (recursion && ftpFile.isDirectory()) {
					List<String> cLists = listFiles(ftpDir + "/" + ftpFile.getName(), recursion);
					fileLists.addAll(cLists);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileLists;
	}

	/**
	 * 获得FTP 服务器下的文件列表,包含目录名称
	 * 
	 * @param ftpDir
	 *            ftp目录
	 * @return
	 */
	public String[] listFileNames(String ftpDir) {
		String files[] = null;
		try {
			this.ftpClient.changeWorkingDirectory(ftpDir);
			files = this.ftpClient.listNames();
		} catch (IOException e) {
			log.error("列出文件异常!", e);
			throw new RuntimeException("列出文件异常!", e);
		}
		return files;
	}

	/**
	 * 修改文件名
	 * 
	 * @param srcFname
	 *            源文件名
	 * @param targetFname
	 *            修改后的文件名
	 * @return
	 */
	public boolean reName(String srcFname, String targetFname) {
		boolean flag = false;
		try {
			flag = this.ftpClient.rename(srcFname, targetFname);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 删除文件,先切换到对应目录
	 * 
	 * @param filename
	 *            要删除的文件名称
	 * @return
	 */
	public boolean deleteFile(String filename) {
		boolean flag = false;
		try {
			return ftpClient.deleteFile(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 设置传输文件的类型[文本文件或者二进制文件]
	 *
	 * @param fileType--FTP.BINARY_FILE_TYPE,FTP.ASCII_FILE_TYPE
	 */
	public void setFileType(int fileType) {
		try {
			this.ftpClient.setFileType(fileType);
		} catch (Exception e) {
			log.error("设置传输文件的类型异常！", e);
			throw new RuntimeException("设置传输文件的类型异常！", e);
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param ftpDir
	 *            上传到指定的ftp目录
	 * @param file
	 *            待上传文件或文件夹
	 * @return
	 */
	public boolean upload(String ftpDir, File file) {
		try {
			String currentFtpDir = ftpDir;
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			if (file.isDirectory()) {// 创建当前目录
				currentFtpDir = currentFtpDir + "/" + file.getName();
				mkdir(new File(currentFtpDir));
				File[] files = file.listFiles();
				for (File source : files) {
					if (source.isDirectory()) {
						upload(currentFtpDir, source);
					} else {
						upload(currentFtpDir, source, source.getName());
					}
				}
			} else if (file.isFile()) {
				upload(currentFtpDir, file, file.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean upload(String ftpDir, File file, String fileName) throws Exception {
		if (Objects.isNull(file) || !file.isFile()) {
			throw new Exception("只能上传文件");
		}
		return upload(ftpDir, new BufferedInputStream(new FileInputStream(file)), fileName);
	}

	/**
	 * 上传文件
	 * 
	 * @param fileName
	 *            上传到ftp的文件名
	 * @param inputStream
	 *            输入文件流
	 * @return
	 */
	public boolean upload(String ftpDir, InputStream inputStream, String fileName) {
		boolean flag = false;
		try {
			changeWorkingDirectory(ftpDir);
			ftpClient.storeFile(fileName, inputStream);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	/**
	 * 下载ftp目录下的全部直接文件
	 *
	 * @param ftpDir
	 *            FTP服务器文件目录
	 * @param localDir
	 *            下载后的存储路径
	 * @return
	 */
	public List<String> downloadAll(String ftpDir, File localDir) {
		return download(ftpDir, localDir, null, RegexEnum.INGORE_REGEX, false);
	}

	/**
	 * 下载ftp目录下文件名以startWith开头的直接文件
	 *
	 * @param ftpDir
	 *            FTP服务器文件目录
	 * @param localDir
	 *            下载后的存储路径
	 * @param startWith
	 *            以某个字符串开头的文件
	 * @return
	 */
	public List<String> downloadStartWith(String ftpDir, File localDir, String startWith) {
		return download(ftpDir, localDir, startWith, RegexEnum.START_REGEX, false);
	}

	/**
	 * 下载ftp目录下文件名以endWith结尾的直接文件
	 *
	 * @param ftpDir
	 *            FTP服务器文件目录
	 * @param localDir
	 *            下载后的存储路径
	 * @param endWith
	 *            以某个字符串结尾的文件
	 * @return
	 */
	public List<String> downloadEndWith(String ftpDir, File localDir, String endWith) {
		return download(ftpDir, localDir, endWith, RegexEnum.END_REGEX, false);
	}

	/**
	 * 下载ftp目录下文件名为equalWith的直接文件
	 *
	 * @param ftpDir
	 *            FTP服务器文件目录
	 * @param localDir
	 *            下载后的存储路径
	 * @param equalWith
	 *            查找文件名为 equalWith的文件
	 * @return
	 */
	public List<String> downloadEqualWith(String ftpDir, File localDir, String equalWith) {
		return download(ftpDir, localDir, equalWith, RegexEnum.EQUAL_REGEX, false);
	}

	/**
	 * 下载ftp目录下文件名符合正则表达式的直接文件
	 *
	 * @param ftpDir
	 *            FTP服务器文件目录
	 * @param localDir
	 *            下载后的存储路径
	 * @param regex
	 *            合正则表达式
	 * @return
	 */
	public List<String> downloadMatchWith(String ftpDir, File localDir, String regex) {
		return download(ftpDir, localDir, regex, RegexEnum.MATCH_REGEX, false);
	}

	/**
	 * * 下载ftp文件到本地目录
	 * 
	 * @param ftpDir
	 *            FTP服务器文件目录
	 * @param localDir
	 *            下载后的存储路径
	 * @param regex
	 *            表达式
	 * @param type
	 *            采用的表达式匹配类型
	 * @param recursion
	 *            是否递归下载
	 * @return
	 */
	public List<String> download(String ftpDir, File localDir, String regex, RegexEnum type, boolean recursion) {
		List<String> downList = null;
		FTPFile[] ftpFiles = null;
		try {
//			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//			 ftpClient.enterLocalPassiveMode();
			boolean changeFtpDir = ftpClient.changeWorkingDirectory(ftpDir);
			if (!changeFtpDir) {
				return downList;
			}

			ftpFiles = ftpClient.listFiles();
			if (ftpFiles == null || ftpFiles.length <= 0) {
				return downList;
			}

			downList = new ArrayList<>();
			for (FTPFile file : ftpFiles) {
				if (!recursion && file.isDirectory()) {
					continue;
				}

				if (file.isDirectory()) {
					File dir = new File(localDir, file.getName());
					FileUtils.forceMkdir(dir);
					download(ftpDir + "/" + file.getName(), dir, regex, type, recursion);
				} else if (file.isFile()) {
					ftpClient.changeWorkingDirectory(ftpDir);
					String fileName = file.getName();// 文件名
					boolean isMatch = false;
					if (type == RegexEnum.START_REGEX) {
						isMatch = fileName.startsWith(regex);
					} else if (type == RegexEnum.END_REGEX) {
						isMatch = fileName.endsWith(regex);
					} else if (type == RegexEnum.MATCH_REGEX) {
						Matcher match = Pattern.compile(regex).matcher(fileName);
						isMatch = match.find();
					} else if (type == RegexEnum.EQUAL_REGEX) {
						isMatch = fileName.equals(regex);
					} else if (type == RegexEnum.INGORE_REGEX || type == null) {
						isMatch = true;
					}

					if (!isMatch) {
						continue;
					}

					try {
						FileUtils.forceMkdir(localDir);
					} catch (Exception e) {
						log.error("创建目录异常：" + e.getMessage());
					}
					File localFile = new File(localDir, fileName);
					try (OutputStream out = new FileOutputStream(localFile);
							BufferedOutputStream buffer = new BufferedOutputStream(out);) {
						if (ftpClient.retrieveFile(fileName, buffer)) {
							log.info("download file name is:{}", fileName);
							downList.add(localFile.getAbsolutePath());
						}
					}
				}
			}
		} catch (IOException se) {
			log.info("获取文件异常：" + se.getMessage());
		}
		return downList;
	}

	/**
	 * 退出并关闭FTP连接
	 */
	public void close() {
		if (Objects.nonNull(this.ftpClient) && this.ftpClient.isConnected()) {
			try {
				this.ftpClient.logout();// 退出FTP服务器,退出登录
				this.ftpClient.disconnect();// 关闭FTP服务器的连接
			} catch (Exception e) {
				throw new RuntimeException("连接FTP服务失败！", e);
			}
		}
	}
}