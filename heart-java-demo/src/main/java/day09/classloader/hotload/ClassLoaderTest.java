package day09.classloader.hotload;

/**
 * 测试Java类的热加载
 * @author liuyazhuang
 *
 */
public class ClassLoaderTest {
	public static void main(String[] args) {
		new Thread(new MsgHandler()).start();
	}
}
