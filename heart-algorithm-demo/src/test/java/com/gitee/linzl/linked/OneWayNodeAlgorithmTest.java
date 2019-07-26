package com.gitee.linzl.linked;

import org.junit.Test;

public class OneWayNodeAlgorithmTest {
	@Test
	public void reserve() {
		OneWayNode a = new OneWayNode();
		a.setData("a");

		OneWayNode b = new OneWayNode();
		b.setData("b");

		OneWayNode c = new OneWayNode();
		c.setData("c");

		OneWayNode d = new OneWayNode();
		d.setData("d");

		OneWayNode e = new OneWayNode();
		e.setData("e");

		OneWayNode f = new OneWayNode();
		f.setData("f");

		a.setNext(b);
		b.setNext(c);
		c.setNext(d);
		d.setNext(e);
		e.setNext(f);

		System.out.println("反转前:");
		OneWayNode beforCur = a;
		while (beforCur != null) {
			System.out.print(beforCur.getData() + "\t");
			beforCur = beforCur.getNext();
		}
		System.out.println();
		OneWayNode afterCur = OneWayNodeAlgorithm.reserve(a);

		System.out.println("反转后:");
		while (afterCur != null) {
			System.out.print(afterCur.getData() + "\t");
			afterCur = afterCur.getNext();
		}
	}

	@Test
	public void recursionReserve() {
		OneWayNode a = new OneWayNode();
		a.setData("a");

		OneWayNode b = new OneWayNode();
		b.setData("b");

		OneWayNode c = new OneWayNode();
		c.setData("c");

		OneWayNode d = new OneWayNode();
		d.setData("d");

		OneWayNode e = new OneWayNode();
		e.setData("e");

		OneWayNode f = new OneWayNode();
		f.setData("f");

		a.setNext(b);
		b.setNext(c);
		c.setNext(d);
		d.setNext(e);
		e.setNext(f);

		System.out.println("递归反转前:");
		OneWayNode beforCur = a;
		while (beforCur != null) {
			System.out.print(beforCur.getData() + "\t");
			beforCur = beforCur.getNext();
		}
		System.out.println();
		OneWayNode afterCur = OneWayNodeAlgorithm.recursionReserve(a);

		System.out.println("递归反转后:");
		while (afterCur != null) {
			System.out.print(afterCur.getData() + "\t");
			afterCur = afterCur.getNext();
		}
	}

	@Test
	public void twoTwoReserve() {
		OneWayNode a = new OneWayNode();
		a.setData("a");

		OneWayNode b = new OneWayNode();
		b.setData("b");

		OneWayNode c = new OneWayNode();
		c.setData("c");

		OneWayNode d = new OneWayNode();
		d.setData("d");

		OneWayNode e = new OneWayNode();
		e.setData("e");

		OneWayNode f = new OneWayNode();
		f.setData("f");

		OneWayNode g = new OneWayNode();
		g.setData("g");

		OneWayNode h = new OneWayNode();
		h.setData("h");

		a.setNext(b);
		b.setNext(c);
		c.setNext(d);
		d.setNext(e);
		e.setNext(f);
		f.setNext(g);
		g.setNext(h);

		System.out.println("两两反转前:");
		OneWayNode beforeCur = a;
		while (beforeCur != null) {
			System.out.print(beforeCur.getData() + "\t");
			beforeCur = beforeCur.getNext();
		}
		System.out.println();
		OneWayNode afterCur = OneWayNodeAlgorithm.twoTwoReserve(a);

		System.out.println("两两反转后:");
		while (afterCur != null) {
			System.out.print(afterCur.getData() + "\t");
			afterCur = afterCur.getNext();
		}
	}

	@Test
	public void recursionTwoTwoReserve() {
		OneWayNode a = new OneWayNode();
		a.setData("a");

		OneWayNode b = new OneWayNode();
		b.setData("b");

		OneWayNode c = new OneWayNode();
		c.setData("c");

		OneWayNode d = new OneWayNode();
		d.setData("d");

		OneWayNode e = new OneWayNode();
		e.setData("e");

		OneWayNode f = new OneWayNode();
		f.setData("f");

		OneWayNode g = new OneWayNode();
		g.setData("g");

		OneWayNode h = new OneWayNode();
		h.setData("h");

		a.setNext(b);
		b.setNext(c);
		c.setNext(d);
		d.setNext(e);
		e.setNext(f);
		f.setNext(g);
		g.setNext(h);

		System.out.println("两两反转前:");
		OneWayNode beforeCur = a;
		while (beforeCur != null) {
			System.out.print(beforeCur.getData() + "\t");
			beforeCur = beforeCur.getNext();
		}
		System.out.println();
		OneWayNode afterCur = OneWayNodeAlgorithm.recursionTwoTwoReserve(a);

		System.out.println("两两反转后:");
		while (afterCur != null) {
			System.out.print(afterCur.getData() + "\t");
			afterCur = afterCur.getNext();
		}
	}

