package com.gitee.linzl.socket.nio.udp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class UdpSocketClient {
	public static void main(String[] args) throws IOException {
		// 1.打开管道
		DatagramChannel channel = DatagramChannel.open();
		// 设置为非阻塞,会立即返回结果。如果不设置该值，等同于使用原生的ServerSocket写法
		channel.configureBlocking(false);

		int count = 0;
		while (count < 5) {
			String str = "我是udp客户端" + count + "==";
			ByteBuffer writeBuf = ByteBuffer.allocate(1024);
			writeBuf.clear();
			writeBuf.put(str.getBytes());
			writeBuf.flip();
			channel.send(writeBuf, new InetSocketAddress(InetAddress.getByName("localhost"), 8800));
			count++;

			ByteBuffer readBuf = ByteBuffer.allocate(1024);
			readBuf.clear();
			channel.receive(readBuf);
			writeBuf.flip();
			System.out.println("客户端接收到服务端:" + channel.getRemoteAddress() + "的数据为:" + getString(readBuf));
		}
		channel.close();
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
