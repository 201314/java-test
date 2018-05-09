package com.gitee.linzl.nio.channels;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * 使用最多的socket通道类
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年5月9日
 */
public class SocketChannelDemo {
	public static void main(String[] args) throws Exception {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress("localhost", 1234));
		String message = "client sending message " + System.currentTimeMillis();
		ByteBuffer byteBuffer = ByteBuffer.allocate(512);
		byteBuffer.clear();
		System.out.println("client sends message: " + message);
		byteBuffer.put(message.getBytes());
		byteBuffer.flip();
		socketChannel.write(byteBuffer);

		while (true) {
			byteBuffer.clear();
			int readBytes = socketChannel.read(byteBuffer);
			if (readBytes > 0) {
				byteBuffer.flip();
				System.out.println("client receive message: " + getString(byteBuffer));
				break;
			}
		}

		try {
			socketChannel.close();
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
