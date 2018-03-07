package com.linzl.proxy.pattern;

public class Pursuer implements GiveGift {
	private SchoolGirl mm;

	public Pursuer(SchoolGirl mm) {
		this.mm = mm;
	}

	@Override
	public void chocolate() {
		System.out.println("送" + mm.getName() + "巧克力");
	}

	@Override
	public void flower() {
		System.out.println("送" + mm.getName() + "玫瑰花");
	}

	@Override
	public void travel() {
		System.out.println("陪" + mm.getName() + "去旅游");
	}

}
