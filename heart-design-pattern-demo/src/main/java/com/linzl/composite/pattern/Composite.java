package com.linzl.composite.pattern;

import java.util.ArrayList;
import java.util.List;

public class Composite extends Component {
	private List<Component> nodeList = new ArrayList<Component>();

	public Composite(String name) {
		super(name);
	}

	@Override
	public void add(Component c) {
		nodeList.add(c);
	}

	@Override
	public void remove(Component c) {
		nodeList.remove(c);
	}

	@Override
	public void display(int depth) {
		String str = "";
		while (depth > 0) {
			str += "--";
			depth--;
		}
		System.out.println(str + getName());
		for (Component c : nodeList) {
			c.display(depth + 6 - nodeList.size());
		}
	}

}
