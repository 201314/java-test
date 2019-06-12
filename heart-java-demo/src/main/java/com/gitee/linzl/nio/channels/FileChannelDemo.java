package com.gitee.linzl.nio.channels;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

/**
 * 1、写入数据到Buffer
 * 
 * 2、调用flip()方法
 * 
 * 3、从Buffer中读取数据
 * 
 * 4、调用clear()方法或者compact()方法
 * 
 * 
 * 从Channel中分散（scatter）读取，是指在读操作时将读取的数据写入多个buffer中,
 * 
 * 因此，从Channel中读取的数据将“分散（scatter）”到多个Buffer中。
 * 
 * 聚集（gather）写入一个Channel，是指在写操作时将多个buffer的数据写入同一个Channel，
 * 
 * 因此，多个Buffer中的数据将“聚集（gather）”后写入到一个Channel。
 * 
 * 
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
 * FileChannel 是线程安全的,只能通过已经打开的文件流获取FileChannel
 * 
 * @description
 * 
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年4月27日
 */
public class FileChannelDemo {

	public static void writeToConsole() {
		try (RandomAccessFile aFile = new RandomAccessFile("D:\\trawe_store\\第2个接口.txt", "rw")) {
			FileChannel inChannel = aFile.getChannel();

			// 创建容量为48byte的buffer
			ByteBuffer buffer = ByteBuffer.allocate(48);

			// byteBuffer.put();也可这样写入数据
			// channel-->buffer写入数据时，buffer会记录下写了多少数据
			while (inChannel.read(buffer) != -1) {
				// 一旦要读取数据，需要通过flip()方法将Buffer从写模式切换到读模式
				buffer.flip();
				while (buffer.hasRemaining()) {
					// 在读模式下，可以读取之前写入到buffer的所有数据
					// 每次读取1byte，也就是一个字节,也可以指定一次读取多少字节
					System.out.println((char) buffer.get());
					// buffer 读到-->channel
					// inChannel.write();
				}

				// 一旦读完了所有的数据，就需要清空缓冲区，让它可以再次被写入
				buffer.clear();
				// 调用compact()的作用是丢弃已经释放(已经输出)的数据，保留未释放的数据，并使缓冲区对重新填充容量准备就绪
				// buffer.compact();

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
		}
	}

	public static void headAndBody() {
		try (RandomAccessFile aFile = new RandomAccessFile("D:\\trawe_store\\第2个接口.txt", "rw")) {
			FileChannel inChannel = aFile.getChannel();

			ByteBuffer head = ByteBuffer.allocate(128);
			ByteBuffer body = ByteBuffer.allocate(1024);

			// Scattering Reads在移动下一个buffer前，必须填满当前的buffer。
			// 这意味着不适用于动态消息(消息大小不固定)。换句话说，如果存在消息头和消息体，消息头必须完成填充(例如128byte),Scattering
			// Reads才能正常工作。
			inChannel.read(new ByteBuffer[] { head, body });

			FileOutputStream fos = new FileOutputStream(new File("D:\\trawe_store\\111.txt"));
			FileChannel outChannel = fos.getChannel();
			// write()方法会按照buffer在数组中的顺序，将数据写入到channel， 注意只有position和limit之间的数据才会被写入。
			// 因此，如果一个buffer的容量为128byte，但是仅仅包含58byte的数据，那么这58byte的数据将被写入到channel中。
			// 因此与Scattering Reads相反，Gathering Writes能较好的处理动态消息。
			outChannel.write(new ByteBuffer[] { head, body });
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void transferFrom() {
		try (RandomAccessFile fromFile = new RandomAccessFile("D:\\trawe_store\\第2个接口.txt", "rw");
				FileChannel fromChannel = fromFile.getChannel();
				RandomAccessFile toFile = new RandomAccessFile("D:\\trawe_store\\toFile.txt", "rw");
				FileChannel toChannel = toFile.getChannel();) {

			long position = 0;
			long count = fromChannel.size();

			// 在SoketChannel的实现中，SocketChannel只会传输此刻准备好的数据（可能不足count字节）。
			// 因此，SocketChannel可能不会将请求的所有数据(count个字节)全部传输到FileChannel中
			toChannel.transferFrom(fromChannel, position, count);

			// 和以上方法一样，也会有同样的问题
			// fromChannel.transferTo(position, count, toChannel);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readAndWrite() {
		String pathname = "D:\\trawe_store\\trawe_store.zip";
		String filename = "D:\\trawe_store\\trawe_store1.zip";

		try (FileInputStream fin = new FileInputStream(new File(pathname));
				FileChannel fromChannel = fin.getChannel();
				FileOutputStream fos = new FileOutputStream(new File(filename));
				FileChannel toChannel = fos.getChannel();) {

			int capacity = 100;// 字节
			ByteBuffer bf = ByteBuffer.allocate(capacity);
			System.out.println("限制是：" + bf.limit() + "容量是：" + bf.capacity() + "位置是：" + bf.position());
			while (fromChannel.read(bf) != -1) {
				// 将当前位置置为limit，然后设置当前位置为0，也就是从0到limit这块，都写入到同道中
				bf.flip();
				toChannel.write(bf);
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

	public static void openFileChannel() {
		String sourceName = "D:\\trawe_store\\trawe_store.zip";
		String targetName = "D:\\trawe_store\\trawe_store1.zip";

		try (FileChannel read = FileChannel.open(Paths.get(sourceName), StandardOpenOption.READ,
				StandardOpenOption.WRITE);
				FileChannel write = FileChannel.open(Paths.get(targetName),
						EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW));) {

			MappedByteBuffer inByte = read.map(FileChannel.MapMode.READ_WRITE, 0, 100);

			while (read.read(inByte) != -1) {
				inByte.flip();
				write.write(inByte);
				inByte.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 内存映射文件能让你创建和修改那些因为太大而无法放入内存的文件。
	 * 
	 * 有了内存映射文件，你就可以认为文件已经全部读进了内存，然后把它当成一个非常大的数组来访问。
	 * 
	 * 这种解决办法能大大简化修改文件的代码。 fileChannel.map(FileChannel.MapMode mode, long
	 * position,long size)将此通道的文件区域直接映射到内存中。
	 * 注意，你必须指明，它是从文件的哪个位置开始映射的，映射的范围又有多大；也就是说，它还可以映射一个大文件的某个小片断。
	 * 
	 * MappedByteBuffer
	 */
	public static void mappedByteBuffer() {
		String pathname = "D:\\trawe_store\\trawe_store.zip";
		String filename = "D:\\trawe_store\\trawe_store1.zip";

		try (RandomAccessFile fin = new RandomAccessFile(pathname, "rw");
				FileOutputStream fos = new FileOutputStream(new File(filename));) {

			FileChannel channel = fin.getChannel();
			FileChannel outchannel = fos.getChannel();

			// 通过通道获取内存映射
			int capacity = 100;// 字节
			MappedByteBuffer bf = channel.map(FileChannel.MapMode.READ_WRITE, 0, capacity);

			// System.out.println("限制是：" + bf.limit() + "容量是：" + bf.capacity() + "位置是：" +
			// bf.position());
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

	/**
	 * 独占锁，写文件
	 * 
	 * 1 共享锁：允许多个线程进行文件的读取操作
	 * 
	 * 2 独占锁： 只允许一个线程进行文件的读/写操作
	 * 
	 * 锁的对象是文件而不是通道或线程，这意味着文件锁不适用于判优同一台 Java 虚拟机上的多个线程发起的访问。
	 * 
	 * 如果一个线程在某个文件上获得了一个独占锁，然后第二个线程利用一个单独打开的通道来请求该文件的独占锁，
	 * 
	 * 那么第二个线程的请求会抛出OverlappingFileLockException异常。但如果这两个线程运行在不同的 Java
	 * 
	 * 虚拟机上，那么第二个线程会阻塞，因为锁最终是由操作系统或文件系统来判优的并且几乎总是在进程级而非线程级（同一JVM上的线程）上判优。
	 * 
	 * 锁都是与一个文件关联的，而不是与单个的文件句柄或通道关联。
	 * 
	 * 0.可以通过lock方法来获取文件锁，可以通过tryLock方来测试该文件锁是否可用。二者的区别是那：lock()方法是阻塞的方法，当文件锁不可用时，当前进程会被挂起；tryLock是非阻塞的方法，当文件锁不可用时，tryLock()会得到null值。
	 * 
	 * 1.同一进程内，在文件锁没有被释放之前，不可以再次获取。即在release()方法调用前,只能lock()或者tryLock()一次。
	 * 
	 * 2.进程锁对于同一进程来说是共享(shared)的，即这个进程中的线程都可以操作这个文件锁（且线程安全）；
	 * 对于不同的进程来说是互斥(exclusive)的，因为FileLock保证只能有一个进程通过lock()或者tryLock()方法获得文件锁。
	 * 
	 * 3. 文件锁的效果是与操作系统相关的。一些系统中文件锁是强制性的（mandatory），就当Java的某进程获得文件锁后，
	 * 操作系统将保证其它进程无法对文件做操作了。而另一些操作系统的文件锁是询问式的(advisory)，意思是说要想拥有进程互斥的效果，
	 * 其它的进程也必须也按照API所规定的那样来申请或者检测文件锁，不然，将起不到进程互斥的功能。
	 * 所以，文档里建议将所有系统都当做是询问式系统来处理，这样程序更加安全也更容易移植。
	 */
	public void lockWriteToFile() {
		String pathname = "D:\\trawe_store\\lock.txt";
		// 文件锁所在文件
		File lockFile = new File(pathname);

		try (FileOutputStream outStream = new FileOutputStream(lockFile, true);
				FileChannel channel = outStream.getChannel();) {

			// 默认是独占锁
			FileLock lock = channel.lock();
			if (lock.isValid()) {
				outStream.write(Thread.currentThread().getName().getBytes());
			}

			// 方法二
			// lock = channel.tryLock();
			// if (lock != null && lock.isValid()) {
			// do something..
			// }
			lock.release();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
