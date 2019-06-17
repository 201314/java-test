package com.gitee.linzl.socket.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClientDemo {
	/**
	 * socket传递字符串例子
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static void baseString() throws UnknownHostException, IOException {
		// 1.初始化socket客户端
		Socket socket = new Socket("127.0.0.1", 8088);
		// 2.获取输出流
		OutputStream os = socket.getOutputStream();
		os.write("我是中国人".getBytes());
		os.flush();
		// 关闭输出流
		socket.shutdownOutput();

		// 3.获取服务器的响应
		InputStream is = socket.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String str = null;
		while ((str = reader.readLine()) != null) {
			System.out.println("【BIO客户端】收到服务端响应的数据:" + str);
		}
		reader.close();
		is.close();
		os.close();
		socket.close();

	}

	/**
	 * 多客户端访问,传递对象例子
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static void moreClient() throws UnknownHostException, IOException {
		// 1.初始化socket客户端
		Socket socket = new Socket("127.0.0.1", 8088);
		// 2.获取输出流
		OutputStream os = socket.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(new User("test", 21));
		oos.flush();
		os.flush();
		// 关闭输入流
		socket.shutdownOutput();

		// 3.获取服务器的响应
		InputStream is = socket.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String str = null;
		while ((str = reader.readLine()) != null) {
			System.out.println("【BIO客户端】收到服务端响应的数据:" + str);
		}
		reader.close();
		is.close();
		os.close();
		socket.close();
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
	}
}
