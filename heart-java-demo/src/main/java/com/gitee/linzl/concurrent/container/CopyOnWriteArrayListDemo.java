package com.gitee.linzl.concurrent.container;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWrite优点：
 * 
 * 并发容器用于读多写少的并发场景。因为只在写的时候加会锁，读并不会。
 * 
 * 比如白名单，黑名单，商品类目的访问和更新场景，假如我们有一个搜索网站，
 * 
 * 用户在这个网站的搜索框中，输入关键字搜索内容，但是某些关键字不允许被搜索。
 * 
 * 这些不能被搜索的关键字会被放在一个黑名单当中，黑名单每天晚上更新一次。
 * 
 * 当用户搜索时，会检查当前关键字在不在黑名单当中，如果在，则提示不能搜索。
 * 
 * 1、在初始化时，指定容器大小，避免扩容的开销
 * 
 * 2、add的时候，尽可能使用批量添加，减少容器的复制
 * 
 * 缺点：即内存占用问题和数据一致性问题。
 * 
 * 1、内存占用问题。
 * 
 * 因为CopyOnWrite的写时复制机制，所以在进行写操作的时候，内存里会同时驻扎两个对象的内存，
 * 旧的对象和新写入的对象（注意:在复制的时候只是复制容器里的引用，只是在写的时候会创建新对象添加到新容器里，而旧容器的对象还在使用，所以有两份对象内存）。
 * 如果这些对象占用的内存比较大，比如说200M左右，那么再写入100M数据进去，内存就会占用300M，那么这个时候很有可能造成频繁的Yong GC和Full
 * GC。之前我们系统中使用了一个服务由于每晚使用CopyOnWrite机制更新大对象，造成了每晚15秒的Full GC，应用响应时间也随之变长。
 * 
 * 针对内存占用问题，可以通过压缩容器中的元素的方法来减少大对象的内存消耗，比如，如果元素全是10进制的数字，可以考虑把它压缩成36进制或64进制。
 * 或者不使用CopyOnWrite容器，而使用其他的并发容器，如ConcurrentHashMap。
 * 
 * 2、数据一致性问题。
 * 
 * CopyOnWrite容器只能保证数据的最终一致性，不能保证数据的实时一致性。
 * 所以如果你希望写入的的数据，马上能读到，请不要使用CopyOnWrite容器。
 * 
 * 例如：一个线程在写操作，此时进行复制，写入对象，如果此时另一个线程在读，则会读到旧的数据。
 * 
 * @description 线程安全
 * 
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年5月23日
 */
public class CopyOnWriteArrayListDemo {
	public static void main(String[] args) throws InterruptedException {
		List<String> a = new ArrayList<String>();
		a.add("a");
		a.add("b");
		a.add("c");
		CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>(a);
		Thread t = new Thread(new Runnable() {
			int count = -1;

			@Override
			public void run() {
				while (count < 10) {
					list.add(String.valueOf(count++));
				}
			}
		});
		t.setDaemon(true);
		t.start();
		Thread.sleep(3);
		for (String s : list) {
			// 发现每次的hascode不一样，因为在动态添加元素，元素发生变化。
			System.out.println(list.hashCode());
			System.out.println(s);
		}
	}
}
