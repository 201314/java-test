package com.gitee.linzl.concurrent.container;

import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 跳表
 * 
 * 时间换空间的算法，跳表的遍历输出是有序的
 * 
 * 时间复杂度 O（log n）
 * 
 * 哈希表 跳表操作
 * 
 * @description
 * 
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2019年7月23日
 */
public class ConcurrentSkipListMapDemo {

	public static void main(String[] args) {
		ConcurrentSkipListMap<String, Double> map = new ConcurrentSkipListMap<>(Comparator.reverseOrder());
		map.put("Thinking in java", 9.0);
		map.put("java concurrency111", 8.9);
		map.put("java cookbook", 8.6);
		map.put("java concurrency222", 8.9);
		System.out.println(map);
	}
}
