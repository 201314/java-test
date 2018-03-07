package com.linzl.concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 两个线程，一个加10，一个加8，循环100输出结果
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年3月3日
 */
public class ConcurrentHashMapTest {
	private int x = 100;

	// 在spring中经常使用
	private Map<String, String> map = new ConcurrentHashMap<String, String>();

}
