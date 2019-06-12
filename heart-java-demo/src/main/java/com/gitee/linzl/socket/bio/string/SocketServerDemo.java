package com.gitee.linzl.socket.bio.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerDemo {

	public static void main(String[] args) throws IOException {

		try (
				// 1.初始化server,服务端端口号
				ServerSocket server = new ServerSocket(8088);
				// 2.阻塞等待接收数据
				Socket socket = server.accept();) {// 采用JVM自动关闭

			// 3.获取数据输入流
			new Thread(() -> {// 3.获取数据输入流
				try (InputStream is = socket.getInputStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(is));
						OutputStream os = socket.getOutputStream();) {

					String str = null;
					while ((str = reader.readLine()) != null) {
						System.out.println("服务端接收到客户端传递的数据:" + str);
					}

					// 4.服务端对客户端响应
					os.write("我是服务端,你的数据我收到了".getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
