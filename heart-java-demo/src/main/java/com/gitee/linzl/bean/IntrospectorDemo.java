package com.gitee.linzl.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;

import com.gitee.linzl.classloader.MyClassLoader;

public class IntrospectorDemo {
	public static void main(String[] args) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(MyClassLoader.class);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
	}
}
