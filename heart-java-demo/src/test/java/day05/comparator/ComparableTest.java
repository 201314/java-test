package day05.comparator;

import java.util.Arrays;

import org.junit.Test;

public class ComparableTest {

	@Test
	public void testComparable() {
		ComparableDemo[] temp = { new ComparableDemo("linzl", 25), new ComparableDemo("linwc", 20),
				new ComparableDemo("linys", 56), new ComparableDemo("linsj", 47), new ComparableDemo("linwb", 83),
				new ComparableDemo("lins", 25) };
		Arrays.sort(temp);
		for (int i = 0; i < temp.length; i++) {
			ComparableDemo stu = temp[i];
			System.out.println(stu.getName());
			System.out.println(stu.getAge());
			System.out.println();
		}
	}

}
