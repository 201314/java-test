package com.gitee.linzl.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 如果通过重写loadClass来实现类加载器，这样会破坏查找加载的规则被修改，所以直接导致ClassCastException,
 * 
 * 因为一个是通过MyClassLoader加载，一个是通过AppClassLoader加载
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年3月12日
 */
public class MyClassLoader extends ClassLoader {

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classData = getClassData(name); // 根据类的二进制名称,获得该class文件的字节码数组
		if (classData == null) {
			throw new ClassNotFoundException();
		}
		return defineClass(name, classData, 0, classData.length);// 将class的字节码数组转换成Class类的实例
	}

	private byte[] getClassData(String name) {
		InputStream is = null;
		try {
			String dir = "D:/log";
			String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";

			File file = new File(dir, fileName);
			is = new FileInputStream(file);

			byte[] buff = new byte[1024 * 4];
			int len = -1;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((len = is.read(buff)) != -1) {
				baos.write(buff, 0, len);
			}
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
