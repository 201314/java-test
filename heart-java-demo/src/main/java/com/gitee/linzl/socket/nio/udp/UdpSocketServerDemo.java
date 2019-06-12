package com.gitee.linzl.socket.nio.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

//UDP/IP 可以发送单独的数据报给不同的目的地址。
//同样，DatagramChannel对象也可以接收来自任意地址的数据包。每个到达的数据报都含有关于它来自何处的信息(源地址)。
//DatagramChannel 是模拟无连接协议(如 UDP/IP)。DatagramChannel 对象既可以充当服务器(监听者)也可以充当客户端(发送者)
public class UdpSocketServerDemo {
	private static ByteBuffer readBuf = ByteBuffer.allocate(1024);

	public static void main(String[] args) throws IOException {
		// 1.打开管道
		DatagramChannel channel = DatagramChannel.open();
		// 设置为非阻塞,会立即返回结果。如果不设置该值，等同于使用原生的ServerSocket写法
		channel.configureBlocking(false);
		// 2.绑定端口
		channel.socket().bind(new InetSocketAddress(8800));
		int count = 0;
		while (true) {
			readBuf.clear();
			channel.receive(readBuf);
			if (channel.isBlocking()) {
				// 如果是阻塞模式,不需要做任何处理
			} else if (readBuf.limit() > 0) {

			}

			readBuf.flip();
			System.out.println("接收到的数据为:" + getString(readBuf));

//			ByteBuffer writeBuf = ByteBuffer.allocate(1024);
//			writeBuf.clear();
//			String str = "我是udp服务器响应数据" + count + "==";
//			writeBuf.put(str.getBytes());
//			writeBuf.flip();
//			channel.send(writeBuf, new InetSocketAddress("127.0.0.1", 8800));
			count++;
		}
//		socket.close();//当系统关闭时才将socket关闭
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
