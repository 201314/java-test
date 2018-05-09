package com.gitee.linzl.proxy.pattern;

/**
 * 男生 通过 代理人 向女生 送礼物，可知 男生（追求者） 和 代理人（送礼物者） 有相同的方法，只不过是男生买，代理人送
 */
public class Test {
	public static void main(String[] args) {
		SchoolGirl mm = new SchoolGirl("林志玲");

		Proxy proxy = new Proxy(mm);

		proxy.chocolate();
		proxy.flower();
		proxy.travel();
	}
}
