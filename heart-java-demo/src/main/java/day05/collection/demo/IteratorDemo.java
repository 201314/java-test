package day05.collection.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 如果一个类，想通过iterator循环出数据，必须实现iterator接口 以下例子为：通过继承ArrayList
 * iterator,增加reserved实现反序iterator
 * 
 * @author linzl
 * 
 */
public class IteratorDemo extends ArrayList<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IteratorDemo(Collection<String> col) {
		super(col);
	}

	public Iterable<String> reserved() {
		return new Iterable<String>() {
			@Override
			public Iterator<String> iterator() {
				return new Iterator<String>() {
					int current = size() - 1;

					@Override
					public boolean hasNext() {
						return current > -1;
					}

					@Override
					public String next() {
						return (String) get(current--);
					}

					@Override
					public void remove() {
					}
				};
			}
		};
	}

	public static void main(String[] args) {
		IteratorDemo demo = new IteratorDemo(Arrays.asList("hello who are you?".split(" ")));
		for (String string : demo) {
			System.out.println("-->" + string);
		}
		System.out.println("反转");
		for (String string : demo.reserved()) {
			System.out.println("-->" + string);
		}

		Integer[] ia = { 1, 2, 3, 4, 5, 6 };
		List list = Arrays.asList(ia);
		System.out.println("修改前--》" + list.toString());
		list.set(0, 100);// 修改第0个
		System.out.println("修改后--》" + list.toString());
		System.out.println("修改后ia--》" + Arrays.toString(ia));
	}
}
