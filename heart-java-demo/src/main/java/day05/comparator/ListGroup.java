package day05.comparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 分组器 分组合并,统计同一分组的合计金额
 * 
 * @author linzl 最后修改时间：2014年7月29日
 */
public class ListGroup {

	public static void main(String[] args) {
		List<Group> list = new ArrayList<>();
		list.add(new Group("来源A", 100));
		list.add(new Group("来源B", 200));
		list.add(new Group("来源C", 300));
		list.add(new Group("来源B", 6600));
		list.add(new Group("来源A", 99800));

		List<Group> groupList = getListByGroup(list);
		groupList.stream().forEach((bean) -> {
			System.out.println(bean.getGroup() + "		" + bean.getMoney());
		});
	}

	private static List<Group> getListByGroup(List<Group> list) {
		List<Group> result = new ArrayList<>();
		Map<String, Integer> map = new TreeMap<>();

		list.stream().forEach((bean) -> {
			if (map.containsKey(bean.getGroup())) {
				map.put(bean.getGroup(), map.get(bean.getGroup()) + bean.getMoney());
			} else {
				map.put(bean.getGroup(), bean.getMoney());
			}
		});

		map.entrySet().forEach((entry) -> {
			result.add(new Group(entry.getKey(), entry.getValue()));
		});
		return result;
	}
}

class Group {
	private String group;
	private int money;

	public Group(String group, int money) {
		this.group = group;
		this.money = money;
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
}
