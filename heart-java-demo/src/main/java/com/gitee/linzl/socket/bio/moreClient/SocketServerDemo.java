package com.gitee.linzl.socket.bio.moreClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 服务端要处理多客户端请求，则意味着每次接收到一个请求后要启动一个线程单独处理
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2019年6月12日
 */
public class SocketServerDemo {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// 1.初始化server,服务端端口号
		ServerSocket server = new ServerSocket(8088);
		ExecutorService pool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 50, 120L,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000));
		while (true) {
			// 2.阻塞等待接收数据
			Socket socket = server.accept();
			pool.submit(() -> {
				// 3.获取数据输入流
				try {
					InputStream is = socket.getInputStream();
					ObjectInputStream ois = new ObjectInputStream(is);
					User user = (User) ois.readObject();
					System.out.println(
							"我是服务端,收到客户端:" + socket.getInetAddress().getHostAddress() + ",数据为:" + user.getName());
					// 4.服务端对客户端响应
					OutputStream os = socket.getOutputStream();
					os.write("我是服务端,你的数据我收到了".getBytes());
					os.flush();

					os.close();
					is.close();
					socket.close();
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			});
		}
//		server.close();//不能关闭,只有当系统关闭时才将socketServer关闭
	}
}
