package com.gitee.linzl.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * java调用dll例子
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年10月11日
 */
public class DllTest {

	public interface Dll extends Library {
		// 在window下要用使用目录win32-x86-64
		Dll instance = (Dll) Native.load("DllTest", Dll.class);

		public int add(int a, int b);

		public int substract(int a, int b);

		public void printHello();
	}

	public static void main(String[] args) {
		int sum = Dll.instance.add(5, 3);
		int sub = Dll.instance.substract(5, 3);
		System.out.println("add(5,3) = " + sum);
		System.out.println("substract(5,3) = " + sub);
		Dll.instance.printHello();
	}
}
