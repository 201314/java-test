package com.gitee.linzl.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;

/**
 * 使用最多的socket通道类,实体的传输可采用转为JSON
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2019年6月12日
 */
public class SocketChannelClientDemo {
	private static String content = "我是客户端发送的数据";
	private static ByteBuffer writeBuf = ByteBuffer.allocate(1024);
	private static ByteBuffer readBuf = ByteBuffer.allocate(1024);

	public static void main(String[] args) throws UnknownHostException, IOException {
		// 1.打开管道 channel
		SocketChannel socketChannel = SocketChannel.open();
		// 设置为非阻塞,会立即返回结果。如果设置为true,则代码与原生服务端Server一样
		socketChannel.configureBlocking(false);// 设置为非阻塞模式
		// 2.连接Socket服务端
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 8088));
		// 3.注册到selector管家
		// 设置为非阻塞才能使用selector模式,如果是阻塞模式下，注册选择器会报java.nio.channels.IllegalBlockingModeException
		Selector selector = Selector.open();
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		// 4.监听SelectionKey的变化
		SocketChannel channel = null;
		while (true) {
			selector.select();
			Set<SelectionKey> set = selector.selectedKeys();
			Iterator<SelectionKey> iter = set.iterator();
			while (iter.hasNext()) {
				SelectionKey key = iter.next();
				iter.remove();// 确保不重复
				if (key.isValid()) {
					if (key.isConnectable()) {// 连接成功即可开始写数据
						channel = (SocketChannel) key.channel();
						channel.configureBlocking(false);

						if (channel.isConnectionPending()) {
							if (channel.finishConnect()) { // 完成连接操作，不调用会报异常java.nio.channels.NotYetConnectedException
								writeBuf.clear();
								writeBuf.put(content.getBytes());
								writeBuf.flip();
								channel.write(writeBuf);
							} else {
								key.channel().close();
								key.cancel();
							}
							channel.register(selector, SelectionKey.OP_READ);
							// 注册监听源代码中，最后等同以下语句
//							key.interestOps(SelectionKey.OP_READ);
						}
					} else if (key.isReadable()) {// 缓存区可读
						readBuf.clear();
						channel = (SocketChannel) key.channel();
						channel.read(readBuf);
						readBuf.flip();
						System.out.println("客户端收到服务端响应的数据:" + getString(readBuf));
						channel.register(selector, SelectionKey.OP_WRITE);
					} else if (key.isWritable()) {// 缓存区可写
						writeBuf.clear();
						writeBuf.put(content.getBytes());
						writeBuf.flip();
						channel = (SocketChannel) key.channel();
						channel.write(writeBuf);
						channel.register(selector, SelectionKey.OP_READ);
					}
				}
			}
		}
	}

	public static String getString(ByteBuffer buffer) {
		Charset charset = null;
		CharsetDecoder decoder = null;
		CharBuffer charBuffer = null;
		try {
			charset = Charset.forName("UTF-8");
			decoder = charset.newDecoder();
			// charBuffer = decoder.decode(buffer);//用这个的话，只能输出来一次结果，第二次显示为空
			charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
			return charBuffer.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}
}
