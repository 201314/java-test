package com.gitee.linzl.socket.bio.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpSocketClient {
	public static void main(String[] args) throws IOException {
		int count = 0;
		DatagramSocket socket = new DatagramSocket();
		while (count < 5) {
			String str = "我是udp客户端" + count + "==";
			DatagramPacket packet = new DatagramPacket(str.getBytes(), str.getBytes().length,
					InetAddress.getByName("localhost"), 8800);
			socket.send(packet);
			count++;

			byte[] rec = new byte[1024];
			DatagramPacket recPacket = new DatagramPacket(rec, rec.length);
			socket.receive(recPacket);
			byte[] data = recPacket.getData();
			System.out.println("【BIO-UDP客户端】接收到服务端:" + recPacket.getSocketAddress() + "的数据为:" + new String(data));
		}
		socket.close();
	}
}
