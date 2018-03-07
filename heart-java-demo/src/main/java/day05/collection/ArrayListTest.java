package day05.collection;

import java.util.ArrayList;

public class ArrayListTest {	
	public static void main(String[] args) {
		 ArrayList<String> list=new ArrayList<String>();
		 
		 list.add("A");
		 list.add("不");
		 list.add("no");
		 list.add("who");
		 
		 for (String str : list) {
			 System.out.println(str);			
		 }
		
		 System.out.println("---------------------");
		 
		 ArrayList<String> list1=new ArrayList<String>();
		 
		 list1.addAll(list);
		 list1.add("我是list1");
		 list1.add("我是list1");
		 list1.add("我是list1");
		 list1.add("我是list1");
		 list1.add("who");
		 //list1.addAll(list);
		 
		 for (String str : list1) {
			 System.out.println(str);			
		 }
		 
		 System.out.println("---------------------");
		 
		 System.out.println(list1.indexOf("no"));
		 System.out.println(list1.lastIndexOf("我是list1"));

	}

}
