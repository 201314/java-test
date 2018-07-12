package com.gitee.linzl.nio.channels;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

//UDP/IP 可以发送单独的数据报给不同的目的地址。
//同样，DatagramChannel对象也可以接收来自任意地址的数据包。每个到达的数据报都含有关于它来自何处的信息（源地址）。
//DatagramChannel 是模拟无连接协议（如 UDP/IP）。DatagramChannel 对象既可以充当服务器（监听者）也可以充当客户端（发送者）
public class DatagramChannelDemo {

	public static void main(String[] args) throws IOException {
		DatagramChannel channel = DatagramChannel.open();
		DatagramSocket socket = channel.socket();
		socket.bind(new InetSocketAddress(44));
	}
}
