package com.linzl.bridge.pattern;

public abstract class CellphoneBrand {
	CellphoneSoftware cps;

	public void setCps(CellphoneSoftware cps) {
		this.cps = cps;
	}

	public abstract void operation();
}
