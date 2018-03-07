package com.linzl.single.pattern;

import java.util.HashMap;
import java.util.Map;

/**
 * 登记式单例模式：先将某些类的实例化登记 在Map,待需要时从Map取出来用 如：线程池 也是同样的道理 Connection
 * conn=***.getConnection() 此方法和 享元模式 类似
 * 
 * @author GDCC i心灵鸡汤you email:2225010489@qq.com 2013-4-27 下午09:13:42
 */
public class RegisterSingleton {
	private static Map<String, RegisterSingleton> map = new HashMap<String, RegisterSingleton>();
	static {
		// 登记 入户
		RegisterSingleton register = new RegisterSingleton();
		map.put(register.getClass().getName(), register);
	}

	public static RegisterSingleton getInstance(String name)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (name == null) {
			System.out.println("抛出异常");
			return null;
		} else {
			if (map.get(name) == null)
				map.put(name, (RegisterSingleton) Class.forName(name).newInstance());
		}
		return map.get(name);
	}

	// map中实例总数
	public int getLength() {
		return map.size();
	}
}
