package day05.collection.demo;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class MapDemo {
	public static void main(String[] args) {
		Map hashMap = new HashMap();
		hashMap.put("省东1", 1);
		hashMap.put("2哈哈", 2);
		hashMap.put("没劲0", 3);
		hashMap.put("-1负数", 4);
		hashMap.put("3", 3);
		hashMap.put("5", null);
		hashMap.put(null, "无值");
		System.out.println("hashMap-->" + hashMap);
		
		Map link = new LinkedHashMap();
		link.put("省东1", 1);
		link.put("2哈哈", 2);
		link.put("没劲0", 3);
		link.put("-1负数", 4);
		link.put("3", 3);
		link.put("5", null);
		link.put(null, "无值");
		System.out.println("link-->" + link);

		// key和value均不允许null
		Map hashTable = new Hashtable();
		hashTable.put("省东1", 1);
		hashTable.put("2哈哈", 2);
		hashTable.put("没劲0", 3);
		hashTable.put("-1负数", 4);
		hashTable.put("3", 3);
		System.out.println("hashTable-->" + hashTable);

		Map treeMap = new TreeMap();
		treeMap.put("省东1", 1);
		treeMap.put("2哈哈", 2);
		treeMap.put("没劲0", 3);
		treeMap.put("-1负数", 4);
		treeMap.put("3", 3);
		treeMap.put("5", null);
		System.out.println("treeMap-->" + treeMap);

		Map weakHashMap = new WeakHashMap();
		weakHashMap.put("省东1", 1);
		weakHashMap.put("2哈哈", 2);
		weakHashMap.put("没劲0", 3);
		weakHashMap.put("-1负数", 4);
		weakHashMap.put("3", 3);
		weakHashMap.put("5", null);
		weakHashMap.put(null, "无值");
		System.out.println("weakHashMap-->" + weakHashMap);

		Map concurrent = new ConcurrentHashMap();
		concurrent.put("省东1", 1);
		concurrent.put("2哈哈", 2);
		concurrent.put("没劲0", 3);
		concurrent.put("-1负数", 4);
		concurrent.put("3", 3);
		// concurrent.put("5", null);
		// concurrent.put(null, "无值");
		System.out.println("concurrent-->" + concurrent);

		Map concurrentSkip = new ConcurrentSkipListMap();
		concurrentSkip.put("省东1", 1);
		concurrentSkip.put("2哈哈", 2);
		concurrentSkip.put("没劲0", 3);
		concurrentSkip.put("-1负数", 4);
		concurrentSkip.put("3", 3);
		// concurrentSkip.put("5", null);
		// concurrentSkip.put(null, "无值");
		System.out.println("concurrentSkip-->" + concurrentSkip);
	}
}
