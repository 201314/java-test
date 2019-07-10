package com.gitee.linzl.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * TCP/IP 面向流: ServerSocketChannel负责监听传入的连接和创建新的SocketChannel 对象，它本身从不传输数据
 * 
 * @description
 * 
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2019年6月12日
 */
public class SocketServerChannelDemo {
	private static String content = "我是服务端,你的数据我收到了";
	private static ByteBuffer readBuf = ByteBuffer.allocate(1024);
	private static ByteBuffer writeBuf = ByteBuffer.allocate(1024);

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// 1. 打开管道 channel
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		// 设置为非阻塞,会立即返回结果。如果设置为true,则代码与原生服务端Server一样
		serverSocketChannel.configureBlocking(false);
		// 2.初始化server,服务端端口号
		serverSocketChannel.bind(new InetSocketAddress(8088));
		// 3.注册到管家selector并监听接收
		// 设置为非阻塞才能使用selector模式,如果是阻塞模式下，注册选择器会报java.nio.channels.IllegalBlockingModeException
		Selector selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		// 4.循环监听
		while (true) {
			int selectNum = selector.select();
			Set<SelectionKey> set = selector.selectedKeys();
			Iterator<SelectionKey> iter = set.iterator();
			// 如果此值永远为false,将会出现空轮询BUG
			while (iter.hasNext()) {
				SelectionKey key = iter.next();
				iter.remove();// 确保不重复

				if (key.isValid()) {// 判断是否有效的Key
					if (key.isAcceptable()) {// 监听中
						try {
							ServerSocketChannel server = (ServerSocketChannel) key.channel();
							SocketChannel socket = server.accept();
							// 设置客户端为非阻塞模式
							socket.configureBlocking(false);
							socket.register(selector, SelectionKey.OP_READ);
						} catch (ClosedChannelException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else if (key.isReadable()) {// 可读状态
						try {
							SocketChannel socket = (SocketChannel) key.channel();
							readBuf.clear();
							int count = socket.read(readBuf);
							if (count > 0) {
								readBuf.flip();
								System.out.println("【NIO服务端】接收到客户端传递的数据:" + getString(readBuf));
								writeBuf.clear();
								writeBuf.put(content.getBytes());
								writeBuf.flip();
								socket.write(writeBuf);
							} else {
								key.channel().close();
								key.cancel();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		// 5.应用关闭时，才将其关闭
//			serverSocketChannel.close();
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
