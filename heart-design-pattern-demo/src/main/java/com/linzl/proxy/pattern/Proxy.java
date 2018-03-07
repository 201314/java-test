package com.linzl.proxy.pattern;

/**
 * @author GDCC i心灵鸡汤you email:2225010489@qq.com 2013-4-9 下午07:26:44 代理类：
 *         代替某人送礼物
 */
public class Proxy implements GiveGift {
	private Pursuer pursuer;

	public Proxy(SchoolGirl mm) {
		// 通过 中间人，将 追求者 和 被追求者 关联起来
		pursuer = new Pursuer(mm);
	}

	@Override
	public void chocolate() {
		pursuer.chocolate();
	}

	@Override
	public void flower() {
		pursuer.flower();
	}

	@Override
	public void travel() {
		pursuer.travel();
	}

}
