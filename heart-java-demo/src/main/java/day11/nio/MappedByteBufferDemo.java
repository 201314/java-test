package day11.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 
 * 内存映射文件能让你创建和修改那些因为太大而无法放入内存的文件。
 * 
 * 有了内存映射文件，你就可以认为文件已经全部读进了内存，然后把它当成一个非常大的数组来访问。
 * 
 * 这种解决办法能大大简化修改文件的代码。 fileChannel.map(FileChannel.MapMode mode, long
 * position,long size)将此通道的文件区域直接映射到内存中。
 * 注意，你必须指明，它是从文件的哪个位置开始映射的，映射的范围又有多大；也就是说，它还可以映射一个大文件的某个小片断。
 * 
 * MappedByteBuffer
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年5月4日
 */
public class MappedByteBufferDemo {
	static void testReadAndWriteNIO() {
		String pathname = "D:\\trawe_store\\trawe_store.zip";
		String filename = "D:\\trawe_store\\trawe_store1.zip";

		try (RandomAccessFile fin = new RandomAccessFile(pathname, "rw");
				FileOutputStream fos = new FileOutputStream(new File(filename));) {

			FileChannel channel = fin.getChannel();
			// 通过通道获取内存映射
			int capacity = 100;// 字节
			MappedByteBuffer bf = channel.map(FileChannel.MapMode.READ_WRITE, 0, capacity);
			FileChannel outchannel = fos.getChannel();

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
