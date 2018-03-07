package day05.collection.demo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ArrayList , LinkedList
 * 
 * @author linzl 最后修改时间：2015年1月18日
 */
public class ListDemo {

	public static void main(String[] args) {
		List arrayList = new ArrayList();
		// 扩大到1.5倍数组
		arrayList.add("");

		// 链表数组，类似C语言中的指针
		List linkedList = new LinkedList();
		linkedList.add("1");
		linkedList.add("2");
		linkedList.add("");
		linkedList.add("有没搞错");
		linkedList.add("最后一个");

		// 加锁，效率较低
		List stack = new Stack();
		// 扩大到原来的2倍
		stack.add("第一天");
		stack.add("hello");

		List copyOnWrite = new CopyOnWriteArrayList();
		copyOnWrite.add("hello");
		copyOnWrite.add("复制定稿");
		copyOnWrite.add("why are you");
		System.out.println(copyOnWrite);

	}

}
