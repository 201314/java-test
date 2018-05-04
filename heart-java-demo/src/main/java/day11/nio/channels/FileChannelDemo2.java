package day11.nio.channels;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 从Channel中分散（scatter）读取，是指在读操作时将读取的数据写入多个buffer中,
 * 
 * 因此，从Channel中读取的数据将“分散（scatter）”到多个Buffer中。
 * 
 * 聚集（gather）写入一个Channel，是指在写操作时将多个buffer的数据写入同一个Channel，
 * 
 * 因此，多个Buffer中的数据将“聚集（gather）”后写入到一个Channel。
 * 
 * 
 * @description
 * 
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年4月27日
 */
public class FileChannelDemo2 {
	private static void useNio() {
		RandomAccessFile aFile = null;
		try {
			aFile = new RandomAccessFile("D:\\trawe_store\\第2个接口.txt", "rw");
			FileChannel inChannel = aFile.getChannel();

			ByteBuffer head = ByteBuffer.allocate(128);
			ByteBuffer body = ByteBuffer.allocate(1024);

			// Scattering Reads在移动下一个buffer前，必须填满当前的buffer。
			// 这意味着不适用于动态消息(消息大小不固定)。换句话说，如果存在消息头和消息体，消息头必须完成填充(例如128byte),Scattering
			// Reads才能正常工作。
			inChannel.read(new ByteBuffer[] { head, body });

			FileOutputStream os = new FileOutputStream(new File("D:\\trawe_store\\111.txt"));
			FileChannel outChannel = os.getChannel();
			// write()方法会按照buffer在数组中的顺序，将数据写入到channel， 注意只有position和limit之间的数据才会被写入。
			// 因此，如果一个buffer的容量为128byte，但是仅仅包含58byte的数据，那么这58byte的数据将被写入到channel中。
			// 因此与Scattering Reads相反，Gathering Writes能较好的处理动态消息。
			outChannel.write(new ByteBuffer[] { head, body });
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
