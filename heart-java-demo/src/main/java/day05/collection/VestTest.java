package day05.collection;

import java.util.List;
import java.util.Vector;

public class VestTest {
	public static void main(String[] args) {
		List<String> list=new Vector<String>();
		list.add("韦小宝");
		list.add("陈敬南");
		list.add("方世玉");
		
		for (String str : list) {
			System.out.println(str);
		}
		
		System.out.println("------------");
		list.remove("方世玉");
		for (String str : list) {
			System.out.println(str);
		}
		
		System.out.println("------------");
		list.set(1,"陈家洛");
		for (String str : list) {
			System.out.println(str);
		}
		System.out.println(list.contains("陈敬南"));
		
	}
}
