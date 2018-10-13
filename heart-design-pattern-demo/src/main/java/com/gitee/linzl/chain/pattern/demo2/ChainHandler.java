package com.gitee.linzl.chain.pattern.demo2;

import com.gitee.linzl.chain.pattern.Apply;

public interface ChainHandler {
	default public boolean execute(Chain chain) {
		boolean flag = handleProcess(chain.getApply());
		if (!flag) {// 如果返回真，则责任链条不需要再往下走，直接返回
			return chain.proceed();
		}
		return flag;
	}

	public abstract boolean handleProcess(Apply apply);
}
