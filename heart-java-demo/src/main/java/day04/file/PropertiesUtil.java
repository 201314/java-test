package day04.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

/**
 * 常用资源文件工具类
 * 
 * @author linzl 2013年8月15日
 */
public class PropertiesUtil {
	private static Properties properties = new Properties();

	public PropertiesUtil() {
		try {
			// InputStream
			// in=this.getClass().getClassLoader().getResourceAsStream("jdbc.properties");

			// 使用getResource或getResourceAsStream toString得到的路径 file://开头
			// 采用 getFile()则不会有file://开头
			// 获取该类的加载器，和类文件是同级
			InputStream in = this.getClass().getResourceAsStream("/com/linzl/cn/property/jdbc.properties");
			// getClassLoader是获取本类的父加载器，和该类的包结构是同级
			in = this.getClass().getClassLoader().getResourceAsStream("com/linzl/cn/property/jdbc.properties");

			System.err.println("1-->" + this.getClass().getResource("/com/linzl/cn/property/jdbc.properties"));

			URL url = PropertiesUtil.class.getClassLoader().getResource("com/linzl/cn/property/jdbc.properties");
			System.err.println("2-->" + url);

			System.err.println("3-->" + this.getClass().getResource("/src.properties"));

			System.err.println("4-->" + this.getClass().getClassLoader().getResource("src.properties"));

			System.err.println("5-->" + this.getClass().getResource("/com/linzl/cn/test"));

			System.err.println("6-->" + this.getClass().getClassLoader().getResource("com/linzl/cn/test"));

			// InputStream in = new FileInputStream("具体路径的资源文件");
			properties.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取资源文件的属性值
	 */
	public static String getAssignName(String property) {
		return properties.getProperty(property, "没有该" + property + "属性值");
	}

	/**
	 * 默认资源文件中驱动名称的Key为driverName
	 */
	public static String getDefaultDriverName() {
		return getAssignName("driverName");
	}

	/**
	 * 默认资源文件中数据库地址的key为url
	 */
	public static String getDefaultUrl() {
		return getAssignName("url");
	}

	/**
	 * 默认资源文件中用户名的key为user
	 */
	public static String getDefaultUser() {
		return getAssignName("user");
	}

	/**
	 * 默认资源文件中用户密码的key为password
	 */
	public static String getDefaultPass() {
		return getAssignName("password");
	}

	/**
	 * 设置资源文件属性key的值为value
	 * 
	 * @param key
	 *            属性
	 * @param value
	 *            属性值
	 */
	public static void setKeyValue(String key, String value) {
		properties.setProperty(key, value);
	}

	/**
	 * 保存properties属性的修改
	 * 
	 * @param path
	 *            保存位置路径
	 * @throws IOException
	 */
	public static void outputToProp(String path) throws IOException {
		OutputStream fos = new FileOutputStream(path);
		properties.store(fos, "输出到properties文件");
		// properties.storeToXML(fos, "保存为xml格式");
		fos.close();
	}

	public static void main(String[] args) throws IOException {
		new PropertiesUtil();
		PropertiesUtil.setKeyValue("name", "linzl");
		PropertiesUtil.setKeyValue("age", "25");
		PropertiesUtil.setKeyValue("height", "70KG");
		PropertiesUtil.outputToProp("D:/first.properties");
	}
}
