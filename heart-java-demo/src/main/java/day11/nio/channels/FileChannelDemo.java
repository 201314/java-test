package day11.nio.channels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 1、写入数据到Buffer
 * 
 * 2、调用flip()方法
 * 
 * 3、从Buffer中读取数据
 * 
 * 4、调用clear()方法或者compact()方法
 * 
 * @description
 * 
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年4月27日
 */
public class FileChannelDemo {
	private static void useNio() {
		RandomAccessFile aFile = null;
		try {
			aFile = new RandomAccessFile("D:\\trawe_store\\第2个接口 .txt", "rw");
			FileChannel inChannel = aFile.getChannel();

			// 创建容量为48byte的buffer
			ByteBuffer buffer = ByteBuffer.allocate(48);

			// channel-->buffer写入数据时，buffer会记录下写了多少数据
			int byteReader = inChannel.read(buffer);
			// byteBuffer.put();也可这样写入数据

			while (byteReader != -1) {
				System.out.print("Read:" + byteReader);
				// 一旦要读取数据，需要通过flip()方法将Buffer从写模式切换到读模式
				buffer.flip();

				while (buffer.hasRemaining()) {
					// 在读模式下，可以读取之前写入到buffer的所有数据
					// 每次读取1byte，也就是一个字节
					System.out.println((char) buffer.get());
					// buffer 读到-->channel
					// inChannel.write();
				}

				// 一旦读完了所有的数据，就需要清空缓冲区，让它可以再次被写入
				buffer.clear();
				// buffer.compact();
				byteReader = inChannel.read(buffer);

				// buffer.rewind()将position设回0，所以你可以重读Buffer中的所有数据
				// buffer.rewind();

				// buffer.mark();//标记当前position位置
				// 例如在解析过程中调用几次buffer.get()方法。
				// buffer.reset(); // 设置position恢复到标记的位置.
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				aFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		useNio();
	}

}
