package com.gitee.linzl.socket.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class SocketClientDemo {
	public static void main(String[] args) throws Exception {
		try (// 1.初始化socket客户端
				AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();) {
			// 2.连接server服务端
			socketChannel.connect(new InetSocketAddress("127.0.0.1", 8088));
//			socketChannel.connect(remote, attachment, handler);//可使用回调方式
			ByteBuffer writeBuf = ByteBuffer.allocate(1024);
			writeBuf.clear();
			writeBuf.put("我是中国人".getBytes());
			writeBuf.flip();
			Integer writeCnt = socketChannel.write(writeBuf).get();
//			socketChannel.write(src, attachment, handler);//可使用回调方式
			if (writeCnt > 0) {
				ByteBuffer readBuf = ByteBuffer.allocate(1024);
				readBuf.clear();
				Integer readCnt = socketChannel.read(readBuf).get();
//				socketChannel.read(dst, attachment, handler);//可使用回调方式
				if (readCnt > 0) {
					readBuf.flip();
					System.out.println("【AIO客户端】收到服务端响应的数据:" + getString(readBuf));
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
