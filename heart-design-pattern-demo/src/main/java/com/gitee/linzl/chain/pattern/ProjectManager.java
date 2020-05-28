package com.gitee.linzl.chain.pattern;

//项目经理 
public class ProjectManager extends Manager {

	// 处理申请，默认为申请不通过
	@Override
	public boolean handleRequest(Apply apply) {
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
		// 无权处理该申请，提交给上级处理
		else {
			if (superManager != null) {
				return superManager.handleRequest(apply);
			}
		}
		return false;
	}

}
