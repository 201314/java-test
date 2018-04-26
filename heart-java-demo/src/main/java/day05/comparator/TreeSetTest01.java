package day05.comparator;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class TreeSetTest01 {
	public static void main(String[] args) {
		Set<User> list = new TreeSet<User>();
		
		User user1=new User(100, "lzl03");
		
		list.add(new User(200, "lzl01"));
		list.add(new User(210, "lzl02"));
		list.add(new User(210, "lzl06"));
		list.add(user1);
		list.add(new User(103, "lzl04"));

//		Iterator<User> iter = list.iterator();
//		while (iter.hasNext()) {
//			System.out.println(iter.next());
//		}
//		System.out.println("----------------");
//		list.remove(user1);
		
		Iterator<User> iter1 = list.iterator();
		while (iter1.hasNext()) {
			System.out.println(iter1.next());
		}
		

	}
}
