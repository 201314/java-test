package com.linzl.facade.pattern;

/*
 * 自己买股票，还不如买基金，让别人帮你买股票，
 * 这样自己就不需要和 股票A、B……进行耦合
 */

public class Fund {
	private InvestA investA;
	private InvestB investB;

	public Fund() {
		investA = new InvestA();
		investB = new InvestB();
	}

	// 方案： 基金经理人，帮你投资
	public void firstMethod() {
		investA.buyA();
		investB.sellB();
	}

	// 方案： 基金经理人，帮你投资
	public void secondMethod() {
		investA.buyA();
		investB.buyB();
	}

	// 方案： 基金经理人，帮你投资
	public void thirdMethod() {
		investA.sellA();
		investB.sellB();
	}
}
