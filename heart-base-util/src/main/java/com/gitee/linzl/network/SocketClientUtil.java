package com.gitee.linzl.network;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;

@Slf4j
public class SocketClientUtil {
	private Socket client;

	/**
	 * 获取Socket工具类实例，默认连接超时时间为30秒
	 * 
	 * @param host
	 *            主机名或IP
	 * @param port
	 *            端口号
	 * @return Socket工具类实例
	 */
	public SocketClientUtil(String host, int port) {
		this(host, port, 30000);
	}

	/**
	 * 获取Socket工具类实例
	 * 
	 * @param host
	 *            主机名或IP
	 * @param port
	 *            端口号
	 * @param timeout
	 *            连接超时时间(单位毫秒)
	 * @return Socket工具类实例
	 */
	public SocketClientUtil(String host, int port, int timeout) {
		client = new Socket();
		try {
			client.connect(new InetSocketAddress(host, port), timeout);
			log.debug("Socket连接成功!");
		} catch (IOException e) {
			log.error("Socket连接SocketServer时抛异常,异常信息:{}", e);
		}
	}

	/**
	 * 给SocketServer发送String类型的数据，默认的编码方式为UTF-8
	 * 
	 * @param data
	 *            发送的String类型的数据
	 */
	public void send(String data) {
		send(data, Charset.forName("GBK"));
	}

	/**
	 * 给SocketServer发送String类型的数据
	 * 
	 * @param data
	 *            发送的String类型的数据
	 * @param charset
	 *            String类型的数据的编码方式
	 */
	public void send(String data, Charset charset) {
		try {
			client.getOutputStream().write(data.getBytes(charset));
			client.shutdownOutput();
		} catch (Exception e) {
			log.error("发送数据时将字符串转换成【{}】编码类型的字节码时抛异常了，异常信息如下:{}", charset, e);
		}
	}

	/**
	 * 接收SocketServer响应的String类型的数据，默认的编码方式为UTF-8
	 * 
	 * @return
	 */
	public String receive() {
		return receive(Charset.forName("GBK"));
	}

	/**
	 * 接收SocketServer响应的String类型的数据
	 * 
	 * @param charset
	 *            响应的String类型的数据的编码方式
	 * @return 响应的String类型的数据
	 */
	public String receive(Charset charset) {
		StringBuilder sb = new StringBuilder(64);
		try {
			int rs = 0;
			byte[] bs = new byte[1024];
			while ((rs = client.getInputStream().read(bs)) != -1) {
				String d = new String(bs, 0, rs, charset);
				sb.append(d);
				return sb.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 给SocketServer发送String类型的数据，并接收SocketServer响应的String类型的数据，默认的编码方式为UTF-8
	 * 
	 * @param data
	 *            发送的String类型的数据
	 * @return 响应的String类型的数据
	 */
	public String sendAndReceive(String data) {
		return sendAndReceive(data, Charset.forName("GBK"));
	}

	/**
	 * 给SocketServer发送String类型的数据，并接收SocketServer响应的String类型的数据
	 * 
	 * @param data
	 *            发送的String类型的数据
	 * @param charset
	 *            String类型的数据的编码方式
	 * @return 响应的String类型的数据
	 */
	public String sendAndReceive(String data, Charset charset) {
		send(data, charset);
		return receive(charset);
	}

	/**
	 * 关闭Socket连接
	 */
	public void close() {
		try {
			if (null != client) {
				client.close();
				client = null;
				log.debug("Socket成功关闭!");
			}
		} catch (Exception e) {
			log.error("关闭Socket时抛异常了，异常信息如下:{}", e);
		}
	}

	public static void main(String[] args) throws Exception {
		SocketClientUtil clientUtil = new SocketClientUtil("10.63.0.37", 29211);
		// 加上你的报文
		String data = "000001039100970000063000002019012218473036EB6EB5972019012218473041659700001100000000001  20190122184730";// true,5648859,541,密钥技术部,
		String receive = clientUtil.sendAndReceive(data, Charset.forName("GBK"));
		System.out.println("socket返回结果:" + receive);
		clientUtil.close();
	}
}
