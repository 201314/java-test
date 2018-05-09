package com.gitee.linzl.intermediary.pattern;

public class UnitedNationsSecurityCouncil implements UnitedNations {
	private Country american;// 美国
	private Country china;// 中国

	@Override
	public void declare(String message, Country country) {
		if (country instanceof American) {// 美国向中国发出信息
			china.notifyMessage(message);
		} else {// 中国向美国发出信息
			american.notifyMessage(message);
		}
	}

	public Country getAmericans() {
		return american;
	}

	public Country getChina() {
		return china;
	}

	public void setAmericans(Country american) {
		this.american = american;
	}

	public void setChina(Country china) {
		this.china = china;
	}
}
