package com.gitee.linzl.nio.channels;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * TCP/IP 面向流
 * 
 * @description ServerSocketChannel负责监听传入的连接和创建新的SocketChannel 对象，它本身从不传输数据
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年5月9日
 */
public class ServerSocketChannelDemo {
	public static final String GREETING = "Hello I must be going.\r\n";

	public static void test() throws IOException {
		int port = 1234;
		ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());

		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.socket().bind(new InetSocketAddress(port));
		// 设置为非阻塞模式
		ssc.configureBlocking(false);
		while (true) {
			System.out.println("Waiting for connections");
			// 非阻塞模式下，无内容，会立即返回null
			// 阻塞模式下，等同于ServerSocket.accept()
			SocketChannel sc = ssc.accept();
			if (sc == null) {
				// no connections, snooze a while
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Incoming connection from: " + sc.socket().getRemoteSocketAddress());
				buffer.rewind();
				sc.write(buffer);
				sc.close();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		ServerSocket serverSocket = serverSocketChannel.socket();
		serverSocketChannel.configureBlocking(false);
		serverSocket.bind(new InetSocketAddress("localhost", 1234));

		while (true) {
			SocketChannel socketChannel = serverSocketChannel.accept();
			if (socketChannel != null) {
				ByteBuffer byteBuffer = ByteBuffer.allocate(512);
				socketChannel.read(byteBuffer);
				byteBuffer.flip();
				System.out.println("server received message: " + getString(byteBuffer));
				byteBuffer.clear();
				String message = "server sending message " + System.currentTimeMillis();
				System.out.println("server sends message: " + message);
				byteBuffer.put(message.getBytes());
				byteBuffer.flip();
				socketChannel.write(byteBuffer);
				break;
			}
		}
		try {
			serverSocketChannel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getString(ByteBuffer buffer) {
		Charset charset;
		CharsetDecoder decoder;
		CharBuffer charBuffer;
		try {
			charset = Charset.forName("UTF-8");
			decoder = charset.newDecoder();
			charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
			return charBuffer.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}
}
