package day05.collection;

import java.util.Set;
import java.util.TreeSet;

public class TreeSetTest {
	public static void main(String[] args) {
		Set<String> list = new TreeSet<String>();

		list.add("abc");
		list.add("bcd");
		list.add("cde");
		list.add("def");
		list.add("0");
		list.add("Acd");

		for (String str : list) {
			System.out.println(str);
		}
		System.out.println("---------------------");

		list.remove("A");
		for (String str : list) {
			System.out.println(str);
		}
		System.out.println("---------------------");
		System.out.println(list.contains("不"));

	}

}
