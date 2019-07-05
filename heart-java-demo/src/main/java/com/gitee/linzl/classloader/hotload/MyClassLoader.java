package com.gitee.linzl.classloader.hotload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

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
	private String classpath;

	public MyClassLoader(String classpath) {
		this.classpath = classpath;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] data = this.loadClassData(name);
		return this.defineClass(name, data, 0, data.length);
	}

	/**
	 * 加载class文件中的内容
	 * 
	 * @param name
	 * @return
	 */
	private byte[] loadClassData(String name) {
		name = name.substring(name.lastIndexOf(".") + 1) + ".class";

		try (FileInputStream is = new FileInputStream(new File(classpath, name));
				ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
			byte[] buff = new byte[1024 * 4];
			int len = -1;
			while ((len = is.read(buff)) != -1) {
				baos.write(buff, 0, len);
			}
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
