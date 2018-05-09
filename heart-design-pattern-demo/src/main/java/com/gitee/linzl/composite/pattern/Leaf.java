package com.gitee.linzl.composite.pattern;

public class Leaf extends Component {

	public Leaf(String name) {
		super(name);
	}

	@Override
	public void add(Component c) {
		System.out.println("Can't add to a leaf");
	}

	@Override
	public void remove(Component c) {
		System.out.println("Can't remove from a leaf");
	}

	@Override
	public void display(int depth) {
		String str = "";
		while (depth > 0) {
			str += "--";
			depth--;
		}
		System.out.println(str + getName());
	}

}
