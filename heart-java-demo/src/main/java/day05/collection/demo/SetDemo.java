package day05.collection.demo;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

public class SetDemo {
	public static void main(String[] args) {
		// 同HashMap
		Set hashSet = new HashSet();
		hashSet.add("省东1");
		hashSet.add("2哈哈");
		hashSet.add("没劲0");
		System.out.println("hashSet--》" + hashSet);

		Set link = new LinkedHashSet();
		link.add("省东1");
		link.add("2哈哈");
		link.add("没劲0");
		System.out.println("link--》" + link);

		Set treeSet = new TreeSet();
		treeSet.add("省东1");
		treeSet.add("2哈哈");
		treeSet.add("没劲0");
		System.out.println("treeSet--》" + treeSet);

		Set copyOnWrite = new CopyOnWriteArraySet();

		Set concurrent = new ConcurrentSkipListSet();

	}
}
