package day11.nio.channels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @description
 * 
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年4月27日
 */
public class FileChannelDemo3 {
	private static void useNio() {
		RandomAccessFile fromFile = null;
		try {
			fromFile = new RandomAccessFile("D:\\trawe_store\\第2个接口.txt", "rw");
			FileChannel fromChannel = fromFile.getChannel();

			RandomAccessFile toFile = new RandomAccessFile("D:\\trawe_store\\toFile.txt", "rw");
			FileChannel toChannel = toFile.getChannel();

			long position = 0;
			long count = fromChannel.size();

			// 在SoketChannel的实现中，SocketChannel只会传输此刻准备好的数据（可能不足count字节）。
			// 因此，SocketChannel可能不会将请求的所有数据(count个字节)全部传输到FileChannel中
			toChannel.transferFrom(fromChannel, position, count);

			// 和以上方法一样，也会有同样的问题
			// fromChannel.transferTo(position, count, toChannel);

			toChannel.close();
			fromChannel.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}

	public static void main(String[] args) {
		useNio();
	}

}
