package com.linzl.composite.pattern;

public class Test {

	public static void main(String[] args) {
		Component c = new Composite("root");
		c.add(new Leaf("Leaf A"));
		c.add(new Leaf("Leaf B"));

		Component cpX = new Composite("Composite X");
		cpX.add(new Leaf("Leaf XA"));
		cpX.add(new Leaf("Leaf XB"));

		c.add(cpX);

		Component cpXY = new Composite("Composite XY");
		cpXY.add(new Leaf("Leaf XYA"));
		cpXY.add(new Leaf("Leaf XYB"));

		cpX.add(cpXY);

		c.add(new Leaf("Leaf C"));

		Leaf leaf = new Leaf("Leaf D");
		c.add(leaf);
		c.remove(leaf);

		c.display(1);

	}
}
