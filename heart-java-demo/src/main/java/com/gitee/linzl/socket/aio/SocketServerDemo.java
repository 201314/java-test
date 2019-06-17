package com.gitee.linzl.socket.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.concurrent.ExecutionException;

public class SocketServerDemo {
	public static void main(String[] args) throws IOException {
		try (
				// 1.初始化socket server端，并绑定端口
				AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open()
						.bind(new InetSocketAddress(8088));) {
			// 2.开始接收数据
			listener.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
				@Override
				public void completed(AsynchronousSocketChannel ch, Void v) {
					// 接收下一个监听
					// 当有下一个客户端接入的时候 直接调用Server的accept方法，这样反复执行下去，保证多个客户端都可以阻塞
					listener.accept(v, this);

					ByteBuffer readBuf = ByteBuffer.allocate(1024);
					ByteBuffer writeBuf = ByteBuffer.allocate(1024);
					readBuf.clear();
					writeBuf.clear();
					try {
						Integer readCnt = ch.read(readBuf).get();
//						ch.read(dst, attachment, handler);
						if (readCnt > 0) {
							readBuf.flip();
							System.out.println("【AIO服务端】接收到客户端传递的数据:" + getString(readBuf));
							writeBuf.put("我是服务端,你的数据我收到了".getBytes());
							writeBuf.flip();
							ch.write(writeBuf);
//							ch.write(src, attachment, handler);
						}
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void failed(Throwable exc, Void v) {
					System.out.println("【AIO服务端】异步失败了");
				}
			});

			// 因为AIO不会阻塞调用进程，因此必须在主进程阻塞，才能保持进程存活。
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
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
