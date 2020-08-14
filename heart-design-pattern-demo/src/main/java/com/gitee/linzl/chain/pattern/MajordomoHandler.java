package com.gitee.linzl.chain.pattern;

//总监
public class MajordomoHandler implements ChainHandler {

	@Override
	public boolean handleProcess(Apply apply) {
		// 处理请假，3~10默认允许
		if (apply.getApplyType() == 1 && apply.getNumber() < 10) {
			System.out.println("总监通过申请请假");
			return true;
		}
		// 处理加薪，500~1000内默认允许
		else if (apply.getApplyType() == 2 && apply.getNumber() < 1000) {
			System.out.println("总监通过申请加薪");
			return true;
		}
		return false;
	}
}
