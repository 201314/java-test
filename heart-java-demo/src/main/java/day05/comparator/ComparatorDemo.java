package day05.comparator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 排序比较器 Comparator 是一种算法的实现，在需要容器集合实现比较功能的时候， 来指定这个比较器，这可以看成一种设计模式，将算法和数据分离
 * 
 * @author linzl 最后修改时间：2014年9月20日
 */
public class ComparatorDemo implements Comparator {

	// JavaBean 按照金额从大到小排序，同金额的按时间从大到小排序
	// 比较器是按-1 ,0 ,1 自然升序排序的
	public int compare(Object firstObj, Object secondObj) {
		JavaBean first = (JavaBean) firstObj;
		JavaBean second = (JavaBean) secondObj;
		if (first.getMoney() > second.getMoney()) {
			return -1;
		} else if (first.getMoney() < second.getMoney()) {
			return 1;
		}

		if (first.getDate().after(second.getDate())) {
			return -1;
		} else if (first.getDate().before(second.getDate())) {
			return 1;
		}
		return 0;
	}

	public static void main(String[] args) {
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

class JavaBean {

	private String group;
	private int money;
	private Date date;

	public JavaBean() {
	}

	public JavaBean(String group, int money) {
		this.group = group;
		this.money = money;
	}

	public JavaBean(String group, int money, Date date) {
		this.group = group;
		this.money = money;
		this.date = date;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
