package com.linzl.responsibility.chain.pattern;
//总经理
public class GeneralManager extends Manager {

	@Override
	public boolean handleRequest(Apply apply) {
		//处理请假，3~15默认允许
		if(apply.getApplyType()==1&&apply.getNumber()<15){
			System.out.println("总经理通过申请请假");
			return true;
		}
		//处理加薪，500~1500内默认允许
		else if(apply.getApplyType()==2&&apply.getNumber()<1500){
			System.out.println("总经理通过申请加薪");
			return true;
		}
		return false;
	}

}
