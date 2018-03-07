package day08.spi;

import java.util.ServiceLoader;

/**
 * 一个服务(service)通常指的是已知的接口或者抽象类，服务提供方就是对这个接口或者抽象类的实现，然后按spi标准存放到资源路径META-INF/
 * services目录下
 * ，文件的命名为该服务接口的全限定名。如有一个服务接口com.test.Service，其服务实现类为com.test.ChildService，
 * 此时需要在
 * META-INF/services中放置文件com.test.Service，其中的内容就为该实现类的全限定名com.test.ChildService
 * ，有多个服务实现，每一行写一个服务实现，#后面的内容为注释，并且该文件只能够是以UTF-8编码。
 * 这种实现方式，感觉和我们通常的开发方式差不多，都是定义一个接口
 * ，然后子类实现父类中定义的方法，为什么要搞这么一套标准以及单独搞一个配置文件？这种方式主要是针对不同的服务提供厂商
 * ，对不同场景的提供不同的解决方案制定的一套标准
 * ，举个简单的例子，如现在的JDK中有支持音乐播放，假设只支持mp3的播放，有些厂商想在这个基础之上支持mp4的播放
 * ，有的想支持mp5，而这些厂商都是第三方厂商
 * ，如果没有提供SPI这种实现标准，那就只有修改JAVA的源代码了，那这个弊端也是显而易见的，也就是不能够随着JDK的升级而升级现在的应用了
 * ，而有了SPI标准，SUN公司只需要提供一个播放接口，在实现播放的功能上通过ServiceLoad的方式加载服务，那么第三方只需要实现这个播放接口，
 * 再按SPI标准进行打包成jar，再放到classpath下面就OK了，没有一点代码的侵入性。
 * 
 * @author linzl
 * 
 */
public class SpiTest2 {

	/**
	 * @param args
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		ServiceLoader<SPIService> loader = ServiceLoader.load(SPIService.class);
		if (loader == null) {
			return;
		}

		for (SPIService service : loader) {
			System.out.println("哪个类--》" + service.getClass().getSimpleName());
			service.test();
		}
	}
}
