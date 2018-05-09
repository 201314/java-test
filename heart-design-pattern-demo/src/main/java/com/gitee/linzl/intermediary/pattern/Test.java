package com.gitee.linzl.intermediary.pattern;

public class Test {
	public static void main(String[] args) {
		UnitedNationsSecurityCouncil un = new UnitedNationsSecurityCouncil();

		Country american = new American(un);
		Country china = new China(un);

		// 联合国成员
		un.setAmericans(american);
		un.setChina(china);

		american.sendMessage("钓鱼岛是中国的");
		china.sendMessage("中国赞成美国的说法");
	}
}