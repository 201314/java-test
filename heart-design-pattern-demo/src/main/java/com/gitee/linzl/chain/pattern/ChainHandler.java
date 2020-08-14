package com.gitee.linzl.chain.pattern;

public interface ChainHandler {
	default boolean execute(Chain chain) {
		boolean flag = handleProcess(chain.getApply());
		// 如果返回真，则责任链条不需要再往下走，直接返回
		if (!flag) {
			return chain.proceed();
		}
		return flag;
	}

	boolean handleProcess(Apply apply);
}
