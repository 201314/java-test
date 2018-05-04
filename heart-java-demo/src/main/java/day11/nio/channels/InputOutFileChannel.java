package day11.nio.channels;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NIO是面向缓冲、通道的；传统IO面向流
 * 
 * NIO通道是双向的既可以写、也可以读；传统IO只能是单向的
 * 
 * NIO可以设置为异步；传统IO只能是阻塞，同步的
 * 
 * 
 * 缓冲区常用方法
 * 
 * clear：将当前位置设置为0，限制设置为容量，目的是尽最大可能让字节，由通道读取到缓冲中
 * 
 * flip：当前位置置为限制，然后将当前位置置为0，目的是将有数据部分的字节，由缓冲写入到通道中。通常用在读与写之间。
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年5月4日
 */
public class InputOutFileChannel {

	static void testReadAndWriteNIO() {
		String pathname = "D:\\trawe_store\\trawe_store.zip";
		String filename = "D:\\trawe_store\\trawe_store1.zip";

		try (FileInputStream fin = new FileInputStream(new File(pathname));
				FileOutputStream fos = new FileOutputStream(new File(filename));) {

			FileChannel channel = fin.getChannel();
			FileChannel outchannel = fos.getChannel();

			int capacity = 100;// 字节
			ByteBuffer bf = ByteBuffer.allocate(capacity);
			System.out.println("限制是：" + bf.limit() + "容量是：" + bf.capacity() + "位置是：" + bf.position());
			while (channel.read(bf) != -1) {
				// 将当前位置置为limit，然后设置当前位置为0，也就是从0到limit这块，都写入到同道中
				bf.flip();
				outchannel.write(bf);
				// 将当前位置置为0，然后设置limit为容量，也就是从0到limit（容量）这块，
				// 都可以利用，通道读取的数据存储到
				// 0到limit这块
				bf.clear();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		testReadAndWriteNIO();
	}

}
