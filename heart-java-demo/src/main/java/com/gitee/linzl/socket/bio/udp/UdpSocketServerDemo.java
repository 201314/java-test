package com.gitee.linzl.socket.bio.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpSocketServerDemo {
	public static void main(String[] args) throws IOException {
		DatagramSocket socket = new DatagramSocket(8800);
		int count = 0;
		while (true) {
			byte[] rec = new byte[1024];
			DatagramPacket recPacket = new DatagramPacket(rec, rec.length);
			socket.receive(recPacket);
			byte[] data = recPacket.getData();
			System.out.println("【BIO-UDP服务端】接收到的数据为:" + new String(data));

			String str = "我是udp服务器响应数据" + count + "==";
			DatagramPacket packet = new DatagramPacket(str.getBytes(), str.getBytes().length,
					recPacket.getSocketAddress());
			socket.send(packet);
			count++;
		}
//		socket.close();//当系统关闭时才将socket关闭
	}
}