	/**
	 * 9->3->7和6->3，相加结果为1->0->0->0
	 */
	@Test
	public void testPlus() {
		OneWayNode one = new OneWayNode();
		one.setNum(9);

		OneWayNode b = new OneWayNode();
		b.setNum(9);

		OneWayNode c = new OneWayNode();
		c.setNum(9);

		one.setNext(b);
		b.setNext(c);

		OneWayNode two = new OneWayNode();
		two.setNum(1);

		OneWayNode twoB = new OneWayNode();
		twoB.setNum(9);

		two.setNext(twoB);

		OneWayNode minus = OneWayNodeAlgorithm.plus(one, two);
		while (minus != null) {
			System.out.print(minus.getNum() + "\t");
			minus = minus.getNext();
		}
	}

	/**
	 * 例如：9->3->7和6->3，相减结果为8->7->4
	 */
	@Test
	public void testMinus() {
		OneWayNode one = new OneWayNode();
		one.setNum(9);

		OneWayNode b = new OneWayNode();
		b.setNum(3);

		OneWayNode c = new OneWayNode();
		c.setNum(7);

		one.setNext(b);
		b.setNext(c);

		OneWayNode two = new OneWayNode();
		two.setNum(6);

		OneWayNode twoB = new OneWayNode();
		twoB.setNum(3);

		two.setNext(twoB);

		OneWayNode minus = OneWayNodeAlgorithm.minus(one, two);
		while (minus != null) {
			System.out.print(minus.getNum() + "\t");
			minus = minus.getNext();
		}
	}

	@Test
	public void testMerge() {
//		例如：9->7->6->3->1和8->7->5->3->2->1，合并后结果为9->8->7->6->5->3->2->1
		OneWayNode one = new OneWayNode();
		one.setNum(9);

		OneWayNode b = new OneWayNode();
		b.setNum(7);

		OneWayNode c = new OneWayNode();
		c.setNum(6);

		OneWayNode d = new OneWayNode();
		d.setNum(3);

		OneWayNode e = new OneWayNode();
		e.setNum(1);

		one.setNext(b);
		b.setNext(c);
		c.setNext(d);
		d.setNext(e);

		OneWayNode two = new OneWayNode();
		two.setNum(8);

		OneWayNode twoB = new OneWayNode();
		twoB.setNum(7);

		OneWayNode twoC = new OneWayNode();
		twoC.setNum(5);

		OneWayNode twoD = new OneWayNode();
		twoD.setNum(3);

		OneWayNode twoE = new OneWayNode();
		twoE.setNum(2);
		OneWayNode twoF = new OneWayNode();
		twoF.setNum(1);

		two.setNext(twoB);
		twoB.setNext(twoC);
		twoC.setNext(twoD);
		twoD.setNext(twoE);
		twoE.setNext(twoF);

		OneWayNode merged = OneWayNodeAlgorithm.merge(one, two);
		while (merged != null) {
			System.out.print(merged.getNum() + "\t");
			merged = merged.getNext();
		}
	}

	@Test
	public void reserverByGroup() {
		OneWayNode a = new OneWayNode();
		a.setData("a");

		OneWayNode b = new OneWayNode();
		b.setData("b");

		OneWayNode c = new OneWayNode();
		c.setData("c");

		OneWayNode d = new OneWayNode();
		d.setData("d");

		OneWayNode e = new OneWayNode();
		e.setData("e");

		OneWayNode f = new OneWayNode();
		f.setData("f");

		a.setNext(b);
		b.setNext(c);
		c.setNext(d);
		d.setNext(e);
		e.setNext(f);

		System.out.println("反转前:");
		OneWayNode beforCur = a;
		while (beforCur != null) {
			System.out.print(beforCur.getData() + "\t");
			beforCur = beforCur.getNext();
		}
		System.out.println();
		OneWayNode afterCur = OneWayNodeAlgorithm.reserverByGroup(a, 4);

		System.out.println("反转后:");
		while (afterCur != null) {
			System.out.print(afterCur.getData() + "\t");
			afterCur = afterCur.getNext();
		}
	}
}
