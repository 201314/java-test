package com.gitee.linzl.chain.pattern.demo2;

import com.gitee.linzl.chain.pattern.Apply;

//项目经理 
public class ProjectHandler implements ChainHandler {

	// 处理申请，默认为申请不通过
	@Override
	public boolean handleProcess(Apply apply) {
		// 处理请假
		if (apply.getApplyType() == 1 && apply.getNumber() <= 3) {
			System.out.println("项目经理通过申请请假");
			return true;
		}
		// 处理加薪
		else if (apply.getApplyType() == 2 && apply.getNumber() <= 500) {
			System.out.println("项目经理通过申请加薪");
			return true;
		}
		return false;
	}

}
