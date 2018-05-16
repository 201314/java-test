package day05.comparator;

import java.util.Comparator;
import java.util.Date;

/**
 * 排序比较器 Comparator 是一种算法的实现，在需要容器集合实现比较功能的时候， 来指定这个比较器，这可以看成一种设计模式，将算法和数据分离
 * 
 * @author linzl 最后修改时间：2014年9月20日
 */
public class ComparatorDemo implements Comparator<Object> {// 外比较器，策略模式

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
