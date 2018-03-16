package day05.comparator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class ComparatorTest {

	@Test
	public void testComparator() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, 9);

		List<JavaBean> list = new ArrayList<JavaBean>();
		cal.set(Calendar.DAY_OF_MONTH, 12);
		list.add(new JavaBean("来源A", 100, cal.getTime()));

		cal.set(Calendar.DAY_OF_MONTH, 11);
		list.add(new JavaBean("来源B", 200, cal.getTime()));

		cal.set(Calendar.DAY_OF_MONTH, 13);
		list.add(new JavaBean("来源C", 300, cal.getTime()));

		cal.set(Calendar.DAY_OF_MONTH, 10);
		list.add(new JavaBean("来源D", 100, cal.getTime()));

		cal.set(Calendar.DAY_OF_MONTH, 9);
		list.add(new JavaBean("来源E", 300, cal.getTime()));

		Collections.sort(list, new ComparatorDemo());
		for (int i = 0; i < list.size(); i++) {
			JavaBean demo = list.get(i);
			System.out.println(demo.getGroup());
		}
	}
}
