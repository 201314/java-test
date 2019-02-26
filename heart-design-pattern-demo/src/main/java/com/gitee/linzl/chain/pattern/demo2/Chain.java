package com.gitee.linzl.chain.pattern.demo2;

import java.util.LinkedList;
import java.util.List;

import com.gitee.linzl.chain.pattern.Apply;

public class Chain {
	public List<ChainHandler> handlers = new LinkedList<>();

	private int index = 0;

	private Apply apply;

	public Chain(List<ChainHandler> handlers, Apply apply) {
		this.handlers = handlers;
		this.apply = apply;
	}

	public Apply getApply() {
		return this.apply;
	}

	public boolean proceed() {
		if (index >= handlers.size()) {
			return false;
		}
		return handlers.get(index++).execute(this);
	}
}