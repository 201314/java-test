package com.gitee.linzl.socket.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SocketServerDemo {
	public static void baseString() {
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
						System.out.println("【BIO服务端】接收到客户端传递的数据:" + str);
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

	public static void moreClient() {
		try (// 1.初始化server,服务端端口号
				ServerSocket server = new ServerSocket(8088);) {

			ExecutorService pool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 50, 120L,
					TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000));
			while (true) {
				// 2.阻塞等待接收数据
				Socket socket = server.accept();
				pool.submit(() -> {
					try (InputStream is = socket.getInputStream();
							BufferedReader reader = new BufferedReader(new InputStreamReader(is));
							OutputStream os = socket.getOutputStream();) {

						String str = null;
						while ((str = reader.readLine()) != null) {
							System.out.println("【BIO服务端】接收到客户端传递的数据:" + str);
						}

						// 4.服务端对客户端响应
						os.write("我是服务端,你的数据我收到了".getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}

					// 3.获取数据输入流
//					try (InputStream is = socket.getInputStream();
//
//							ObjectInputStream ois = new ObjectInputStream(is);
//
//							OutputStream os = socket.getOutputStream();) {
//
//						User user = (User) ois.readObject();
//						System.out.println(
//								"【BIO服务端】收到客户端:" + socket.getInetAddress().getHostAddress() + ",数据为:" + user.getName());
//						// 4.服务端对客户端响应
//						os.write("我是服务端,你的数据我收到了".getBytes());
//					} catch (IOException | ClassNotFoundException e) {
//						e.printStackTrace();
//					} finally {
//						try {
//							socket.close();
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
				});
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		moreClient();
	}
}
